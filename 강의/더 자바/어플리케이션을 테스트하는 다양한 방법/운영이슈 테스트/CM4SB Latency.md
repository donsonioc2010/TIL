# CM4SB Latency

> 응답지연에 대한 테스팅 방법들

## 응답 지연 이슈의 재현 방법

- Repository Watcher 활성화

  - `application.yml`에 `chaos.monkey.watcher.repository=true`항목 추가

- Chaos Monkey 활성화

  - POST요청으로 localhost:8080/actuator/chaosmonkey/enable
    - PostMan, Http, Curl등 다 사용 가능하다.
  - Chaos Monky Status 확인 방법
    - GET요청 localhost:8080/actuator/chaosmonkey/status
      - 비활성화 되어있는 경우 : `You switechd me off`라는 문구가 출력된다.
      - 활성화가 되어있는 경우 : `Ready to be evil` 라는 문구로 안내한다

- Chaos Monkey의 Watcher Enable된 속성 확인 커맨드

  - GET요청 localhost:8080/actuator/chaosmonkey/watchers
  - POST요청 localhost:8080/actuator/chaosmonkey/watchers service=false 식으로 보내게 될 경우 True인 Watcher를 False로 변환이 가능하다
    - Disable은 Runtime중 가능하나, Enable은 Runtime중 변경이 불가능 하다.

- Chaos Monkey 지연 공격 설정

  - POST요청 localhost:8080/actuator/chaosmonkey/assaults level=3 latencyRangeStart=2000 latencyRangeEnd=5000 latencyActive=true
    - 의미 번역 : 3번 요청마다 한번씩 level뒤의 옵션들에 대한 공격을 하라는 의미이다.
    - latencyRangeStart, End는 start시간에서부터 End시간 사이로 지연 공격을 하라는 의미
    - latencyActive를 활성화 또는 비활성화 시키는 의미

- Chaos Monkey 공격 설정 확인 방법
  - GET요청 localhost:8080/actuator/chaosmonkey/assaults 를통한 확인
