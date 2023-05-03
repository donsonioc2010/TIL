# JWT

## Index

- [JWT](#jwt)
  - [Index](#index)
  - [JWT란?](#jwt란)
    - [공개문서의 소개](#공개문서의-소개)
    - [JWT정의 요약](#jwt정의-요약)
  - [JWT를 사용하는 이유?](#jwt를-사용하는-이유)
    - [공식문서 의견](#공식문서-의견)
    - [사용하는 이유 요약](#사용하는-이유-요약)
  - [JWT의 구조?](#jwt의-구조)
    - [JWT의 표현방법](#jwt의-표현방법)
      - [JWT Example](#jwt-example)
    - [Header](#header)
      - [Header의 Attribute종류](#header의-attribute종류)
      - [Header Example](#header-example)
      - [알고리즘의 종류들?](#알고리즘의-종류들)
      - [알고리즘의 적용 하는 경우](#알고리즘의-적용-하는-경우)
    - [Payload](#payload)
      - [Registerd Claim](#registerd-claim)
        - [Registered Claim Type](#registered-claim-type)
      - [Public Claim](#public-claim)
      - [Private Claim](#private-claim)
      - [Payload Example](#payload-example)
    - [Signature](#signature)
  - [JWT토큰의 통신 활용](#jwt토큰의-통신-활용)
    - [How To Using JWT Base By Http, Spring](#how-to-using-jwt-base-by-http-spring)
    - [Spring에서의 처리방법](#spring에서의-처리방법)
  - [JWT의 장단점](#jwt의-장단점)
    - [장점](#장점)
    - [단점](#단점)
  - [Reference](#reference)

## JWT란?

> JSON Web Token의 약자로, [JWT Introduction Docs](https://jwt.io/introduction)페이지로 공식홈페이지에서 소개하고 있는 원문에서 알수 있었다.

### 공개문서의 소개

> JWT는 개방형 표준(공개표준, Open Standard) RFC7519로서, 당사자(서로 개체)간에 JSON 객체를 사용해 안전하게 전송하기 위한 소형<small>(compact)</small> 및 자가수용적<small>(self-contained)</small> 방식이다.
>
> 또한 정보는 디지털 서명이 되어있어, 신뢰가 가능하며 JWT는 HMAC알고리즘을 사용하거나 또는 RSA, ECDSA를 사용해 공개 / 개인 키를 쌍으로 사용하여 서명이 가능하다.
>
> 당사자 간에 비밀 유지를 위해 JWT를 암호화 할 수도 있지만, 서명된 토큰에 초점을 맞춘다. 서명된 토큰은 해당 토큰에 포함된 무결성의 확인이 가능하며, 암호화 된 토큰은 다른 당사자의 클레임을 숨길 수도 있다.
> 공용 / 개인 키 쌍을 사용하여 서명 될 때 서명은 개인 키를 보유한 당사자만 서명한 것을 인증한다.

### JWT정의 요약

> JWT는 여러 개체간에 JSON객체를 바탕으로 사용자에 대한 속성을 저장하는 Claim기반의 Web Tokendlau, JWT는 필요한 모든 정보를 자체적으로 지니고 있으며, 해당 정보는 JWT시스템에서 발급된 토큰은 토큰에 대한 기본정보, 전달 할 정보, 그리고 토큰이 검증된 것을 증명하는 Signature를 포함한다. Signature는 발급자를 의미

---

## JWT를 사용하는 이유?

### 공식문서 의견

JWT공식문서의 추천으로는 다음 2가지의 경우 JWT를 사용하라 권장하였다.

- 권한 부여<small>(Authorization)</small>
  - JWT의 가장 일반적인 시나리오로, 사용자가 로그인하면 각 후속 요청에 JWT가 포함되어 해당 토큰으로 허용된 경로, 서비스 및 리소스에 액세스가 할 수 있다.
  - SSO(Single Sign On)는 오버헤드가 작고 다양한 도메인에서 쉽게 사용이 가능하기 때문에 현재 JWT를 널리 사용하는 기능이다.
- 정보 교환<small>(Information Exchange)</small>
  - 당사자 간에 정보를 안전하게 전송하는 좋은 방법중 하나로, JWT는 공용 / 개인키 쌍을 사용해 서명할 수 있으며, 발송자(또는 발송지, 발송처)가 내가 알고 있는 사람이라는 확신이 가능하다.
  - 서명의 경우 헤더와 페이로드를 사용해 계산되므로 내용이 변경되지 않았는지 확인도 가능하다.

### 사용하는 이유 요약

- 로그인기능과 정보교환에 주로 사용하면 된다.
- 스프링 개발자로 기능개발할때 Security에서 Response Header에 유저확인용으로 주로 많이 쓸듯하다.
- 최근 개발추세가 Stateless, Session을 사용하지 않는 방향(MSA)로 가고 있고 해당 중심에 JWT가 많은 역할을 한다 생각함.
  - Session의 경우에도 잦은 입출력을 하는 경우가 많기 때문에 Memory를 활용하는 경우가 많고 그 중심은 Redis라 생각함.

---

## JWT의 구조?

> JWT는 간결한 폼을 가지고 있으며, 세개의 파트가 Dot(.)으로 구분되며 영역은 아래와 같다.

- Header
- Payload
- Signature

### JWT의 표현방법

`xxxx(Header).yyyy(Payload).zzzzzz(Signature)`

#### JWT Example

eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c

다음의 예제가 있을 때 위의 [JWT의 표현방법](#jwt의-표현방법)에 의해 구분을 다음의 영역으로 구분된다.

- 붉은색 : Header
- 파란색 : Payload
- 초록색 : Signature

<font color="red">eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9</font>.<font color="blue">eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ</font>.<font color="green">SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c</font>

### Header

> Header에는 일반적으로 두개의 구성으로 이뤄저있으며, 토큰유형과 사용중인 서명 알고리즘<small>(SHA256, RSA)</small>의 두부분으로 구성된다

#### Header의 Attribute종류

- `alg` :알고리즘 종류
- `typ` :토큰 타입

#### Header Example

```JSON
{
    "alg": "HS256",
    "typ": "JWT"
}
```

#### 알고리즘의 종류들?

> 일반적으로 서용 할 수 있는 [공식문서](https://jwt.io/)에 등재된 알고리즘 종류에 대해서만 나열하였다.

> 알고리즘의 뒤의 S256, S384, S512는 SHA256, SHA384, SHA512를 의미한다.
> SH{Number}의 경우 임의의 메세지를 {Number} bit의 축약된 메세지로 만들어내는 해시 알고리즘이며, 각각 32바이트, 48바이트, 64바이트를 의미한다.

- HS256, HS384, HS512
  - H는 HMAC<small>(Hash-basedMessaqe Authentication Code)</small>를 의미
  - 서버와 클라이언트 간에 공유된 비밀키를 사용하여 서명을 생성 및 검증한다.
- RS256, RS384, RS512
  - RSA알고리즘을 의미하며 비대칭 키 쌍을 사용하여 JWT를 서명 및 검증한다.
  - 서버는 비밀키로 서명생성하며, 클라이언트는 공개키로 검증한다.
  - 서버키는 숨길수있기 때문에 Back, Front에서 주로 사용할수있을듯하다..?
- ES256, ES384, ES512
  - ECDSA<small>(Elliptic Curve Digital Signature Algorithm)</small>알고리즘을 의미
  - 타원 곡선 암호화를 기반으로 한 대칭키 알고리즘이다
- PS256, PS384, PS512
  - RSA-PSS<small>(RSA-Probabilistic Signature Scheme)</small>을 이며 RSA-PSS-SHA25이며, 해당 알고리즘을 사용하여 JWT를 서명 및 검증하는 것을 의미한다.
  - RSA-PSS는 RSA와 해시함수를 결합한 서명 알고리즘이다.

#### 알고리즘의 적용 하는 경우

> 해당 영역은 내 개인적인 주관으로, 일단 내가 주로 사용하게 될 알고리즘은 아마 HS256또는 RS256이 될 것 같으며 다음의 경우에 해당 알고리즘을 채택 할 것 같다.

- HS256
  - 각 두 객체에 SecretKey를 공유하고 해당 키를 서명 검증키로 활용되도 상관이 없는 경우 즉 서버간의 통신만 존재하고 해당 사용자가 누군지 명확할때 주로 사용할듯 하다.
- RS256
  - 사용자를 통제하지 못하는 경웅 주로 활용할 듯 하며, 특히 웹에서 사용을 많이 하지 않을 듯하다..

### Payload

- Payload영역의 경우에는 '클레임<small>(Claim)</small>'을 포함하고 있다.
- 클레임<small>(Claim)</small>은 엔티티 및 추가 데이터에 대한 데이터를 담고 있다.
  - 해당 형태또한 JSON형태로 Key, Value형태의 한개의 쌍으로 이뤄저 있다.
- 클레임은 여러개를 넣을 수 있으며, 종류는 다음 세가지로 분류되어 있다.
  - [Registered Claim](#registerd-claim)
  - [Public Claim](#public-claim)
  - [Private Claim](#public-claim)

#### Registerd Claim

> 해당 클레임은 서비스에 필요한 정보들이 아닌, 토큰에 대한 정보들을 담기 위한 것이다.
>
> > <span style="color:red; font-weight:bold;">이름이 이미 정해진 클레임들</span>이다.
>
> > 해당 클레임들은 <span style="color:red; font-weight:bold;">모두 Optinal</span>하게 사용이 가능하기 떄문에 무조건적인 필수는 아니다.

##### Registered Claim Type

- `iss` <small>(Issuer)</small>
  - 토큰 발급자
- `sub` <small>(Subject)</small>
  - 토큰 제목
- `aud` <small>(Audience)</small>
  - 토큰 대상자
- `exp` <small>(Expiratiion Time)</small>
  - 토큰의 만료 시간이며, 시간은 NumbericDate형식으로 이뤄저 있어야한다.
  - 클레임의 설정된 시간은 항상 <span style="color:red">현재시간보다 이후</span>로 설정이 되어 있어야 한다.
- `nbf` <small>(Not Before)</small>
  - 토큰의 이전시간에 대해서는 처리가 불가능하도록 하는 클레임이며, 시간은 `exp`와 동일하게 NumbericDate형식으로 이뤄진다.
  - 위에서의 설명처럼 설정된 날짜가 지나기 전에는 토큰이 처리되지 않는다.
- `iat` <small>(Issued At)</small>
  - 토큰이 발급된 시간이다.
- `jtl` <small>(JWT ID)</small>
  - JWT의 고유 식별자로 주로 중복적인 처리를 방지하기 위해 사용된다.
  - 특히 <span style="color:red; font-weight:bold;">일회용 토큰</span>에 사용하면 유용하다.

#### Public Claim

> 클레임의 명칭은 JWT를 이용하는 사용자가 정의를 할 수 있다. 하지만, 충돌을 방지하기 위해서 새로운 클레임의 명칭은 <span style="color:red; font-weight:bold;">IANA</span> [Registry에 등록](https://www.iana.org/assignments/jwt/jwt.xhtml)되어있는 ([공식문서 섹션 10.1](https://datatracker.ietf.org/doc/html/rfc7519#section-10.1) 같은 페이지)에 사용된 명칭을 따르거나 아니면 URI형식으로 키를 지정하는게 좋다.

#### Private Claim

- 등록된 클레임도, 공개된 클레임도 아니다.
- 양 측간 <span style="color:red; font-weight:bold;">협의하</span>에 사용되는 클레임 명칭들이다.
  - 공개 클레임과는 달리 이름이 중복되어 충돌의 발생이 있을 수 있어 사용에 주의를 해야한다.
  - 좋은 예시는 Client ↔ Server

#### Payload Example

> 아래의 예제는 2개의 등록 클레임, 1개의 공개 클레임, 1개의 비공개 클레임으로 등록되어 있다.

```JSON
{
    "iss":"devJong1.com",
    "sub": "1234567890",
    "https://devjong1.com/is_admin" : true,
    "username": "PrivateClaim Jong1",
}
```

### Signature

> Signature부분을 만들려면, Encode된 Header, Encode된 PayLoad, Secret을 가져와서 서명을 해야한다.  
> Secret의 경우에도 Encode가 가능하다.
>
> HS256을 사용할 때를 기준으로 서명시 다음과 같은 방법으로 서명이 생성된다
>
> > ```
> > HS256 (
> >    base64UrlEncode(header)+ "." + base64UrlEncode(payload),secret
> > )
> > ```

---

## JWT토큰의 통신 활용

> 개발이 어떻게 진행되는지에 따라 다르지만 다음과 같은 방식으로 활용하는게 좋다고 생각된다.
>
> JWT의 예제는 [JWT Token](#jwt-example)를 기반으로 설명하며 HTTP통신, Spring을 기준으로 설명을 이어가려 한다.

### How To Using JWT Base By Http, Spring

> HTTP의 경우 통신을 할때 **Request**와 **Response**가 존재하며, 각 요청은 또 하위에 **Header**와 **Body**로 이뤄져 있다.

> 여기서 내가 눈여겨 본사항은 **RequestHeader** 영역이다. 또한 통신을 할 때 Authorization항목이 존재하며, 아래의 이미지는 Postman에서 실제 항목이 존재하는걸 스크린샷으로 찍었다.
> ![Authorization](./%08Header_Authorization.png)

> 해당 값을 기준으로 JWT를 담아서 사용하면 되며 JWT의 Value규칙은 `"Bearer {JWT TokenValue}"`형식으로 Value를 제작한다

### Spring에서의 처리방법

> 인터셉터를 활용하여 요청이 들어올 때 RequestHeader항목을 미리 읽어서 정규 검사를 진행하고 Request요청을 진행한다.
>
> 만약 시간이 만료된 경우에는 Refresh Token과 함게 재검증을 진행한다.

> Refresh Token을 바탕으로 한 기능 구현까지는...생각해보는게 좋을 듯하다.
>
> > Refresh Token을 Redis에서 관리하는 방법으로, DB로 관리하는방법으로는 본인의 환경에 맞추서 진행하자..
> >
> > > 일단 JWT의 목표는 Stateless 이기 때문에 굳이 저장에 필요는 없을 수도 있지만, DB의 부하를 막을 수 있는 방법으로 있지 않나 라는 생각이 든다.. 요청이 너무 많아지면 잦은 IO로 인한 부하도 발생 할 수 있기 떄문이니..

---

## JWT의 장단점

### 장점

- JWT의 이점은 사용자 인증에 필요한 모든정보를 토큰이 자체적으로 포함하기 떄문에 별도의 인증 저장이 필요 없긴 하다.
- 쿠키의 전달이 필요 없으므로 쿠키를 사용시 발생되는 취약점이 사라진다.
- URL파라미터와 헤더를 활용한다.
- Session의 사용이 없는 것도 가능하기에 부담이 적다
- 전부 Rest로 관리가 가능

### 단점

- 토큰이 모든 정보를 자체적으로 담고 있는것
  - 토큰을 제작하는 규칙 생성시 생각해 봐야 할 요소라 생각한다.
  - PayLoad영역에 대해 중요 데이터를 쉽게 볼 수 있기 떄문에 중요데이터의 노출이 문제되기 때문
- Stateless가 역으로 발목을 잡는다고 생각도 하는데, JWT는 상태를 저장하지 않기 때문에 한번 만들어지면 제어가 불가능하다.즉 토큰을 임의 삭제는 불가능 하기 떄문에 <span style="color:red; font-weight:bold;">토큰 만료 시간</span>을 꼭 활용하자..

---

## Reference

1. [Open Standard에 대한 설명](https://en.wikipedia.org/wiki/Open_standard)
2. [JWT 소개 공식문서](https://jwt.io/introduction)
3. [RFC7519에 대한 문서](https://datatracker.ietf.org/doc/html/rfc7519)
4. [SHA알고리즘과 관련한 문서](http://wiki.hash.kr/index.php/SHA256)
5. [세부적으로 번역된 JWT의 소개 Velog](https://velog.io/@dnjscksdn98/JWT-JSON-Web-Token-%EC%86%8C%EA%B0%9C-%EB%B0%8F-%EA%B5%AC%EC%A1%B0)
