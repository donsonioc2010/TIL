# Mock 객체의 Stubbing방법

## Stubbing?

> 행동, 즉 Mock객체한테 인자값을 줄 경우 어떤 행동을 하게 될지 조작하는것을 의미

## Mock객체의 기본 행동

- 기본적으로 `Return타입이 있는 Method는 모두 Null을 Return`한다.
- `Primitive`타입을 Return하는 Method는 `Primitive 타입의 기본값을 Return`한다.
- `Collection`의 경우에는 `Empty Collection을 Return`한다.
- `Void`메소드는 예외를 던지지 않고, 아무 일도 발생하지 않는다.
- `Optional`이 Return Type인 경우에는 Optional.empty()로 반환된다.

## Stubbing

| MethodName                                 |
| ------------------------------------------ |
| [when](#when)                              |
| [thenReturn](#thenreturn)                  |
| [any()](#any)                              |
| [Argument Matchers](#argument-matchers)    |
| [thenThrow](#thenthrow)                    |
| [doThrow](#dothrow)                        |
| [OngoingStubbing](#ongoingstubbing-object) |

### when

> Mock객체의 Method가 호출될 때 인자값을 특정 인자값을 주는 경우 행동 지정한다.
>
> > 예제 코드의 경우에는 일부로 `when`의 활용만을 보여주기 위해서 Return을 지정하였으나, `Chaning`을 해서 이어나갈 수 있다.

```java
@Test
void create_mock_test( @Mock MemberService m, @Mock StudyRepository sr){
  Member member = new Member();
  member.setId(1L);
  member.setEmail("abcde@naver.com");
  OngoingStubbing<Optional<Member>> a = Mockito.when(m. findById(1L));
}
```

### thenReturn

> 행위에 대해서 어떤 값을 Return할지를 지정할 수 있으며, 단독으로의 사용은 불가능하다.
>
> > 내 경험은 `when`과만 보통 어울려 사용했던거같은데..
>
> > 아래 예제코드의 의미는 `MemberService.findById`를 사용시 `1L`를 인자값으로 넣을 경우  
> > `member`객체를 `Optional`로 감싼 `Optional<Member>`를 Return한다는 의미이다.

```java
@Test
void create_mock_test(@Mock MemberService m, @Mock StudyRepository sr) {
  Member member = new Member();
  member.setId(1L);
  member.setEmail("abcde@naver.com");
  Mockito.when(m.findById(1L)).thenReturn(Optional.of(member));
}
```

### any()

> 인자값으로 `아무 값`이나 받게도 가능하다.
> 실제 `Method Call`을 진행하고 `Arguments`로 어떠한 값을 제공하더라도 Return지정한 값을 받게 된다.

```java
@Test
void create_mock_test(@Mock MemberService m, @Mock StudyRepository sr) {
    Member member = new Member();
    member.setId(1L);
    member.setEmail("abcde@naver.com");
    Mockito.when(m.findById(Mockito.any())).thenReturn(Optional.of(member));
}
```

#### Argument Matchers

> [any()](#any)를 `Argument Matchers`라고 칭하는데 더 자세한 Docs를 보고싶은 경우에는 [Arguments Matchers Docs](https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#3) 참고할것

### thenThrow

> [thenReturn](#thenreturn)과 반대로 Throw로 Return시키는 방법

```java
@Test
void create_mock_test(@Mock MemberService m, @Mock StudyRepository sr) {
  Member member = new Member();
  member.setId(1L);
  member.setEmail("abcde@naver.com");
  Mockito.when(m.findById(Mockito.any())).thenThrow(new RuntimeException());
}
```

#### doThrow

> `Void` Return Type의 메소드의 경우에 사용할 수 있는 방법이다.
>
> > 아래의 예제코드처럼 설정이 가능한데, Exception을 발생시키는 타이밍이 `MemberService`에서 `validate`메소드에 인자값이 `1L`이 들어가게 될때 Exception을 발생시키는 코드이다.

```java
Mockito.doThrow(new RuntimeException()).when(memberService).validate(1L);
```

### OngoingStubbing Object

> 위의 thenReturn, thenThrow등의 메소드들은 결국 `행위`메소드의 Chaning이 가능한데 그 이유가 아래의 `OngoingStubbing`객체를 반환하기 때문이다.
> 아래의 객체들을 통해 Return되는 행위 또는 값을 지정할 수 있는것

```java
public interface OngoingStubbing<T> {
    OngoingStubbing<T> thenReturn(T var1);
    OngoingStubbing<T> thenReturn(T var1, T... var2);
    OngoingStubbing<T> thenThrow(Throwable... var1);
    OngoingStubbing<T> thenThrow(Class<? extends Throwable> var1);
    OngoingStubbing<T> thenThrow(Class<? extends Throwable> var1, Class<? extends Throwable>... var2);
    OngoingStubbing<T> thenCallRealMethod();
    OngoingStubbing<T> thenAnswer(Answer<?> var1);
    OngoingStubbing<T> then(Answer<?> var1);
    <M> M getMock();
}
```
