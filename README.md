# Spring Security Demo - sillyfan-passpoprt

## 运行

> mvn spring-boot run

## 访问token测试页面:

[http://localhost:8888](http://localhost:8888)

## 使用 h2 数据库

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
