## **Chap.11 : 1인개발시 도움이 될 도구 그리고 조언**

**도구의 소개**

> 저자의 경우 1인 프로젝트를 진행시에 메인이 되는 기능 외에는 전부 외부 서비스에 의존해 개발한다고 한다. 외부서비스 종류들에 대한 기록드리다.
> 서비스 기능들에 대해서는 외부 서비스를 통해서 개발을 진행하는 것에 대해서도 추천한다.

> 댓글기능 활용할 예정이라면 Github를 기반으로 댓글을 남길수 있는 서비스인 [Utteranc](https://utteranc.es/) 를 활용하자

> 방문자 분석 툴의 경우에는 구글 애널리스트를 활용하자.

> `CDN`은 `Content Delivery Network`의 약자로 전 세계에 분산되어 있는 서버 네트워크 이다.  
> `서비스를 운영하던 서비스가 작동되는 서버`가 어디 있느냐에 따라 `한국에서 접속` 할 때, `해외에서 접속`할 때 `속도의 차이`가 발생 할 수밖에 없다.  
> 그리하여 흔히 `정적 컨텐츠`라고 불리는 `JS`,`CSS`, `이미지`등을 전세계에 퍼진 서버에 전달하여 사용자가 `서비스에 접속`시 가장 가까운 서버에서 가져가도록 지원하는 서비스가 `CDN`이며, 목적은 트래픽의 분산이다.
> `CDN`에서 정적 파일들을 전달하다 보니 서비스 서버에서는 API요청만 서버에서 받아주면 되니 비용, 속도가 많이 절약되며 대표적인 서비스는 `클라우드 플레어`이다.

> 뉴스레터 형식의 메일 발송 시스템을 `이메일 마케팅` 종류라고 한다. 해당 서비스로 유명한건 [Mailchimp](https://mailchimp.com/)이다.

**조언?**

1. 1인개발을 할 것이라면 핵심 기능을 제외한 나머지는 모두 무시하고, 욕심을 부리지 마라.
2. 여러가지를 고려하게 되면 결국 구상만 하다가 출시를 못한다.
3. 가장 자신있는 환경으로 개발해라
   1. Exam으로 React, Vue같은거보다 jQuery가 익숙하면 그걸 써라. 그이후 변경하라.
