# Interpreter Pattern

## Interpreter

> 일을 하다가 자주 사용하는 문제를 패턴 언어 문법등으로 정의하고 그 문법에 맞춰서 표현식을 작성해 대입을 푼다?

> Stream...? 중개오퍼레이션, 종료 오퍼레이션

### Structure

![Interpreter Structure](img/Interpreter%20Structure.png)

## 장단점

### 장점

- 주로 등장하는 문제 패턴에 대해서 언어, 문법으로 정의가 가능하다.
- 기존 코드를 변경하지 않고 새롭게 Expression의 추가가 가능하다

### 단점

- 복잡한 문법을 표현하려 하다 보니 Expression, Parser의 로직이 복잡하다...
  - 진짜로...

## Source

### Before Source

#### Main겸 Logic

```java
public class PostfixNotation {
  private final String expression;
  public PostfixNotation(String expression) {
    this.expression = expression;
  }
  public static void main(String[] args) {
    PostfixNotation postfixNotation = new PostfixNotation("123+-");
    postfixNotation.calculate();
  }

  private void calculate() {
    Stack<Integer> numbers = new Stack<>();

    for (char c : this.expression.toCharArray()) {
      switch (c) {
        case '+':
          numbers.push(numbers.pop() + numbers.pop());
          break;
        case '-':
          int right = numbers.pop();
          int left = numbers.pop();
          numbers.push(left - right);
          break;
        default:
          numbers.push(Integer.parseInt(c + ""));
      }
    }

    System.out.println(numbers.pop());
  }
}

```

### After Source

> `xyz+-`의 실행 Example...어렵네

```java
new MinusExpression(
  new VariableExpression('x'),
  new PlusExpression(
    new VariableExpression('y'), new VariableExpression('z')
  )
);
```

#### Main, Client

```java
public class App {
  public static void main(String[] args) {
    PostfixExpression expression = PostfixParser.parse("xyz+-a+");
    int result = expression.interpret(Map.of('x', 1, 'y', 2, 'z', 3, 'a', 4));
    System.out.println(result);
  }
}
```

#### Service Logic 1

> 클래스의 직접적인 구현을 해야함.

```java
public interface PostfixExpression {
  int interpret(Map<Character, Integer> context);
}
public class PostfixParser {
  public static PostfixExpression parse(String expression) {
    Stack<PostfixExpression> stack = new Stack<>();
    for (char c : expression.toCharArray())
        stack.push(getExpression(c, stack));
    return stack.pop();
  }

  private static PostfixExpression getExpression(char c, Stack<PostfixExpression> stack) {
    switch (c) {
      case '+':
        return new PlusExpression(stack.pop(), stack.pop());
      case '-':
        PostfixExpression right = stack.pop();
        PostfixExpression left = stack.pop();
        return new MinusExpression(left, right);
      default:
        return new VariableExpression(c);
    }
  }
}

public class MinusExpression implements PostfixExpression {
  private PostfixExpression left;
  private PostfixExpression right;
  public MinusExpression(PostfixExpression left, PostfixExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int interpret(Map<Character, Integer> context) {
    return left.interpret(context) - right.interpret(context);
  }
}
public class MultiplyExpression implements PostfixExpression{
  private PostfixExpression left, right;
  public MultiplyExpression(PostfixExpression left, PostfixExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int interpret(Map<Character, Integer> context) {
    return left.interpret(context) * right.interpret(context);
  }
}

public class PlusExpression implements PostfixExpression {
  private PostfixExpression left;
  private PostfixExpression right;
  public PlusExpression(PostfixExpression left, PostfixExpression right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public int interpret(Map<Character, Integer> context) {
    return left.interpret(context) + right.interpret(context);
  }
}

public class VariableExpression implements PostfixExpression {
  private Character character;
  public VariableExpression(Character character) {
    this.character = character;
  }
  @Override
  public int interpret(Map<Character, Integer> context) {
    return context.get(this.character);
  }
}
```

#### Service Logic 2

> Class 말고 Interface를 활용해서....

```java
public interface PostfixExpression {
  int interpret(Map<Character, Integer> context);

  static PostfixExpression plus(PostfixExpression left, PostfixExpression right) {
    return context -> left.interpret(context) + right.interpret(context);
  }

  static PostfixExpression minus(PostfixExpression left, PostfixExpression right) {
    return context -> left.interpret(context) - right.interpret(context);
  }

  static PostfixExpression multiply(PostfixExpression left, PostfixExpression right) {
    return context -> left.interpret(context) * right.interpret(context);
  }

  static PostfixExpression variable(Character c) {
    return context -> context.get(c);
  }
}

public class PostfixParser {
  public static PostfixExpression parse(String expression) {
    Stack<PostfixExpression> stack = new Stack<>();
    for (char c : expression.toCharArray())
        stack.push(getExpression(c, stack));
    return stack.pop();
  }

  private static PostfixExpression getExpression(char c, Stack<PostfixExpression> stack) {
    switch (c) {
      case '+':
        return PostfixExpression.plus(stack.pop(), stack.pop());
      case '-':
        PostfixExpression right = stack.pop();
        PostfixExpression left = stack.pop();
        return PostfixExpression.minus(stack.pop(), stack.pop());
      case '*':
        PostfixExpression right = stack.pop();
        PostfixExpression left = stack.pop();
        return PostfixExpression.multiply(stack.pop(), stack.pop());
      default:
        return PostfixExpression.variable(c);
    }
  }
}
```
