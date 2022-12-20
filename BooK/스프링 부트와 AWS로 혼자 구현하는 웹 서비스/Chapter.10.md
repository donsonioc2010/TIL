## **Chap.10 : 24시간 365일 중단 없는 서비스를 만들자**

> `Chap.09`의 내용의 경우 `배포의 자동화` 즉 `CI / CD` 에 대한 학습이었다.  
> 하지만 해당 챕터의 문제점은 배포하는 동안 어플리케이션이 종료가 된다는 문제가 남아 있다.  
> 긴 시간은 아니지만, `새로운 Jar가 실행되기 전`까지 `기존 Jar를 종료`시켜 놓기 떄문에 `짧은 시간 서비스가 중단`된다.
> 하지만 대규모 서비스의 경우 24시간 서비스가 되어야 하기에 `정지`하지 않는다. 해당 챕터에서는 무중단을 하며 서비스를 유지하는 방법에 대한 기록이다.

<br>

**`무중단 배포`에 대하여**

> 예전에는 배포가 팀에서 큰 이벤트 였기에, 코드 병합일을 정하고, 사용자가 적은시간에 배포를 준비했다.  
> 이후 문제가 발생하면 긴급 점검을 하고 , 서비스를 정지해야 하는경우가 많았다.  
> 이후 정지하지 않고 배포할 수 있는 방안을 모색해서 나온것이 `무중단 배포` 이다.
>
> 무중단 배포는 여러가지 방법이 있으나 몇가지가 있다.. (당시 책의 저서 기준)
>
> 1. AWS에서 블루-그린 (Blue-Green) 무중단 배포
> 2. 도커를 활용한 웹서비스 무중단 배포.
> 3. L4스위치를 이용한 무중단 배포
> 4. Nginx를 활용한 무중단 배포

> 책의 예제는 `Nginx`를 활용한 무중단 배포를 진행하며, 사용하는 핵심적인 이유는 `가장 저렴하고 쉽기 떄문`이다.  
> Nginx에서 활용할 기능은 `리버스 프록시`가 될 예정이며 기능의 역할은 `외부의 요청을 받아 백엔드 서버로 요청을 전달`하는 역할이며, 기존에 사용하던 EC2에서 그대로 적용할 수 있고, 추가적인 인스턴스도 불필요하며 클라우드 인프라가 구축되어 있지 않아도 활용할 수 있는 범용적인 방법 이기 때문에 개인서버 또는 사내 서버에서도 동일한 방식으로 구축 할 수 있다.

<br>

**`Nginx`?**

> `Nginx`란 웹 서버, 리버스 프록시, 캐싱, 로드 밸런싱, 미디어 스트리밍 등을 위한 오픈소스 소프트웨어이다.  
> 이전에 `Apache`가 대세였던 자리를 빼았은 웹서버 오픈소스이며, 고성능 웹서버 이기 떄문에 현재는 대부분 `Nginx`를 활용한다.

<br>
 
**`Nginx`를 활용한 무중단 배포 방법**
> 구조는 간단한데 하나의 EC2또는 리눅스 서버에 Nginx 1대 , Application을 2대 사용하는 것이다.

> 아래의 어플리케이션이 현재는 스프링부트를 백엔드로 삼기때문에 스프링부트로 생각하면 편하지만 다른 언어를 통한 백엔드 서버를 구성할떄도 비슷한 방식이 된다.

- Nginx에는 80번, 443번 포트(Http, Https)를 할당한다.
- 어플리케이션 1번은 Exam으로 8081번 포트로 실행한다
- 어플리케이션 2번은 Exam으로 8082번 포트를 실행한다.

1. 일반적인 Production 과정 (1.0 버전일때)
   1. 사용자는 서비스 주소를 통해 접속한다( 일반적인 도메인 그리고 80번 또는 443번 포트를 통한접속)
   2. Nginx는 사용자의 요청을 받아 현재 연결된 어플리케이션 1번 서버로 요청을 전달한다.(현재는 8081번으로 Exam을 생각)
   3. 어플리케이션 2번 서버(8082번 포트)는 연결된 상태가 아니기 떄문에 요청을 받지 못한다.
2. 1.1 버전으로 업그레이드가 필요할 때
   1. 1.1 버전의 배포가 필요하면 어플리케이션 2번 서버에 배포를 한다.
      1. 배포중에도 어플리케이션 1번을 바라보기 떄문에 서비스는 중단되지 않는다.
   2. 배포가 종료되면 스프링 부트 2가 구동중인지 확인한다.
   3. 스프링 부트 2가 정상 구동중이면 `nginx reload`명령어를 통해 어플리케이션 2번 서버를 바라바보게 한다.
      1. `nginx reload`의 경우에는 0.1초 이내에 완료가 된다
3. 1.2 버전으로 다시 업그레이드가 필요할때
   1. 어플리케이션 1번 서버로 배포를 진행한다.
   2. 배포가 안료되면 어플리케이션 1번서버가 구동중인지 확인 후 `nginx reload`를 실행한다.
   3. 이후 요청을 다시 어플리케이션 1번서버가 진행한다.

<br>

### `Nginx`의 설치와 `SpringBoot`연동하기

#### **`Nginx`설치**

**`Nginx` 설치**

```
# 1
sudo yum install nginx

# sudo amazon-linux-extras install nginx1
# EC2에서 nginx를 설치하려 했을때 nginx패키지를 찾지를 못하였으나 해당 명령어로 안내를 하고 있었다.

# 2
sudo service nginx start
# Redirecting to /bin/systemctl start nginx.service 다음과 같이 나오면 성공.
# EC2도메인을 통해 접속해보면 `Welcom to nginx`로 문구가 나온다.
```

**`Nginx`와 `SpringBoot`연동**

```
# 1
sudo vim /etc/nginx/nginx.conf

# 2 location추가
server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        include /etc/nginx/default.d/*.conf;

        location / {
                proxy_pass http://localhost:8080;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
    }

# 3 Nginx 재시작
sudo service nginx restart

```

1.  nginx의 설정 파일을 연다
2.  server영역에서 기존에 location이 없으나 추가를 한다.

---

1.  `proxy_pass http://localhost:8080;`
    1.  Nginx로 요쳥이 들어오는 경우 8080번 포트로 전달한다.
2.  `proxy_set_header XXX`
    1.  실제 요청 데이터를 header의 각 항목에 할당한다.
    2.  예시로 `proxy_set_header X-Real-IP $remote_addr;`의 경우 X-Real-IP에 요청자의 IP를 저장해서 보낸다

**`SpringBoot`에서의 작업**

1. `Profile`을 확인할 수 있는 `Controller`의 제작
2. `Properties`의 분리

<br>

**`Profile`을 확인 할 수 있는 `Controller`의 제작**

```
@RequiredArgsConstructor
@RestController
public class ProfileController {

  private final Environment env;

  @GetMapping("/profile")
  public String profile() {
    List<String> profiles = Arrays.asList(env.getActiveProfiles());
    List<String> realProfiles = Arrays.asList("real", "real1", "real2");

    String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

    return profiles.stream().filter(realProfiles::contains).findAny().orElse(defaultProfile);
  }
}
```

> 위와 같은 Controller를 제작하였으며, 해당 제작을 통해 현재 어떤 Profile을 소지중인지 확인이 가능하다.

<br>

**`Properties`의 분리**

```
# application-real1, 2 properties각각 추가 및 포트번호를 다르게 설정한다.
server.port=8081
server.port=8082
```

<br>

**`Nginx`의 설정 수정**

> 프록시 설정을 위해서는 `Nginx`의 설정이 추가적으로 필요하며 다음의 과정으로 이뤄진다. 추가적으로 `Nginx`의 설정파일은 `/etc/nginx/conf.d/` 에 모여있다.

```
# service-url.inc

# 1
cd /etc/nginx/conf.d
sudo vim ./service-url.inc

# 2
set $service_url http://127.0.0.1:8080;

이후 저장
```

```
# nginx.conf

# 1
sudo vim /etc/nginx/nginx.conf.d

# 2 기존코드 아래처럼 수정
server {
        listen       80;
        listen       [::]:80;
        server_name  _;
        root         /usr/share/nginx/html;

        # Load configuration files for the default server block.
        #include /etc/nginx/default.d/*.conf;
        include /etc/nginx/conf.d/service-url.inc;

        location / {
                # proxy_pass http://localhost:8080;
                proxy_pass $service_url;
                proxy_set_header X-Real-IP $remote_addr;
                proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
                proxy_set_header Host $http_host;
        }

        error_page 404 /404.html;
        location = /404.html {
        }

        error_page 500 502 503 504 /50x.html;
        location = /50x.html {
        }
    }

# 3 재시작
sudo service nginx restart
```

**`SpringBoot`에서 필요한 쉘 스크립트(sh) 파일**

1. `stop.sh` : 기존 Nginx에 연결되어 있지 않지만, 실행중이던 SpringBoot 종료
2. `start.sh` : 배포할 신규 버전 `Spring Boot`프로즉테를 `stop.sh`로 종료한 `profile`로 실행
3. `health.sh` : `start.sh`로 실행시킨 프로젝트가 정상적으로 실행 되었는지 확인
4. `switch.sh` : `Nginx`가 바라보는 `SpringBoot`를 최신 버전으로 변경
5. `profile.sh` : `1~4번`의 스크립트 파일에서 공용으로 사용할 `profile`과 `포트`체크 로직

**`profile.sh`**

```
#!/usr/bin/env bash

# 쉬고 있는 profile 찾는 기능, : real1이 사용중이라면 real2가 쉬고있으니 real2를 설정

function find_idle_profile()
{
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)

  if [ ${RESPONSE_CODE} -ge 400 ] #400보다 큰 값(즉 4, 5오류 의미)

  then
      CURRENT_PROFILE=real2
  else
      CURRENT_PROFILE=$(curl -s http://localhost/profile)
  fi

  if [ ${CURRENT_PROFILE} == real1 ]
  then
    IDLE_PROFILE=real2
  else
    IDLE_PROFILE=real1
  fi

  echo "${IDLE_PROFILE}"
}

# 쉬고 있는 profile의 port 찾기
function find_idle_port()
{
  IDLE_PROFILE=$(find_idle_profile)

  if [ ${IDLE_PROFILE} == real1 ]
  then
    echo "8081"
  else
    echo "8082"
  fi
}
```

1. `$(curl -s -o /dev/null -w "%{http_code}" http://localhost/profile)`
   1. 현재 `Nginx`가 바라보고 있는 `SpringBoot`가 정상적으로 수행중인 지를 확인한다.
   2. 응답값을 `HttpStatus`로 받는다.
   3. 정상인 경우 200, 오류인 경우 예외코드로 발생하니, 400 이상은 모두 예외로 보고 real2를 현재 profile로 설정한다.
2. `IDLE_PROFILE`
   1. `Nginx와 연결되지 않은 profile 이다.`
   2. `Spring Boot`프로젝트를 이 profile로 연결하기 위한 반환이다.
3. `echo "${IDLE_PROFILE}"`
   1. `bash` 스크립트는 값을 반환하는 기능이 없다. 그래서 제일 마지막 줄에 echo로 결과를 출력하여 클라이언트가 그 값을 잡아서 사용 할 수 있게 해야한다.
   2. 위의 이유로 인하여 중간에 `echo`를 사용하면 안된다.

**`stop.sh`**

1. `ABSDIR=$(dirname $ABSPATH)`
   1. 현재 `stop.sh` 가 속해 있는 경로를 찾는다.
   2. `2번`항목의 `profile.sh`의 경로를 찾기 위해 사용된다.
2. `source ${ABSDIR}/profile.sh`
   1. 자바의 `import`같은 역할 구문이다.
   2. `stop.sh`에서 `profile.sh`의 `function`을 사용 할 수 있게 된다.

**`start.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source $ABSDIR/profile.sh

REPOSITORY=/home/ec2-user/app/step3
PROJECT_NAME=jong1-springboot-webservice

echo "> Build 파일 복사"
echo "> cp $REPOSITORY/zip/*.jar $REPOSITORY/"

cp $REPOSITORY/zip/*.jar $REPOSITORY/

echo "> 새 Application 배포"

JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR Name : $JAR_NAME"

echo "> $JAR_JAR_NAME 에 실행권한 추가"

chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"

IDLE_PROFILE=$(find_idle_profile)

echo "> $JAR_NAME 을 profile=$IDLE_PROFILE 로 실행합니다."
nohup java -jar \
  -Dspring.config.location=classpath:/application.properties,classpath:/application-$IDLE_PROFILE.properties,/home/ec2-user/app/application-oauth.properties,/home/ec2-user/app/application-real-db.properties \
  -Dspring.profiles.active=$IDLE_PROFILE \
  $JAR_NAME > $REPOSITORY/nohup.out 2>&1 &
```

1. 기본적인 스크립트는 기존의 `deploy.sh`와 유사하다.
2. 차이점은 `IDLE_PROFILE`을 통해 `properties`을 가져오고 `application-real.properties`를 사용하던 것을 `application-$IDLE_PROFILE.properties`로 수정한것과 -Dspring.profiles.active로 설정을 추가해 준것.

**`health.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh
source ${ABSDIR}/switch.sh

IDLE_PORT=$(find_idle_port)

echo "> Health Check Start!"
echo "> IDLE_PORT: $IDLE_PORT"
echo "> curl -s http://localhost:$IDLE_PORT/profile "
sleep 10

for RETRY_COUNT in {1..10}
do
  RESPONSE=$(curl -s http://localhost:${IDLE_PORT}/profile)
  UP_COUNT=$(echo ${RESPONSE} | grep 'real' | wc -l)

  if [ ${UP_COUNT} -ge 1 ]
  then # $up_count >= 1 ("real" 문자열이 있는지 검증)
      echo "> Health check 성공"
      switch_proxy
      break
  else
      echo "> Health check의 응답을 알 수 없거나 혹은 실행 상태가 아닙니다."
      echo "> Health check: ${RESPONSE}"
  fi

  if [ ${RETRY_COUNT} -eq 10 ]
  then
    echo "> Health check 실패. "
    echo "> 엔진엑스에 연결하지 않고 배포를 종료합니다."
    exit 1
  fi

  echo "> Health check 연결 실패. 재시도..."
  sleep 10
done
```

1. `Nginx`와 연결되지 않은 포트로 스프링 부트가 잘 수행되었는지를 체크한다.
2. 정상적으로 실행이 완료된 경우 `switch_proxy`를 통해 프록시 설정을 변경한다.
3. `Nginx`프록시 설정 변경은 `switch.sh`에서 진행한다.

**`switch.sh`**

```
#!/usr/bin/env bash

ABSPATH=$(readlink -f $0)
ABSDIR=$(dirname $ABSPATH)
source ${ABSDIR}/profile.sh

function switch_proxy() {
  IDLE_PORT=$(find_idle_port)

  echo "> 전환할 PORt: $IDLE_PORT"
  echo "> Port 전환"
  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc

  echo "> Nginx Reload"
  sudo service nginx reload
}
```

1. `  echo "set \$service_url http://127.0.0.1:${IDLE_PORT};"`
   1. 하나의 문장을 만들어 `파이프 라인(|)`으로 넘겨주기 위해 echo를 활용한다.
   2. `Nginx`가 변경할 프록시 주소를 생성한다.
   3. `쌍다옴표("")`를 사용해야 하며, 사용하지 않을 경우 `$service_url`을 그대로 인식하지 못하게 변수를 찾게 된다.
2. `| sudo tee /etc/nginx/conf.d/service-url.inc`
   1. 앞에서 넘겨준 문장을 service-uri.inc에 덮어쓴다.
3. `sudo service nginx reload`
   1. `Nginx`설정을 다시 불러온다.
   2. `restart`와는 다르며, `restart`의 경우에는 잠시 끊기는 현상이 존재하지만 `reload`의 경우에는 끊김없아 디사 불러온다.
   3. `reload`의 경우에는 중요한 설정들은 반영되지 않으며, 반영이 필요할때는 `restart`가 필요하다
   4. `외부설정 파일`인 `service-url.inc`의 경우에는 다시 불러오는 거라 `reload`가 가능하다.

<br>

**버전의 자동 증가**

```
# build.gradle
version '10.1-SNAPSHOT-' + new Date().format("yyyyMMddHHmmss")
```

1. `new Date().format("yyyyMMddHHmmss")`의 추가
   1. new Date로 빌드시 그 시간이 버전에 추가되도록 빌드 된다
