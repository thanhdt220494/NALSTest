# NALSTest
This application was generated using JHipster 6.5.1, you can find documentation and help at [https://www.jhipster.tech/documentation-archive/v6.5.1](https://www.jhipster.tech/documentation-archive/v6.5.1).

## Create schema
Access mysql database with connection information:
    Hostname: localhost
    Port: 3306
    Username: root
    Password: root
If you want to config connection information, please edit file: "/nals-test/src/main/resources/config/application-dev.yml" at line 38-42:
    datasource:
    type: com.zaxxer.hikari.HikariDataSource
    url: jdbc:mysql://localhost:3306/NALSTest?useUnicode=true&characterEncoding=utf8&useSSL=false&useLegacyDatetimeCode=false&serverTimezone=UTC
    username: root
    password: root
Create new schema with name "NALSTest":
    CREATE SCHEMA `nalstest` DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci ;

## Development
To start your application in the dev profile, simply run:
    [../NALSTest] mvnw

## Test API
Access url: http://localhost:8080/swagger-ui.html#/work-resource
    Add work: POST http://localhost:8080/api/works
    Edit work: PUT http://localhost:8080/api/works
    Delete work: DELETE http://localhost:8080/api/works/{id}
    API to fetch list: GET http://localhost:8080/api/works
        Parameter: page,size,sort