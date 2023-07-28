# Docker Hub MySQL 주소

## Link

- https://hub.docker.com/_/mysql
- https://hub.docker.com/_/mysql/tags

## Run

`docker-compose up -d`

### 터미널 접속

`docker exec -it <ContainerName> "/bin/bash"`

## Source

> 변수들은 모두 `.env`에 설정됨

### docker-compose.yml

```yml
version: "3.8"

services:
  mysql: # container name
    image: mysql:8.0.31
    ports: # 바인딩할 포트:내부 포트
      - ${MYSQL_BINDING_PORT}:${MYSQL_PORT}
    volumes: # 마운트할 볼륨 설정
      - ${MYSQL_DATA_PATH}:/var/lib/mysql
      - ${MYSQL_CUSTOM_CONFIG_PATH}:/etc/mysql/conf.d
      - ${MYSQL_DEFAULT_CONFIG_FILE}:/etc/my.cnf
    environment: # MySQL의 환경변수
      - MYSQL_ROOT_PASSWORD=${MYSQL_ROOT_PASSWORD}
      - TZ=${TZ}
    restart: always
```

### .env

> 맛깔나게 변경 가능

```properties
# Docker setting
COMPOSE_PROJECT_NAME=mysql-development

# MySQL setting
MYSQL_ROOT_PASSWORD=1234
TZ=Asia/Seoul

# Docker volume setting
MYSQL_DATA_PATH=./mysql/data
MYSQL_DEFAULT_CONFIG_FILE=./mysql/my.cnf
MYSQL_CUSTOM_CONFIG_PATH=./mysql/conf.d

# etc setting
BINDING_PORT=33306
MYSQL_PORT=3306
```

## Reference

- https://velog.io/@gingaminga/Docker-compose%EB%A1%9C-MySQL-%EC%8B%A4%ED%96%89%ED%95%98%EA%B8%B0
