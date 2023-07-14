# Mock 객체의 확인

> Mock 객체를 확인(Verify)한다는 의미는 실제 Method의 호출이 이뤄지는지? 어떤 순서대로 호출이 되는지? 등의 행동을 확인하는 것을 의미한다.

## 호출 여부 확인 및 몇회 횟수 확인

### Sample Code

> 다음의 코드는 `memberService`에서 `notify(Study)`가 1회 실행 되었는지를 검증한다.
> `never()`를 `times()`대신 입력하게 될 경우 아얘 호출되지 않았다는 뜻으로 실행된다.
>
> > `memberService`에는 실제 `notify`메소드가 존재한다.

`Mockito.verify(memberService, times(1)).notify(study);`

### Docs

- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#exact_verification

## 로직의 실행 순서 검증 방법

### Sample Code

> memberService라는 Mock객체에서 study라는 객체로 notify가 먼저 실행된 후 member라는 객체로 notify가 실행되는지를 검증한다.

```java
@Test
void logicOrderTest() {
  StudyService studyService = new StudyService(memberService, studyRepository);
  assertNotNull(studyService);

  Member member = new Member();
  member.setId(1L);
  member.setEmail("jong1@abc.com");

  Study study = new Study(10, "테스트");

  InOrder inOrder = Mockito.inOrder(memberService); // 순서검증 객체 생성
  inOrder.verify(memberService).notify(study);
  inOrder.verify(memberService).notify(member);
}
```

### Docs

- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#in_order_verification

## 특정 시점 이후 MethodCall이 존재하는지 확인하는 방법

### Sample COde

> MemberService에서 notify(study)실행 이후 추가적은 MethodCall이 존재하는 경우 테스트가 깨지게 된다.

```java
Mockito.verify(memberService, times(1)).notify(study);
verifyNoMoreInteractions(memberService);
```

### Docs

- https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html#finding_redundant_invocationsFinding redundant invocations
