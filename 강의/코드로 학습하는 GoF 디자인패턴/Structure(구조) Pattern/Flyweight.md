# Flyweight Pattern

## Flyweight

> 매우 많은 Instance를 생성하는 Application에서 효과적으로 사용이 가능한 디자인 패턴이다.  
> Application에서 굉장히 많은 Instance를 생성하다 보면 OOM가 발생하거나, 메모리가 부족해지는 문제가 발생한다.
>
> Flyweight패턴은 공통된 부분들 중에서 자주 변하지 않는 속성들을 따로 모아서 재사용을 하는 패턴이다.

### 주의 사항

- Flyweight패턴에서 intrinsit(자주 변하지 않는 속성들)은 Immutable해야한다.
  - 공유하는 객체들이기 떄문에 한곳에서 변경이 이뤄지면 모든곳이 변경이 이뤄진다.
- Factory메소드 꼭 필요하다!!!

## 장단점

### 장점

- Application에서 사용하는 메모리를 감소시킬 수 있다.

### 단점

- 코드의 복잡도 상승

## Source

### Before Source

#### Client

```java
public class Client {
  public static void main(String[] args) {
    Character c1 = new Character('h', "white", "Nanum", 12);
    Character c2 = new Character('e', "white", "Nanum", 12);
    Character c3 = new Character('l', "white", "Nanum", 12);
    Character c4 = new Character('l', "white", "Nanum", 12);
    Character c5 = new Character('o', "white", "Nanum", 12);
  }
}
```

#### logic

```java
public class Character {
  private char value;
  private String color;
  private String fontFamily;
  private int fontSize;

  public Character(char value, String color, String fontFamily, int fontSize) {
    this.value = value;
    this.color = color;
    this.fontFamily = fontFamily;
    this.fontSize = fontSize;
  }
}
```

### After Source

> 예제는 fontFamily와 FontSize를 자주변하지 않는 속성으로 정의하여서 Font Class에는 두개의 속성만 들어간 것

#### Client

```java
public class Client {

  public static void main(String[] args) {
    FontFactory fontFactory = new FontFactory();
    Character c1 = new Character('h', "white", fontFactory.getFont("nanum:12"));
    Character c2 = new Character('e', "white", fontFactory.getFont("nanum:12"));
    Character c3 = new Character('l', "white", fontFactory.getFont("nanum:12"));
  }
}

```

#### Logic

```java
public class Character {
  private char value;
  private String color;
  private Font font;

  public Character(char value, String color, Font font) {
    this.value = value;
    this.color = color;
    this.font = font;
  }
}
```

#### Pattern Logic

```java
public class FontFactory {
  private Map<String, Font> cache = new HashMap<>();
  public Font getFont(String font) {
    if (cache.containsKey(font)) {
      return cache.get(font);
    } else {
      String[] split = font.split(":");
      Font newFont = new Font(split[0], Integer.parseInt(split[1]));
      cache.put(font, newFont);
      return newFont;
    }
  }
}

//Immutable함을 유지하기 위해, 생성자로만 설정이 가능하며, 상속을 막음
public final class Font {
  final String family;
  final int size;
  public Font(String family, int size) {
    this.family = family;
    this.size = size;
  }

  public String getFamily() {
    return family;
  }

  public int getSize() {
    return size;
  }
}
```
