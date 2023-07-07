# Stream

## Stream이란?

> 연속된 데이터를 처리하는 **Operation**의 실행 모음이며, 데이터를 담고 있는 **Collection**이 아니다.
> 해당 내용은 JavaDoc에서도 확인이 가능하다.

## Stream의 특징

- Java API의 Stream의 설명으로는 Functional in nature하다고 정의가 되어있는데, **해당의미는 스트림을 처리함에 있어서 데이터 소스를 변경하지 않는 것을 의미**한다.
  - 즉 Stream을 열은 데이터를 변경하지 않는다!는 의미는 Deep Copy를 한다는 것.
- Stream으로 처리하는 데이터는 **오직 한번**만 처리한다.
- Stream으로 처리해야 할 데이터는 무제한으로도 가능하다.
  - Short Circuit를 활용해서 무제한의 데이터를 제한하는 것 역시 가능하다
- [중개 오퍼레이션](#중개-오퍼레이션)의 경우 근본적으로 **Lazy(지연시킨다?)**하다
  - Lazy와 관련한 이야기는 [Stream Pipeline](#stream-pipeline)을 확인
- 쉽게 병렬 처리가 가능하다
  - [paralleStream](#parallestream)API를 활용해서 작업이 가능하다

### Operation

#### 중개 오퍼레이션

> Stream을 지속적으로 이어 나갈 수 있게 하는 API이다

- Stream을 Return한다
  - Stream을 지속적으로 이어 나갈 수 있게 하는 이유.
- 중개오퍼레이션을 더 상세히 분류하면 Stateless와 Stateful오퍼레이션으로 분류가 가능하다.
  - 대부분이 Stateless Operation이다.
  - `distinct`, `sorted`처럼 **이전 데이터를 참조**해야 하는 경우 **Stateful**오퍼레이션이다

#### 터미널(종료) 오퍼레이션

> Stream을 종료시키는 API이다

- Stream을 Return하는게 아닌 다른 Type을 Return한다
- `collect`, `allMatch`, `count`, `forEach`, `min`, `max`정도가 대표적이다.

### `paralleStream`

- 내부적으로 Multi Thread, **Fork / Join**을 활용한다.
- Thread간의 **ContextSwitching같은 요소들을 고려**하면 오히려 늦어질수도 있다.
- 데이터가 **방대하게 큰 경우**에 대부분의 상황에 사용하면 성능적 이득을 많이 볼 수 잇다.

## Stream Pipeline

- `0개` 이상의 [중개 오퍼레이션](#중개-오퍼레이션)과 `1개` 이상의 [종료 오퍼레이션](#터미널종료-오퍼레이션)으로 구성된다.
- 스트림의 데아터 소스는 오직 **터미널(종료) 오퍼레이션**을 **실행**할 때만 처리된다.
  - **중개 오퍼레이션**의 Lazy부분이 해당 내용에서 이뤄지게 되는데 [Stream Pipeline Example](#stream-pipeline-example)을 참조한다.
  - **터미널 오퍼레이션**이 존재하지 않는 경우에는 실행하지 않는다.

### Stream Pipeline Example

> 중간에 Stream을 중개 오퍼레이션 뿐이라면 저장하고 담아둘 수 있다.

```java
public class StreamExample {
	public static void main(String[] args) {
		new StreamExample().example();
	}
	public void example() {
		List<OnlineClass> springClasses = new ArrayList<>();
		springClasses.add(new OnlineClass(1, "spring boot", true));
		springClasses.add(new OnlineClass(2, "spring data jpa", true));
		springClasses.add(new OnlineClass(3, "spring mvc", false));
		springClasses.add(new OnlineClass(4, "spring core", false));
		springClasses.add(new OnlineClass(5, "rest api development", false));

		Stream<OnlineClass> streamVariable = springClasses.stream().filter(o -> o.getTitle().contains("spring"));

		System.out.println("====");
		streamVariable.map(o -> o.getTitle()).forEach(System.out::println);
	}
}
```
