# Functional Interface

> 함수형 인터페이스와 람다 표현식에 대한 활용법

## Functional Interface란?

> 함수형 인터페이스란 Interface에서 **단 한개**의 추상 메소드만 선언한 인터페이스를 의미한다.
> 여기서 메소드가 단 한개라는 의미는 Interface내부에 몇개의 메소드가 있는지는 상관으며 실제 구현해야할 추상메소드가 단 한개만 있으면 함수형 인터페이스다.
> Interface에서 사용가능한 메소드의 종류에 대해서는 [Interface](./Interface.md)토픽에서 에서 자세히 보자

### `@FunctionalInterface`란?

- Interface 선언부위에 추가가 가능하며 안정적인 함수형 인터페이스를 사용 가능하게 만들어준다.
