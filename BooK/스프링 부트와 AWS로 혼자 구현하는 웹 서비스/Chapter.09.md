## **Chapter. 09 : 코드가 푸시되면 자동으로 배포하기 - Travis CI를 통한 배포 자동화**

> 24시간 365일 운영되는 서비스에서는 `CI / CD` 환경 구축은 선택이 아닌 필수이며, 책에서의 예제는 `Travis CI`를 활용해 구축할 예정이다.  
> 또한 `8장`에서 직접 경험을 해봤기 때문에 알 수 있듯이 `배포 스크립트`를 통한 배포는 개발자가 직접 실행을 해야하는 불편함이 있기 때문에 더더욱 필요한 사항이다.

<br>

**`CI` / `CD`**

> `CI`란 `Continuous Integration`이라 칭하며 `지속적 통합`이라 칭한다.  
> 코드 버전 관리(VCS)를 하는 시스템(`Git`또는 `SVN`)에 `PUSH`가 되면 자동으로 테스트와 빌드가 수행되는 것을 의미한다.

> `CD`란 `Continous Deployment`라 칭하며 `지속적인 배포`라고 칭한다.  
> `CI`의 과정(`테스트`와 `빌드`)가 전부 이뤄지면 `빌드 결과를 바탕으로 자동으로 운영서버에 무중단 배포까지 진행되는 과정`을 의미한다.

<br>

**`CI`와 `CD`의 역사? 개념이 나오게 된 배경**

> 현대의 웹 서비스의 경우 하나의 프로젝트를 다수의 개발자가 함께 개발을 진행하고 있다.  
> 그러다 보니 각자가 개발한 코드를 합쳐야 할 때 마다 큰 일 이었고, 병합일(코드를 Merge만 하는날)을 정해서 할 정도였다.
>
> 이런 작업은 생산성에 저하를 하는 요소이기 떄문에 CI환경이 구축된 것이다.
>
> 또한 CD의 경우에도 한두대의 서버에 개발자가 수동으로 배포는 할 수 있지만, 수십에서 수백대의 서버에 개발자가 직접 배포를 해야 하는 상황에서나 긴급하게 당장 배포하는 일을 수동으로만은 할 수 없게 되었고, 그렇기에 CD역시 구축을 하게 되었다.
>
> CI / CD 환경을 구축하게 되면 개발자가 갖게 되는 장점은 `개발자가 개발에만 집중 할 수 있다`는 장점이 생기게 된다.

<br>

**마틴 파울러의 CI에 대한 4가지 규칙**

> `CI도구를 도입했다고 해서 CI를 하고 있는 것은 아니다`를 명심하며, 마틴 파울러의 4가지 규칙을 기억하자.

1. `모든 소스코드가 살아(현재 실행되고) 있고` 누구든 `현재의 소스에 접근 할 수 있는 단일 지점을 유지`해야 한다.
2. `빌드 프로세스를 자동화` 해서 누구든 `소스로부터 시스템을 빌드하는 단일 명령어`를 `사용`할 수 있도록 한다.
3. `테스팅을 자동화` 해서 단일 명령어로 `언제든지 시스템에 대한 건전한 테스트 수트를 실행` 할 수 있게 한다.
4. 누구든 현재 실행 파일을 얻으면 지금까지 `가장 완전한 실행 파일을 얻었다는 확신`을 하게 한다.

> 여기서 3번 항목인 `테스팅 자동화`는 특히나 중요하다. 지속적인 통합을 하기 위해서는 이 프로젝트가 현재 `완전한 상태를 보장`해야 하기 위한 `테스트 코드`가 구현되어 있어야 하기 떄문이다.

<br>

**Travis CI?**

> Github에서 제공하는 무료 CI 서비스이며, Private Repository에는 다양한 유료플랜을, Public Repository에는 무료 플랜을 제공하고 있다.

<br>

**Travis CI 웹서비스 설정 방법**

1. https://travis-ci.org 접속 및 계정 생성
2. profile → settings
3. Repositories에서 Project 선택
4. 필자의 경우 `Build Pushed branches`와 `Build Pushed Pull Requests`에 대해서만 설정하였다.

**CI설정을 진행할 `.travis.yml` 파일 제작**

> 해당 파일은 `build.gradle`과 같은 파일에 있어야 한다.

```
//1번
language: java

//2번
jdk:
  - openjdk8

//3번
branches:
  only:
    - main

//4번
cache:
  directories:
    - '$HOME/.m2/repository'
    - '$HOME/.gradle'

//5번
script: "./gradlew clean build"

//6번
notifications:
  email:
    recipients:
      - whddnjs822@gmail.com
```

1. 자바 언어 설정
2. JDK 버전 설정
3. Travis CI를 어떤 브랜치가 푸쉬 될 때 수행할지 지정하며, 예제는 main브랜치가 푸쉬될때만 수행한다.
4. gradle을 통해 의존성을 받게 되면 이를 해당 디렉토리에 캐시하여 같은 의존성은 다음 배포때 부터 다시 받지 않도록 한 설정이다.
5. master브랜치에 푸시되었을 때 수행하는 명령어이며 프로젝트 내부에 둔 gradlew를 통해 clean & build를 수행한다.
6. Travis CI실행 완료시 자동으로 설정한 메일로 알람이 오도록 하는 설정

<br>

**AWS S3**

> S3란 AWS에서 제공하는 일종의 파일서버중 하나이다.
> 이미지 파일을 비롯하여 정적 파일을 관리하거나 예제에서 진행하는 것처럼 배포 파일들을 관리하는 등의 기능을 지원한다.
> 보통 이미지 업로드를 구현한다면 S3를 활용해 구현하는 경우가 많다.

<br>

**Travis CI와 AWS S3를 활용한 연동 구조**  
![이미지](https://raw.githubusercontent.com/smpark1020/tistory/master/CI%20%26%20CD/%5BTravis%20CI%5D%20%EC%BD%94%EB%93%9C%EA%B0%80%20%ED%91%B8%EC%8B%9C%EB%90%98%EB%A9%B4%20%EC%9E%90%EB%8F%99%EC%9C%BC%EB%A1%9C%20%EB%B0%B0%ED%8F%AC%ED%95%98%EA%B8%B0%203%20-%20Travis%20CI%EC%99%80%20AWS%20S3%20%EC%97%B0%EB%8F%99%ED%95%98%EA%B8%B0/1.jpg)

> Travis CI를 활용하게 될 경우 다음과 같은 구조가 이뤄지게 된다  
> 실제 배포에는 `AWS의 CodeDeploy` 서비스를 활용해야 한다. 하지만 S3연동이 먼저 필요한 이유는 `Jar파일의 전달`이 필요하기 때문이다.  
> `CodeDeploy`의 경우에는 `저장`기능이 없다. 그렇기 떄문에 Travis CI가 빌드한 결과물을 받아서 `CodeDeploy`가 가져갈 수 있도록 보관 할 수 있는 공간이 필요하며, 이때 `S3`를 활용한다.

> 추가적으로 `CodeDeploy`에서 빌드도 하고 배포도 할 수 있지만, `GitHub의 코드를 가져오는 기능`은 지원하지 않는다. 이렇게 되면 두개가 합쳐지기 때문에 빌드가 필요 없이 과거 버전에 대한 배포를 할떄 이전 빌드정보가 없기 때문에 대응하는게 힘들다.

> `빌드`와 `배포`가 분리되어 있으면 `과거에 빌드`되어 만들어진 파일(Jar, War)를 `재사용`하면 되지만 `CodeDeploy`가 모두 진행하게 되면

<br>

**AWS의 IAM을 사용하는 이유와 IAM에 대해서**

> 일반적으로 `AWS 서비스`에 `외부 서비스가 접근 할 수 없다.` 그렇기 때문에 `접근 가능한 권한을 가진 Key`를 `생성`해서 `사용`해야 한다.  
> AWS에서는 이러한 `인증과 관련된 기능을 제공하는 서비스`를 `IAM(Identity and Access Management)`가 존재한다.  
> IAM은 `AWS에서 제공하는 서비스`의 `접근 방식`과 `권한을 관리`한다.

> 예제에서 사용하는 `Travis CI`가 `AWS`의 `S3`와 `CodeDeploy`에 있도록 하기 위해서도 발급이 필요하다.

**AWS Key발급 방법(Travis CI Deploy적용방식)**

1. `AWS웹 콘솔`에서 `IAM`을 검색해 항목으로 이동한다.
2. 좌측 사이드바에서 `사용자` → `사용자 추가` 버튼을 차례로 클릭한다.
3. `사용자 이름 기입`과 `액세스키-프로그래밍 방식` 선택후 다음
4. `기존 정책 직접 연결` 선택 후 `AmazonS3FullAccess`, `AWSCodeDeployFullAccess`항목을 추가후 다음
   1. 실제 서비스 회사의 경우에는 `S3`와 `CodeDeploy`를 `분리해서 관리`하기도 하나, 예제의 경우에는 간단히 두개를 동시에 관리하도록 한다.
5. `태그`의 경우 키에 `Name`을 지정하고 값도 지정하나 예제의 경우에는 IAM 사용자이름과 일치시켰다.
6. 이후 정보확인을 하고 사용자 생성을 하면 `엑세스 키`와 `비밀 엑세스`키가 생성된다.

<br>

**Travis CI에서 Key설정**

1. `Travis CI`의 `Repository`별 Settings이동
2. `Environment Variables`항목에서 `NAME`을 `AWS_ACCESS_KEY`, `AWS_SECRET_KEY`로 발급받은 KEY값을 넣고 추가한다.
3. 해당 항목은 `.travis.yml`항목에서 `$AWS_ACCESS_KEY`, `$AWS_SECRET_KEY`라는 항목으로 활용이 가능하다

<br>

**S3 버킷 생성**

> S3는 일종의 파일서버로 순수하게 파일들을 저장하고 접근 권한을 관리, 검색등을 지원하는 역할을 한다.  
> S3는 보통 게시글을 쓸 때 나오는 `첨부파일 등록을 구현`할때 많이 이용된다.  
> Travis CI에서 생성된 Build파일을 저장하도록 예제는 구성을 하였다.

1. 버킷만들기 버튼을 통한 페이지 이동
2. 버킷 이름 생성
3. 버킷의 퍼블릭 액세스 차단 설정의 경우에는 `모든 퍼블릭 액세스 차단`으로 설정한다.
   1. 액세스의 차단이 중요한 이유는 실제 빌드후 배포해야 할 파일이 퍼블릭일 경우 누구나 내려받을 수 있기 떄문이다.
   2. 예제의 경우 IAM사용자를 통해 발급받는 키를 사용하기 때문에 접근 가능하다.

<br>

**.travis.yml추가**

> 다음과 같은 항목이 추가되었다.

```
before_deploy:
  - zip -r jong1-springboot-webservice *
  - mkdir -p deploy
  - mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip

deploy:
  - provider: s3
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    region: ap-northeast-2
    skip_cleanup: true
    acl: private
    local_dir: deploy
    wait_until-deployd: true
```

1. `before_deploy`
   1. deploy명령어가 실행되기전에 수행한다.
   2. CodeDeploy는 Jar파일을 인식하지못하므로 Jar + 기타 설정 파일들을 모아서 압축(zip)한다.
2. `zip -r jong1-springboot-webservice *`
   1. 현재위치의 모든 파일을 zip파일로 압축한다.
3. mkdir -p deploy
   1. deploy라는 디렉토리를 Travis CI가 실행중인 위치에 생성한다.
4. mv jong1-springboot-webservice.zip deploy/jong1-springboot-webservice.zip
   1. zip파일을 deploy디렉토리로 이동
5. deploy
   1. S3로 파일 업로드 혹은 CodeDeploy로 배포 등 외부 서비스와 연동될 행위들을 선언한다.
6. local_dir: deploy
   1. 앞에서 생성한 deploy 디렉토리를 지정한다.
   2. 해당위치의 파일들만 S3로 전송한다.

<br>

**Travis CI와 AWS S3, CodeDeploy 연동하기**

> CodeDeploy를 연동 받을 수 있게 하기 위해 역할의 추가가 필요하다.

> IAM에서 사용자와 역할은 차이가 존재한다. 역할의 경우에는 AWS서비스에만 할당할 수 있는 권한을 의미하며`EC2`, `CodeDeploy`, `SQS`등등
> 사용자의 경우에는 `AWS 서비스 외`에 사용할 수 있는 권한으로 `로컬PC`, `IDC서버등이있다` 현재의 경우 EC2에서 활용할 것이기 떄문에 역할로 처리해야 한다.

1. IAM이동
2. 역할 → 역할만들기
3. AWS서비스, EC2 선택후 다음
4. 권한은 AmazonEC2RoleforAWSCodeDeploy 선택 후 다음
5. 역할이름 기입및 태그Key, Value입력
6. EC2에서 적용을 희망하는 인스턴스 우클릭 → 보안 → IAM역할 수정 → 제작한 IAM역할로 업데이트
7. EC2 재부팅
   1. 재부팅을 하지 않을 경우 역할이 정상적으로 적용되지 않기 떄문

<br>

**CodeDeploy 에이전트 설치하기**

1. EC2접속
2. aws s3 cp s3://aws-codedeploy-ap-northeast-2/latest/install . --region ap-northeast-2
   1. 다운로드에 성공하면 `download: s3://aws-codedeploy-ap-northeast-2/latest/install to ./install` 다음과 같은 echo를 리턴받는다.
3. `chmod +x ./install`을 하여 다운로드 받은 설치파일의 권한증가
4. `sudo ./install auto`스크립트로 다운로드 진행
   1. `/usr/bin/env: ruby: No such file or directory` 오류가 발생하며, ruby가 설치되어있지 않아 발생하는 오류이다.
   2. 나의 경우 `sudo yum install ruby`를 통하여 `ruby`의 설치를 진행하였다.
5. `sudo service codedeploy-agent status`를 통하여 서비스 실행 여부 확인
   1. `The AWS CodeDeploy agent is running as PID XXXX` 서비스 실행 문구이다.

<br>

**CodeDeploy를 위한 권한 생성**

> CodeDeploy에서 EC2에 접근하려면 마찬가지로 권한이 필요하다. AWS의 서비스를 통한 접근이기에 IAM에서 역할의 생성으로 필요하다

1. IAM 역할만들기
2. 사용사례에서 CodeDeploy 설정 → `AWSCodeDeployRole` 설정 확인
3. 역할이름, 태그설정

<br>

**AWS의 배포 생태계**

> 근데 일단 해당 저서가 몇년전에 만들어 진 것 이다보니 현재 TIL을 작성하는 2022년을 기준으로 맞는지는 모르겠네...

> 유명한 3종류가 존재하며 다음과 같다.

1. `Code Commit`
   - 깃허브 처럼 코드저장소의 역할을 한다.
   - 프라이빗 기능을 지원한다는 강점이 있지만 현재 Github에서 Private지원을 하고 있어 거의 사용하지 않는다.
2. `Code Build`
   - Travis CI와 마찬가지로 빌드용 서비스이다.
   - 멀티모듈을 배포해야 하는 경우에 사용 해 볼법 하지만 규모가 있는 서비스에서는 대부분 `젠킨스` / `심시티` 등을 이용하는등 외부 서비스를 사용하기 때문에 사용할 경우가 거의 없다.
3. `CodeDeploy`
   - AWS의 배포 서비스이다
   - `Code Commit`과 `Code Build`의 경우에는 다른 서비스들로 대체제가 존재하지만 `CodeDeploy`의 경우에는 대체제가 없다.
   - `오토 스케일링 그룹 배포`, `블루 그린 배포`, `롤링 배포`, `EC2 단독 배포`등 많은 기능을 지원한다.

<br>

**CodeDeploy 생성방법**

1. CodeDeploy 이동 및 Application생성
2. 어플리케이션 설정 및 EC2의 경우 컴퓨터 플랫폼에서 `EC2/온프레미스`를 사용한다.
3. 이후 배포그룹을 생성
   1. 배포그룹명기입 및 이전에 생성한 서비스 역할 선택
   2. 배포 유형의 경우 `내가 배포할 서비스가 2대 이상이라면 블루 / 그린`을 선택한다. 현재는 EC2한대만 이기 떄문에 `현재위치` 항목으로 진행한다.
   3. 환경구성은 EC2를 사용하기 떄문에 Amazon EC2 인스턴스를 사용한다.
   4. 배포 설정의 경우 CodeDeployDefault.AllAtOnce 항목 선택
      1. 항목이 `CodeDeployDefault.OneAtATime`, `CodeDeployDefault.HalfAtATime`, `CodeDeployDefault.AllAtOnce`이 존재한다.
      2. 각각 배포때 10대의 배포라면 한번에 한대씩 배포할지, 전체배포할지 등 조율하는 항목이다
      3. 커스텀으로 조절도 가능하다.
   5. 로드밸런싱은 현재 필요없기에 해제한다.

<br>

**Travis CI, S3, CodeDeploy 연동 순서**

> 해당 내역은 빌드한 파일을 연동까지만의 과정이며 `배포(실행)`하는 과정은 빠져있다..

1. S3에서 Zip파일을 넘겨받을 Directory 추가
   1. Travis CI의 Build가 끝나고 S3에 Zip파일이 전송된다.
   2. Zip파일을 생성한 Directory로 복사후 압출을 푼다.
   3. 설정은 .travis.yml에서 진행하며, AWS CodeDeploy의 경우에는 `appspec.yml`에서 진행한다
2. `appspec.yml`작성
3. `.travis.yml`에서 `CodeDeploy` 설정 추가
4. 설정 Push 이후 `CodeDeploy 웹콘솔` 에서 배포내역 확인

**`appspec.yml` 작성**

```
version: 0.0
os: linux
files:
- source: /
   destination: /home/ec2-user/app/step2/zip
   overwrite: yes
```

1. `version: 0.0`
   1. CodeDeploy버전을 의미한다.
   2. 프로젝트 버전이 아니므로 0.0 외에 다른 버전을 사용하는 경우 오류가 발생한다.
2. `source : /`
   1. CodeDeploy에서 전달을 해 준 파일 중 `destination`으로 이동시킬 대상을 지정한다.
   2. `루트경로(/)` 를 지정하면 전체 파일을 의미한다.
3. `destination: 경로`
   1. source에서 지정한 파일을 받을 위치를 의미한다.
   2. 이후 Jar을 실행하는 등은 destination에서 옮긴 파일들로 진행한다.
4. `overwrite`
   1. 기존에 파일들이 있으면 덮어쓸지를 결정한다.
   2. 현재 `yes`로 지정을 하였기 때문에 파일들을 덮어쓰게 된다.

<br>

**`.travis.yml`에서 `CodeDeploy` 설정 추가**

```
  - provider: codedeploy
    access_key_id: $AWS_ACCESS_KEY
    secret_access_key: $AWS_SECRET_KEY
    bucket: jong1-springboot-build
    key: jong1-springboot-webservice.zip
    bundle_type: zip
    application: jong1-springboot-webservice
    deployment_group: jong1-springboot-webservice-group
    region: ap-northeast-2
    wait-until-deployed: true
    on:
      all_branches: true
```

1. `bucket: jong1-springboot-build`
   - AWS의 S3 버킷에 설정한 명칭
2. `key: jong1-springboot-webservice.zip`
   - 빌드 파일을 압축해서 전달한다.
3. `bundle_type: zip`
   - 압축 확장자
4. `application: jong1-springboot-webservice`
   - CodeDeploy 웹 콘솔에서 등록한 Application 명
5. `deployment_group: jong1-springboot-webservice-group`
   - 웹 콘솔에서 등록한 CodeDeploy 배포 그룹
6. `on.all_branches: true`
   - `S3 Bucket` 설정때와 마찬가지로 작업을 진행햇을때 작업이 정상적으로 마치질 않았다.
   - 빌드는 성공했으나 파일이 없었고, AWS의 CodeDeploy 배포내역에도 존재하지 않는현상.
   - 브랜치설정을 추가한후 다시 빌드 했을 경우 정상적인 파일 배포가 가능해졌다.

**배포 자동화(실행)**

> 쉘 스크립트를 인텔리제이에서 제작할 때 `BashSupport`를 활용하면 보조를 받을 수 있다.

1. 쉘 배포 스크립트 프로젝트에서 생성
2. `.travis.yml` 수정
3. `appspec.yml` 수정

**쉘 배포 스크립트 변경사항**

```
# 기존
$REPOSITORY/$JAR_NAME 2>&1 &

# 변경
$JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

1. nohup 실행을 하게 될 경우 `CodeDeploy`는 `무한 대기`를 하게 된다.
2. 해당 이슈를 해결 하기 위해 nohup.out파일을 표주준 입출력용으로 별도 사용해야 한다.
   1. 이렇게 사용하지 않을 경우에는 nohup.out파일이 생기지 않으며 `CodeDeploy`로그에 표준 입력이 출력된다.
   2. nohup이 끝나기 전 까지는 `CodeDeploy`도 끝나지 않으니 꼭 이렇게 해야 한다.

**`.travis.yml`의 `before-deploy` 수정**

```
before_deploy:
  - mkdir -p before-deploy
  - cp scripts/*.sh before-deploy/
  - cp appspec.yml before-deploy/
  - cp build/libs/*.jar before-deploy/
  - cd before-deploy && zip -r before-deploy *
  - cd ../ && mkdir -p deploy
  - mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip
```

> 실제 필요한 파일은 `스크립트 실행(sh)파일`, `appspec.yml`, `jar` 총 세가지만 필요하나 현재 모든 파일을 zip으로 올리던 상황. 필요한 파일만 담도록 수정한것.

1. `mkdir -p before-deploy`
   1. 기존 `deploy`파일을 사용하는게 아니라 사전에 파일을 정리할 Directory 새로 생성
2. `cp scripts/*.sh before-deploy/`
   1. `쉘 스크립트 실행파일(sh)`을 `before-deploy`로 복사
3. `cp appspec.yml before-deploy/`
   1. `appspec.yml`을 `before-deploy`로 복사
4. `cp build/libs/*.jar before-deploy/`
   1. `jar`파일들을 `before-deploy`로 복사
5. `cd before-deploy && zip -r before-deploy *`
   1. `before-deploy`로 이동 및 `before-deploy`라는 명칭으로 디렉토리내 모든 파일 zip
6. `cd ../ && mkdir -p deploy`
   1. 상위 디렉토리 이동 및 `deploy`디렉토리 생성
7. `mv before-deploy/before-deploy.zip deploy/jong1-springboot-webservice.zip`
   1. `before-deploy`에 생성한 zip파일을 `deploy`디렉토리에 서비스 명칭으로 zip파일 명칭 변경해서 생성

<br>

**`appspec.yml` 수정**

```
permissions:
  - object: /
    pattern: "**"
    owner: ec2-user
    group: ec2-user

hooks:
  ApplicationStart:
    - location: deploy.sh
      timeout: 60
      runas: ec2-user
```

1. `permissions:`
   1. `CodeDeploy`에서 `EC2`서버로 넘겨준 파일들을 모두 `ec2-user`권한을 갖도록 설정.
2. `hooks`
   1. `CodeDeploy`배포 단계에서 실행할 명령어를 지정한다.
   2. `ApplicationStart`라는 단계에서 deploy.sh를 ec2-user 권한으로 실행한다.
   3. `timeout: 60`설정으로 인해서 스크립트 실행이 60초 이상 수행되면 실패가 된다.
      1. 무한정 기다릴순 없으므로 시간제한은 필수다.

<br>

**`CodeDeploy`를 통한 배포 로그 확인 방법**

1. `/opt/codedeploy-agent/deployment-root`에서 `9025f408-3a26-4083-862d-a0f124a5b95d` 이런 형식의 Directory의 역할
   1. 사용자의 `CodeDeploy ID` 이며, 고유한 ID로 각자 다른 ID가 발급된다.
   2. 해당 디렉토리에는 `배포를 진행한 단위별로 배포 파일들이 존재`하며, 본인의 `배포 파일을 정상적으로 수신하였는지 확인`이 가능하다
2. `/opt/codedeploy-agent/deployment-root/deployment-logs/codedeploy-agent-deployments.log`
   1. `CodeDeploy`의 로그 파일이 들어가게 된다.
   2. `CodeDeploy`로 이루어지는 배포 내용 중 입 / 출력 내용이 담기게 되며 작성한 `echo`내용도 모두 표기가 된다.

<br>
