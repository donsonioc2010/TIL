## **Chapter. 08 : EC2서버에 프로젝트 배포하기**

**EC2에 프로젝트 Clone하기**

1. 깃 설치
   1. `sudo yum install git`
2. git 설치 상태 확인
   1. 책에서는 `git --version`을 통해 확인하였다.
3. clone해 넣어둘 디렉토리 생성
   1. `mkdir ~/app && ~/app/step1`
   2. `cd ~/app/step1`
4. 프로젝트 clone
   1. git clone git페이지주소
   2. clone한 프로젝트 실행해보기
      1. 제작한 TestCase를 실행해해봄
         1. `cd 프로젝트명`
         2. `./gradlew test`
            1. 실패의 경우 수정이후 `git pull`
            2. gradlew의 Permission denied인 경우 `chmod +x ./gradlew`

---

**배포란?**

> 작성한 코드를 실제 서버에 반영하는 것을 의미한다.

---

**책의 예제의 배포 과정은?**

1. Git Clone 과 Git Pull을 통해 새 버젼의 프로젝트를 받는다.
2. Gradle 또는 Maven을 통해 해당 프로젝트를 테스트 및 빌드한다.
3. EC2서버에서 해당 프로젝트 실행 및 재실행한다.

---

**배포 스크립트 만들기**

> 위 예제의 배포과정을 배포할 때마다 개발자가 하나 하나 명령어를 실행하는 것은 불편함이 많다, 그렇기에 이를 쉘 스크립트로 작성해 스크립트를 실행시켜 앞의 과정이 한번에 진행되도록 설정하는 것

```
#!/bin/bash

REPOSITORY=/home/ec2-user/app/step1
PROJECT_NAME=SpringBoot-With-Aws

cd $REPOSITORY/$PROJECT_NAME/
git pull
./gradlew build
cd $REPOSITORY
cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/
CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)
if [ ~z "$CURRENT_PID" ]; then
        echo "> 현재 구동중인 어플리케이션이 없으므로 종료하지 않습니다."
else
        echo "> kill -15 $CURRENT_PID"
        kill -15 $CURRENT_PID
        sleep 5
fi

JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)
nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `#!/bin/bash`
   - `bash`로 쉘을 실행시키는 의미다.
2. `REPOSITORY=/home/ec2-user/app/step1`와 `PROJECT_NAME=SpringBoot-With-Aws`
   - `$REPOSITORY`, `$PROJECT_NAME` 으로 호출을 하기 위한 변수 생성 및 경로값을 대입
3. `cd $REPOSITORY/$PROJECT_NAME/`후 `git pull`
   - 프로젝트 디렉토리에 이동한 이후 버전 최신화
4. `./gradlew build`
   - 프로젝트 빌드
5. `cp $REPOSITORY/$PROJECT_NAME/build/libs/*.jar $REPOSITORY/`
   - build를 진행한 이후 나온 프로젝트 jar파일을 $REPOSITORY에 복사
6. `CURRENT_PID=$(pgrep -f ${PROJECT_NAME}.*.jar)`
   - 기존에 수행중이던 스프링 부트를 종료한다
   - `pgrep`은 process id 만 추출하는 명령어이다.
   - `-f` 옵션은 프로세스 이름으로 찾는 기능이다.
7. `if ~ else ~ fi`
   - 현재 구동중인 프로세스가 존재하는지 여부 판단을 위한 조건문
   - `process id` 값을 보고 프로세스가 존재하면 해당 프로세스를 종료한다
8. `JAR_NAME=$(ls -tr $REPOSITORY/ | grep .jar | tail -n 1)`
   - 새로 실행할 jar파일명을 찾아 JAR_NAMEdp sjgdma.
   - 여러 jar파일이 생기기 때문에 tail -n을 하여 가장 최신의 jar파일을 변수에 저장한다.
9. `nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &`
   - 발견한 jar파일명으로 해당 jar을 nohup으로 실행한다.
   - 그냥 java -jar을 통해 실행하는 경우 사용자가 터미널 접속 종료시에 Application도 같이 종료가 되기 떄문에 nohup을 활용
10. 이후 생성한 스크립트 파일 권한 변경
    - `chmod +x ./스크립트 파일`

---

**배포 스크립트 수정 - OAuth 적용**

> 위의 스크립트를 사용시에 `Spring Security`의 값을 가져올 수 있는 `application-oauth.properties`가 존재하지 않기 때문에 실패를 한다.  
> 하지만 `application-oauth.properties`의 경우 Git Ignore가 되어 있기 때문에 직접적으로 운영, 개발서버에 추가하고 설정해줘야한다.

1. `희망위치/application-oauth.properties`생성
2. 개발된 내용의 oauth복사
3. 배포스크립트 내용 수정

   ```
   //수정전
   nohup java -jar $REPOSITORY/$JAR_NAME 2>&1 &

   //수정후
   nohup java -jar \
   -Dspring.config.location=classpath:/application.properties, 생성한파일위치/application-oauth.properties \
   $REPOSITORY/$JAR_NAME 2>&1 &
   ```

**`-Dspring.config.location`**

- 스프링 설정 파일 위치를 지정한다.
- 지정된 properties마다 따로 지정이 가능하다
- classpath가 붙으면 jar안에 있는 resources디렉토리를 기준으로 경로가 생성된다.
- application-oauth.properties의 경우에는 외부에 파일이 있기 때문에 절대 경로를 사용한다.

---

**RDS서버에 테이블 생성**

> 현재 책의 예제의 테이블은 2종류가 있로 `JPA를 활용하는 엔티티 테이블` 그리고 DB를 세션으로 사용하고 있는 `세션테이블`이 존재한다. 이 2종류의 테이블을 만들 수 있는 쿼리는 다음과 같다.
>
> 1. `엔티티 테이블`은 `Application 실행시키며 생성되는 쿼리`를 사용한다.
> 2. `세션 테이블`은 프로젝트에 있는 `schema-mysql.sql`파일에서 추출한다

---

**EC2서버에서 RDS로 접속을 위한 `application-real-db.properties`설정**

```
spring.jpa.hibernate.ddl-auto=none
spring.datasource.url=jdbc:mariadb://AWS Endpoint:Port/Database Name
spring.datasource.username=DB ID
spring.datasource.password=DB PW
spring.datasource.driver-class-name=org.mariadb.jdbc.Driver
```

1. `spring.jpa.hibernate.ddl-auto=none`
   - JPA로 테이블이 자동 생성되는 옵션을 None(생성하지 않도록) 한다.
   - RDS에는 실제 운영에서 사용될 테이블이기 떄문에 절대로 스프링 부트에서 새로 만들도록 하지 않아야 한다.
   - 이 옵션을 설정하지 않으면 테이블이 모두 새로 생성될 수 있기에 주의해야 한다.

---

**DB설정 추가에 따른 배포스크립트의 수정영역**

```
nohup java -jar \
        -Dspring.config.location=classpath:/application.properties, /home/ec2-user/app/application-oauth.properties, /home/ec2-user/app/application-real-db.properties, classpath:/application-real.properties \
        -Dspring.profiles.active=real \
        $REPOSITORY/$JAR_NAME 2>&1 &
```

1. `application-real`와 `application-real-db`설정 파일의 추가
2. `-Dspring.profiles.active=real`
   - `application-real.properties`항목을 활성화 시킨다.
   - `application-real.properties`의 spring.profiles.include=oauth,real-db옵션 때문에 real-db역시 같이 활성화 대상에 포함된다.
