# Assertions

> 객체의 값에 대한 검증 API, `Assertions.assert*()`메소드들로 검증이 가능하다.

## Assertions?

- `assert*(Type, Type)`, `assert*(Type, Type, String)`, `assert*(Type, Type, Supplier)`
  - 타입은 여러개로 표현된다.
  - Type, Type메소드의 경우, True, False(실패와 성공)에 대해서만 Return한다.
  - String과 Supplier의 경우 TestCase가 실패할 시, 해당 String Message를 실패케이스와 함께 출력한다.
  - 첫번째, 두번째 파라미터 인자의 위치를 변경해도 상관은 없으나, `assert*`메소드들의 첫번째 인자가 `Expected` 로 명칭이 되어있다.
    - 즉 나오기를 희망하는 Value를 첫번째 인자로, 테스트를 해야할 대상을 두번째 인자로 제공하면 좋다.

## assertion Method

> 자매품으로 대부분의 메소드가 `assertNot~`의 메소드가 존재한다.

| 메소드 명       | Description                               |
| --------------- | ----------------------------------------- |
| `assertEquals`  | 값이 기대값과 `같은지`확인                |
| `assertNotNull` | 값이 `Null`이 아닌지 확인                 |
| `assertTrue`    | 값 또는 조건이 `true`인지 확인            |
| `assertAll`     | 모든 테스트 케이스를 한번에 확인          |
| `assertThrows`  | `예외`가 발생하는지 확인                  |
| `assertTimeout` | `특정 시간 안에 실행`이 `완료`되는지 확인 |
| `assertSame`    | 값과 기대값이 `동일한지` 확인             |

### assertAll

> 자주 사용하지 않아본, assertAll만 진행한다

> 아래의 코드는 **assertAll을 활용하지 않은 경우**로, assert가 `실패하면 중간에 바로 중단`된다
>
> > ```java
> > @Test
> > void assertAllNotUsed() {
> >   Study study = new Study(-1);
> >   assertNotNull(study);
> >   assertEquals(StudyStatus.DRAFT, study.> > getStatus(), () -> "스터디의 초기값은" + > > StudyStatus.DRAFT + "다.");
> >   assertTrue(study.getLimit() > 0, "스터디 최대 > > 참석 가능 인원은 0보다 커야한다.");
> > }
> > ```
>
> `assertAll`을 활용하게 될 경우 `중간에 테스트 케이스가 실패`하더라도 `모든 테스트케이스에 대한 결과`를 출력한다.
>
> > ```java
> > @Test
> > void assertAllTest() {
> >   Study study = new Study(-1);
> >   assertAll(
> >     () -> assertNotNull(study),
> >     () -> assertEquals(StudyStatus.DRAFT, study.> > getStatus(), () -> "스터디의 초기값은" + > > StudyStatus.DRAFT + "다."),
> >     () -> assertTrue(study.getLimit() > 0, "스터디 > > 최대 참석 가능 인원은 0보다 커야한다.")
> >   );
> > }
> > ```
