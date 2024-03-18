# Kafka

## 구성
`토픽`, `프로듀서`, `컨슈머`로 구성되어 있다.

## 역할
- `토픽`: 메시지를 저장하는 공간, 큐라고 생각하면 편함
- `프로듀서`: 메시지를 토픽에 전달하는 역할, 데이터 삽입
- `컨슈머`: 토픽에 저장된 메시지를 가져오는 역할, 데이터 조회, 추출

## 테스트 토픽생성
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic testTopic

## 테스트 프로듀서 실행
docker exec -it kafka kafka-console-producer.sh --topic testTopic --broker-list 0.0.0.0:9092

docker exec -it kafka kafka-console-consumer.sh --topic testTopic --bootstrap-server 0.0.0.0:9092

## 로직 토픽, 프로듀서 컨슈머
```bash
docker exec -it kafka kafka-topics.sh --bootstrap-server localhost:9092 --create --topic coupon_create
docker exec -it kafka kafka-console-producer.sh --topic coupon_create --broker-list 0.0.0.0:9092

docker exec -it kafka kafka-console-consumer.sh --topic coupon_create --bootstrap-server 0.0.0.0:9092 --key-deserializer "org.apache.kafka.common.serialization.StringDeserializer" --value-deserializer "org.apache.kafka.common.serialization.LongDeserializer"

```