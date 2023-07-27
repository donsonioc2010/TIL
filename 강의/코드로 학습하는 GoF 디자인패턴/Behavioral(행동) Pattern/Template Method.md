# Template Method Pattern

## Template Method

> 알고리즘 구조를 서브 클래스가 확장을 할 수 있도록 템플릿으로 제공하는 방법

### 구현과정

> 추상클래스와 상속을 사용하여 구현을 진행한다.

## 장단점

### 장점

- 템플릿 코드를 재사용하고, 중복 코드를 줄일 수 있다.
- 템플릿 코드를 변경 없이, 상속을 받아 구체적인 알고리즘만 변경할 수 있다.

### 단점

- 리스코프 치환 원칙의 위배가 될 수도 있다
- 알고리듬 구조가 복잡할 수록 템플릿의 유지가 힘들다

## Source

### Before Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    FileProcessor fileProcessor = new FileProcessor("number.txt");
    int result = fileProcessor.process();
    System.out.println(result);
  }
}
```

#### Logic

```java
public class FileProcessor {
  private String path;
  public FileProcessor(String path) {
    this.path = path;
  }
  public int process() {
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
      int result = 0;
      String line = null;
      while((line = reader.readLine()) != null) {
        result += Integer.parseInt(line);
      }
      return result;
    } catch (IOException e) {
        throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
    }
  }
}

public class MultuplyFileProcessor {
  private String path;
  public MultuplyFileProcessor(String path) {
    this.path = path;
  }

  public int process() {
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
      int result = 0;
      String line = null;
      while((line = reader.readLine()) != null) {
        result *= Integer.parseInt(line);
      }
        return result;
    } catch (IOException e) {
      throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
    }
  }
}
```

### After Source

#### Main, Client

```java
public class Client {
  public static void main(String[] args) {
    FileProcessor fileProcessor = new Multiply("number.txt");
    int result = fileProcessor.process();
    System.out.println(result);
  }
}
```

#### Logic

```java
public abstract class FileProcessor {
  private String path;
  public FileProcessor(String path) {
    this.path = path;
  }
  public final int process() {
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
      int result = 0;
      String line = null;
      while((line = reader.readLine()) != null) {
        result = getResult(result, Integer.parseInt(line));
      }
        return result;
    } catch (IOException e) {
      throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
    }
  }
  protected abstract int getResult(int result, int number);
}

public class Multiply extends FileProcessor {
  public Multiply(String path) {
    super(path);
  }
  @Override
  protected int getResult(int result, int number) {
    return result *= number;
  }
}
```

#### Template Callback을 활용한 Template Method 패턴 제작

> 활용시 Callback은 무조건 Interface를 활용해야 하며, 메소드가 단일이어야 한다.
> 해당 부분을 활용해서 람다, 익명 내부클래스의 활용이 가능해진다.
> Callback활용시 Template에서 추상클래스의 활용은 불필요해진다.
>
> > 해당 패턴은 GoF의 디자인패턴에는 정의가 되어 있지는 않다.

```java
public class Client {
  public static void main(String[] args) {
    FileProcessor fileProcessor = new FileProcessor("number.txt");
    int result = fileProcessor.process((sum, number) -> sum += number);
    System.out.println(result);
  }
}

public class FileProcessor {
  private String path;
  public FileProcessor(String path) {
    this.path = path;
  }

  public final int process(Operator operator) {
    try(BufferedReader reader = new BufferedReader(new FileReader(path))) {
      int result = 0;
      String line = null;
      while((line = reader.readLine()) != null) {
        //아래와 같이 인터페이스의 콜백을 실행시킴.
        result = operator.getResult(result, Integer.parseInt(line));
      }
      return result;
    } catch (IOException e) {
      throw new IllegalArgumentException(path + "에 해당하는 파일이 없습니다.", e);
    }
  }
}

public interface Operator {
  abstract int getResult(int result, int number);
}

public class Plus implements Operator {
  @Override
  public int getResult(int result, int number) {
    return result += number;
  }
}
```
