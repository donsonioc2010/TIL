# Mock객체 생성 및 사용법

## 임의의 타입의 Mock객체 생성방법

> Interface만 존재하는 상황 또는 주입을 해야하는데 구현체가 없거나 해당 구현체를 직접 사용하면 안되는 경우 유용하다.

### Sample Interface Code

> 구현체는 없고, `Interface`만 존재한다.

```java
public interface MemberService {
  void validate(Long memberId) throws InvalidMemberException;
  Member findById(Long memberId) throws MemberNotFoundException;
}

public interface StudyRepository extends JpaRepository<Study, Long> {}
```

### Create Mock Object

#### 방법 1

> `Mockito.mock(Class)`를 활용한다

```java
MemberService m = Mockito.mock(MemberService.class);
StudyRepository sR = Mockito.mock(StudyRepository.class);
```

#### 방법 2

> `@Mock`와 `@ExtendWith(MockitoExtension.class)` Annotation을 활용한다

```java
@ExtendWith(MockitoExtension.class)
class TestClass{
  @Mock MemberService m;
  @Mock StudyRepository sr;
}
```

#### 방법 3

> Method Arguments에 직접 `@Mock`을 선언해 사용하는 방법이며, 이 경우에도 `@ExtendWith(MockitoExtension.class)`가 필요하다.(이게 되네)

```java
@ExtendWith(MockitoExtension.class)
class TestClass{
 @Test
  void create_mock_test( @Mock MemberService m, @Mock StudyRepository sr){}
}
```
