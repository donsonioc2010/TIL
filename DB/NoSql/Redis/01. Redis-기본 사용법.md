# Redis

## Index

- [Redis](#redis)
  - [Index](#index)
  - [Redis란?](#redis란)
    - [특징](#특징)
    - [단점](#단점)
      - [시간복잡도가 높은 명령어](#시간복잡도가-높은-명령어)
    - [주의점](#주의점)
  - [Cache란?](#cache란)
    - [Cache를 사용하는 이유?](#cache를-사용하는-이유)
    - [Cache 사용 패턴](#cache-사용-패턴)
      - [Look aside Cache 패턴](#look-aside-cache-패턴)
        - [동작 순서](#동작-순서)
      - [Write Back 패턴](#write-back-패턴)
        - [패턴의 사용 경우](#패턴의-사용-경우)
        - [패턴의 장.단점](#패턴의-장단점)
        - [동작순서](#동작순서)
    - [Cache의 주 사용처](#cache의-주-사용처)
  - [Session Store](#session-store)
    - [Session Store를 사용하는 주된 이유](#session-store를-사용하는-주된-이유)
  - [Mac에서의 Redis 관리](#mac에서의-redis-관리)
    - [설치](#설치)
    - [시작, 종료 및 재시작](#시작-종료-및-재시작)
      - [ForeGround로 실행방법](#foreground로-실행방법)
    - [터미널 접근](#터미널-접근)
  - [Redis 사용법](#redis-사용법)
    - [String](#string)
      - [SET](#set)
      - [GET](#get)
      - [INCR, DECR](#incr-decr)
      - [APPEND](#append)
      - [STRLEN](#strlen)
      - [MSET, MGET](#mset-mget)
      - [EXISTS](#exists)
      - [DEL](#del)
      - [EXPIRE](#expire)
    - [Hash](#hash)
      - [HSET](#hset)
      - [HGET, HMGET, HGETALL, HVALS](#hget-hmget-hgetall-hvals)
  - [Reference](#reference)

## Redis란?

> Redis는 오픈소스로, 메모리를 사용한 데이터 베이스로, **Remote Dictionary Server**의 약자이며, **원격 Dictinary 자료구조 서버**라는 보다 직관적인 명칭을 가지고 있다.

### 특징

- Key-Value 구조
  - 일반적인 DB의 쿼리 연산같은 기능이 제공되지 않는다.
  - **Key**는 문자열이지만, Value는 다양한 타입을 지원한다.
- 고속 **읽기**와 **쓰기**에 최적화 되어있다.
  - MySQL대비 10배의 속도 차이가 존재한다.
    - SSH, HDD같은 디스크를 사용하는게 아닌 **메모리 레벨을 활용**하기 떄문에 속도차이가 발생할 수밖에 없다
  - 쓰기 증대를 위하여 Client측에 샤딩(Sharding)을 지원한다
    - Sharding이란 같은 스키마를 가진 데이터Row를 다수의 DB에 분산해 저장하는 기법
- **Cache**, **Session**, **DB**등 다양한 용도로 활용이 가능하다
- 메모리 기반이지만, Redis는 **영속성 데이터 보존**이 가능하다
  - 메모리는 원래 휘발성이지만, Redis에서는 디스크에 백업옵션을 제공한다.
  - **스냅샷**을 제공하여 메모리 내용을 **.rdb**파일로 제공하며, 해당 시점 복구가 가능하다
- 기본적으로 **싱글 쓰레드**로 수행이 되기 때문에 *안정적인 인프라 구축*을 위해서는 **Master-Slave구조가 필수**이다.

### 단점

- 싱글 쓰레드를 기반으로 수행된다.
  - 즉 하나의 명령만 실행하기 때문에, **긴 시간복잡도가 소요되는 명령어를 사용**하는 경우 **요청건을 처리 전까지 다른 요청을 받을 수 없다**
  - 다른 서비스 요청을 못받으면서 ㅅ버가 다운되는 현상이 발생 할 수 있음

#### 시간복잡도가 높은 명령어

> Redis의 대부분의 명령어는 O(1)이지만, O(N)의 명령어들이 시간복잡도가 높게 발생 할 수 있으며, 다음과 같다.

- `HGETALL key`
  - 해시 키에 저장된 모든 필드와 값을 가져옵니다. (N은 필드의 수)
- `LRANGE key start stop`
  - 리스트의 범위에서 요소를 가져옵니다. (S는 시작 위치, N은 반환되는 요소의 수)
- `SADD key member1 member2 ...`
  - (처음에만 O(N))
  - 셋에 멤버(요소)를 추가합니다.
- `SREM key member1 member2 ...`
  - (처음에만 O(N))
  - 셋에서 멤버(요소)를 제거합니다.
- `SMEMBERS key`
  - 셋에 속한 모든 멤버를 가져옵니다. (N은 셋의 크기)

### 주의점

내가 찾지는 못했다.

> Redis튜토리얼에서 적적한 Key에 대한 충고는 다음과 같다

- Key, Value의 최대 길이는 512MB이다
- 매우 긴 키는 좋지 않다
  - 키를 조회시 비교를 할 때 길게 될 경우 고비용의 비교 로직을 실행해야 하기 때문
  - 키값이 길다면 차라리 SHA-1등으로 해시를 추천
- 매우 짧은 키값 또한 좋지 않다
  - 가독성을 해치면서 글자를 줄이면 이해가 힘들어 지기 ㄷ때문

---

## Cache란?

> Cache란 **한번 조회된 데이터를 미리 특정 공간에 저장** 후, **똑같은 요청이 발생하게 되면 서버에 요청**한게 아닌, **저장한 데이터를 제공**해서 좀 더 빠르게 서비스를 제공해주는 것을 의미한다.

### Cache를 사용하는 이유?

> 서비스의 **요청이 적은 경우에는 필요성이 없다** 느낄 수 있지만, 서비스가 **커지고 분산환경이 필요**하다 보면 *DB만으로 유지를 하기에는 서비스에 부하*가 오게 된다. 이 때 **DB를 스케일 업**만 하기에는 **한계가 발생**하여 **캐시서버**의 도입을 생각 해 볼 수 있게 된다.

### Cache 사용 패턴

#### Look aside Cache 패턴

> [Cache를 사용하는 이유?](#cache를-사용하는-이유)의 캐시 정의된 구조 그자체인 패턴이다.

##### 동작 순서

- 데이터가 캐시(Redis)에 존재하는 경우
  1.  클라이언트 → 서버로 데이터 요청
  2.  서버에서 캐시(Redis)로 데이터 유무 확인
  3.  서버 → 클라이언트로 캐시(Redis)에서 가져온 데이터 반환
- 데이터가 캐시(Redis)에 없는 경우
  1.  클라이언트 → 서버로 데이터 요청
  2.  서버에서 캐시(Redis)로 데이터 유무 확인
  3.  데이터가 없는 경우 DB커넥션 및 데이터 획득
  4.  서버 → 클라이언트로 DB에서 가져온 데이터 반환

#### Write Back 패턴

> Write Back은 주로 쓰기 작업이 많아서, **INSERT 쿼리를 일일이 날리지 않고 한꺼번에 배치 처리를 할 때** 활용한다.

##### 패턴의 사용 경우

> 동시에 Request가 요청되는 서비스에서 **해당 요청을 일일이 DB에 요청을 하게 될 경우 무수한 요청으로 인하여 DB가 죽는 경우가 발생** 할 수 있다. 이때 Write Back기반의 캐시를 사용하게 되면 DB에 안정적인 업데이트 작업이 가능하다.

> **INSERT**또는 **UPDATE**, **DELETE**가 1개씩 개별로 100여번 수행되는 것보다는, 100여개를 모았다가 한번에 일괄적으로 데이터를 처리하는 것이 동작이 훨씬 빠르기 때문

##### 패턴의 장.단점

- 장점
  - DB로 직접적인 접근을 하는 횟수가 줄어들기 때문에 직접적인 성능 향상을 기대 할 수 있다.
- 단점
  - DB에 데이터를 저장하기전에 Redis서버가 죽어버리면 데이터가 유실된다.
    - 보통 재생 가능한 데이터, 극단적으로 무거운 데이터에서 해당 방식을 많이 활용한다.
    - 로그같은 것을 캐시에 저장하고 한번에 저장하는 경우등

##### 동작순서

1. Web Server에서 Request에 대한 데이터를 모두 캐시(Redis)에 저장
2. 일정 시간, 요청 수량의 데이터를 저장
3. 캐시(Redis)에 있는 데이터를 DB에 저장
4. DB저장된 이후 캐시(Redis)에 있는 데이터 중 저장된 데이터 삭제

### Cache의 주 사용처

- 인증 토큰 저장
- 실시간 순의등 (Sorted-Set)
- 유저 API Limit제한

---

## Session Store

### Session Store를 사용하는 주된 이유

> 대규모의 트래픽을 처리하기 위해서는 보통 **분산 처리 환경**을 사용하는 경우가 많다.
> 분산 처리 환경이라는 의미답게 서버를 여러대 운영하게 되는 것인데, 해당 기법을 활용 할 경우 여러대를 운영하게 되면서 요청되는 서버가 서로 달라지게 되면 **세션의 불일치** 현상이 나오게 된다.

> 위와 같은 **세션의 불일치**문제를 해결하는 기법중 한개가 Redis를 세션 스토리지로 활용한 방법이다.

---

## Mac에서의 Redis 관리

> 설정하지 않은 경우 Default Port는 **6379** 이다.

### 설치

- `brew install redis`

### 시작, 종료 및 재시작

- `brew services start redis`
- `brew services stop redis`
- `brew services restart redis`

#### ForeGround로 실행방법

- `redis-server`

### 터미널 접근

- `redis-cli`

---

## Redis 사용법

### String

#### SET

> 값을 추가, 수정 할 때 사용

```
SET [KeyName] [Value] [Option]
SET mac jong1
SET mac jong2 nx
SET mac jong2 xx
```

- Option
  - `nx` : 값이 이미 있는 경우 실패하고 Update를 하지 않는다
  - `xx` : 값이 이미 있는 경우 성공하고 Update를 진행한다

#### GET

> 값을 획득 할 때 사용

```
GET [KeyName]
GET mac
```

#### INCR, DECR

> Value가 정수형인 경우 활용할 수 있으며, 정수형이 아닌 경우는 오류가 발생한다.

```
INCR [KeyName]
DECR [KeyName]
INCR num
DECT num
```

- 정수가 아닌 경우
  - `(error) ERR value is not an integer or out of range`

#### APPEND

> 기존 키의 값에 새로운 값의 추가가 필요한 경우 사용

```
APPEND [KeyName] [AddValue]
APPEND mac jong33
```

#### STRLEN

> 키에 저장된 Value의 Lenght가 필요한 경우 사용

```
STRLEN [KeyName]
STRLEN mac
```

#### MSET, MGET

> 여러 Key, Value를 셋팅 하던가, 가져오고 싶을 때 Multi Set, Get의 활용한다.

```
MSET [KeyName1] [Value1] [KeyName2] [Value2] [KeyName3] [Value3] ...

MGET [KeyName1] [KeyName2] [KeyName3] ...

MSET mac1 j1 mac2 j2 mac3 j3
MGET mac mac1 mac2 mac3
```

#### EXISTS

> Value의 존재 여부를 반환하며, 여러개의 확인도 가능하다, 존재여부에 대해서 1, 0으로 반환하며, 복수개를 설정한 경우 복수개의 결과를 모두 합친 값을 Return 한다

```
EXISTS [key1] [key2] [key3] ...
EXISTS mac mac1 mac2 mac3 mac4  // Return: (integer) 4
EXISTS mac4 // Return: 0
```

#### DEL

> Key를 삭제할떄 활용하며, 값이 존재하여 삭제에 성공시 1, 값이 없어서 실패시 0을 반환한다.

```
DEL [key1] [key2] [key3] ...
DEL mac mac1 mac2 mac3 mac4 // Return: (integer) 4
```

#### EXPIRE

> 만료시간 설정이며, 단위는 초단위이다.

```
EXPIRE [keyName] [Integer] //정수형이 Expire타임이며, 초단위 이다.

EXPIRE mac1 5 //5초의 유예시간
```

### Hash

> Redis의 경우에는 해쉬화를 해서 Key, Value의 삽입이 가능하다.
> 키값은 임의 지정이 가능하다.

#### HSET

> SET의 옵션 활용은 불가능 하다.

```
HSET [hashKey] [key1] [value1] ([key2] [value2] ...)
HSET hashkey mac jong1
HSET hashkey mac1 j1 mac2 j2 mac3 j3 mac4 j4
```

#### HGET, HMGET, HGETALL, HVALS

> 해쉬화된 키에 저장된 값을 조회하며, `HGET`은 단건, `HMGET`은 요청한 건수만큼, `HGETALL`과 `HVALS`의 경우에는 모든 건을 반환하며, `HGETALL`은 Field와 Value를 모두 반환하고 `HVALS`의 경우에는 값만 출력한다.

```
HGET [hashKey] [key]
HMGET [hashKey] [key1 ...]
HGETALL [hashKey]
HVALS [hashKey]

HGET hashkey mac1
HMGET hashkey mac1
HMGET hashkey mac1 mac2 mac3 mac4
HGETALL hashkey
HVALS hashkey
```

---

## Reference

[Redis공식문서](https://redis.io/)
[(AWS)What is Redis](https://aws.amazon.com/ko/elasticache/what-is-redis/)
[Redis Reference 1](https://sihyung92.oopy.io/database/redis/1)
[Redis Reference 2](https://inpa.tistory.com/entry/REDIS-%F0%9F%93%9A-%EA%B0%9C%EB%85%90-%EC%86%8C%EA%B0%9C-%EC%82%AC%EC%9A%A9%EC%B2%98-%EC%BA%90%EC%8B%9C-%EC%84%B8%EC%85%98-%ED%95%9C%EB%88%88%EC%97%90-%EC%8F%99-%EC%A0%95%EB%A6%AC)
