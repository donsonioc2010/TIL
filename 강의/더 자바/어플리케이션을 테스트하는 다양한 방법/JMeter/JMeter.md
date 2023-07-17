# JMeter

## JMeter란

> **성능 측정** 및 **부하(load)** 기능을 제공하는 아파치에서 제작한 오픈소스 자바 어플리케이션이며, CLI환경을 지원한다.

## 성능 측정이 가능한 프로토콜 종류

- HTTP, HTTPS
- JDBC
- SOAP
- FTP
- Mail
- TCP
- Others...

## JMeter의 대체체

- LoadRunner (HP)
- Locust
  - 파이썬 스크립트로 작성해야한다.
- nGrinder
- JMeter
- Gatling
- Otehrs...

## 중요 개념

### Thread Group

> 한 개의 Thread당 유저를 한명으로 정의한다.

### Sampler

> 각각의 사용자가 `어떤 액션을 취해야 하는가`라는 설정, `HTTP요청을 보낸다`도 한개의 Sampler가 될 수 있다.

### Listener

> Response를 받았을때 해야하는 작업을 정의한다.
>
> > 응답의 내용을 처리한다든가, 시간을 측정한다든가, 결과를 종합해서 그래프나 리포트를 생성한다든가 등

### Configuration

> Sampler나 Listener가 사용할 설정이 있는 경우, 설정을 해주는 작업, (HTTPHeader, 쿠키 등등)

### Assertion

> 응답이 성공적인 결과인지 아닌지를 확인하는 방법
