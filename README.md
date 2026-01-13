# User Service

## Высасыватель
### Аутентификация
API:
```
POST http://localhost:8080/api/v1/auth/login
```

REQUEST BODY:
```
{
    "email": "ivanov@gmail.com",
    "password": "ivanov"
}
```


RESPONSE BODY:
```
{
    {
    "token": "*jwt_token*",
    "id": "4875b64f-37a3-4a5d-8621-633d873e102f", // user id
    "roles": [
        "ROLE_EXTRACTOR"
    ],
    "tokenType": "Bearer"
}
```

### Получить список ВСЕХ заявок
API:
```
GET http://localhost:8080/api/v1/extractor/applications
```

REQUEST BODY: нет

RESPONSE BODY:
```

```

### Получить список заявок, закрепленных к высасывателю
API:
```
GET http://localhost:8080/api/v1/extractor/my-applications
```

Headers:
```
Authorization: Bearer *jwt-token*
```

REQUEST BODY: нет

RESPONSE BODY:
```

```

### Получить заявку по id
API:
```
GET http://localhost:8080/api/v1/extractor/applications/{id}
```

REQUEST BODY: нет

RESPONSE BODY:
```

```

### Создать заявку на охоту
API: 
```
POST http://localhost:8080/api/v1/extractor/hunter-application
```

Headers:
```
Authorization: Bearer *jwt-token*
```

REQUEST BODY:
```
{
    "magicId": "id магии",
    "volume": 11,
    "deadline": "дата дедлайна"
}
```

RESPONSE BODY:
```

```
### Взять на себя заявку
API:
```
POST http://localhost:8080/api/v1/extractor/applications/{id}/take
```

Headers:
```
Authorization: Bearer *jwt-token*
```

REQUEST BODY: нет

RESPONSE BODY:
```

```
### Получить все свои выполненные заявки
API:
```
POST http://localhost:8080/api/v1/extractor/my-responses
```

Headers:
```
Authorization: Bearer *jwt-token*
```

REQUEST BODY: нет

RESPONSE BODY:
```

```