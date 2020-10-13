### SPRING SECURITY
-

- Dependencies  dan tipe springboot yang digunakan : 
```properties
    spring 2.2.10.RELEASE
    java 8 
    spring-boot-starter-data-jpa
    spring-boot-starter-security
    spring-boot-starter-thymeleaf
    spring-boot-devtools
    lombok
    postgresql
    spring-boot-starter-actuator
```

- Tahapan Pembuatan 
    - 
    
1. Buat database 
2. setup application.properties
```properties

#setup connection
spring.datasource.driver-class-name=org.postgresql.Driver
spring.datasource.username = kpaydeveloper
spring.datasource.password = ''
spring.datasource.url = jdbc:postgresql://localhost:5432/security_app


# setup jpa

spring.jpa.database=postgresql
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true


# setup thymeleaf
spring.thymeleaf.cache=false
spring.thymeleaf.enabled=true
spring.thymeleaf.mode=HTML5
spring.thymeleaf.encoding=UTF-8


```
    