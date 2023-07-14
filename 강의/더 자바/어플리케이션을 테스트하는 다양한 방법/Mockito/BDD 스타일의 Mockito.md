# BDD스타일의 Mockito

## BDD?

> 이 말 뜯의 의미부터 몰랐었기에, 알아보는게 먼저였다.
> 보고서는 `TDD`연상을 해볼 생각이 없었는데, `TDD`가 우선순위로 나올줄은 몰랐다.
>
> > `BDD`란 `Behaviour-Driven Development`의 약자로 직역하면 행동 중심의 개발이라고 한다.
> > 내가아는 `TDD`의 개념은 TestCase를 먼저 제작하고 개발을 진행하면서 해당 테스트 케이스에 맞춰가면서 개발을 진행하는것으로 알고 있다.
> > 반면에 `BDD`는 `TDD`와는 다르게 시나리오를 기반으로 테스트 케이스를 작성해나가며, `TDD`처럼 LowLevel의 함수 유닛테스트까지 진행하지를 않는다.

### 이게 왜나와?

글을 읽다보니, given, when, then이 나온다.
어..? Mock에 있는 애들이 그대로 있는 거네..?하면서 이제 메소드들이 이해가 되기 시작했다...

### BDD의 참고한 Reference

- https://www.popit.kr/bdd-behaviour-driven-development%EC%97%90-%EB%8C%80%ED%95%9C-%EA%B0%84%EB%9E%B5%ED%95%9C-%EC%A0%95%EB%A6%AC/

## Code?

> BDD스타일의 경우 `BDDMockito`를 통해 테스트 케이스의 작성이 이루어진다.

### Given

> when의 문구와 given의 문구의 기능은 사실상 같다.

```java
when(memberService.findById(1L)).thenReturn(Optional.of(member));
when(studyRepository.save(study)).thenReturn(study);

given(memberService.findById(1L)).willReturn(Optional.of(member));
given(studyRepository.save(study)).willReturn(study);
```

### Then

> verify역시 then과 기능은 사실상 같다.

```java
Mockito.verify(memberService, times(1)).notify(study);
Mockito.verifyNoMoreInteractions(memberService);

BDDMockito.then(memberService).should(times(1)).notify(study);
BDDMockito.then(memberService).shouldHaveNoMoreInteractions();
```
