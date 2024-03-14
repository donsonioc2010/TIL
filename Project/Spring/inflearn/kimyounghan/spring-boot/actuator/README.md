- `http://localhost:8080/actuator`

```json
{
  "_links": {
    "self": {
      "href": "http://localhost:8080/actuator",
      "templated": false
    },
    "health-path": {
      "href": "http://localhost:8080/actuator/health/{*path}",
      "templated": true
    },
    "health": {
      "href": "http://localhost:8080/actuator/health",
      "templated": false
    }
  }
}
```

- `http://localhost:8080/actuator/health`
  - 현재 서버가 잘 작동하고 있는지 어플리케이션의 상태확인
```json
{
"status": "UP"
}
```