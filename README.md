# Spring Security Demo - sillyfan-passpoprt

## 运行

> mvn spring-boot run

## 访问token测试页面:

[http://localhost:8888](http://localhost:8888)

## 使用 mongodb 数据库

```sql
db.createCollection('user');

-- 初始数据 admin - admin
db.t_user.insert({
                     "_id" : NumberLong(1526914049264),
                     "username" : "admin",
                     "password" : "$2a$10$NmROVPir1O2DrBb6e70n8.B2qnE6dgOXNta6E9oih4FS/2u9JNNjG",
                     "email" : "huan.dreamer@gmail.com",
                     "authorizes" : [
                         "ROLE_ADMIN"
                     ],
                     "status": 1,
                     "type": 1,
                     "lastPasswordResetDate" : ISODate("2018-05-21T14:47:29.492Z"),
                     "_class" : "top.sillyfan.model.security.User"
                 })
```

## 作为独立的认证项目

### 客户端配置:

pom.xml
```xml
<dependency>
    <groupId>org.springframework.security.oauth</groupId>
    <artifactId>spring-security-oauth2</artifactId>
</dependency>
```

application.properties
```properties
security.oauth2.resource.user-info-uri=http://localhost:8888/user
```


## CURL
```
get token: 
curl -H "Content-Type: application/json" -d "{\"username\": \"admin\", \"password\": \"admin\"}" localhost:8888/auth
```

```
get user info
curl -H "Authorization:Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImV4cCI6MTUyNzUxMzIyMiwiaWF0IjoxNTI2OTA4NDIyfQ.8UFBW4rOmRldXJSBvic9KRgxKtj-SKAujWWZ3r31Wuj0PZx3LlQOmShTMnO8L42qbOP2Dh-2y34su8IpXiZmBg" localhost:8888/user
```
