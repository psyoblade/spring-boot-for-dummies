# 스프링 데이터 JDBC & JPA

## 스프링 데이터 JDBC

### Hikari Connection Pooling
> 레퍼런스에 포함된 FAQ 문서를 참고하여 기본 값과 최적치를 본인이 설정해야 합니다 

### H2 Console 연결
* 콘솔 접근이 가능하도록 설정 변경 후 http://localhost:8080/h2-console 접속
```properties
spring.h2.console.enabled=true
```

### RDB JDBC 설정
* pom.xml 파일에 jdbc 설정
```xml
<xml>
    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
    </dependency>
</xml>
```

### MySQL Docker 인스턴스 생성 및 삭제 
* 커널을 공유하기 때문에 빨리 설치 및 테스트를 할 수 있습니다
```bash
docker run -p 3306:3306 --name mysql_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=springboot -e MYSQL_USER=psyoblade -e MYSQL_PASSWORD=2 -d mysql
docker ps

CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                               NAMES
37662b2af923        mysql               "docker-entrypoint.s…"   12 seconds ago      Up 11 seconds       0.0.0.0:3306->3306/tcp, 33060/tcp   mysql_boot

docker exec -i -t mysql_boot bash
mysql -u psyoblade -p

docker stop mysql_boot
docker rm mysql_boot
```

### MariaDB Docker 인스턴스 
* MySQL 경우 라이센스를 사서 써야 하므로 Community 버전인 MariaDB 추천
```bash
docker run -p 3306:3306 --name mariadb_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=springboot -e MYSQL_USER=psyoblade -e MYSQL_PASSWORD=2 -d mariadb
```

### PostgreSQL 사용
* MariaDB 경우도 GPL2 이므로 소스공개 의무가 있고, 상용화에도 문제가 없는 PostgreSQL 추천
```bash
docker run -p 5432:5432 --name postgres_boot -e POSTGRES_ROOT_PASSWORD=1 -e POSTGRES_DB=springboot -e POSTGRES_USER=psyoblade -e POSTGRES_PASSWORD=2 -d postgres
docker ps
--
docker exec -it postgres_boot bash
psql springboot psyoblade
--
docker exec -it postgres_boot psql -U psyoblade # 직접 접속하는 방법도 있습니다
\list # 데이터베이스 조회
\dt # 테이블 조회
```
* PostgreSQL 경우 테이블은 소문자만 지원한다

### IntelliJ 데이터베이스 플러그인 설치
* 플러그인 설정에서 Database 로 검색하면 'DB Browser' 플러그인 설치하면 끗

### 레퍼런스
* [HikariCp](https://github.com/brettwooldridge/HikariCP)
* [HikariCp FAQ](https://github.com/brettwooldridge/HikariCP#frequently-used)
* [Spring Application Properties](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html)
* [IntelliJ Database Tools](https://blog.jetbrains.com/idea/2017/07/database-tools-whats-new-in-intellij-idea-2017-2)
* [PostgreSQL Syntax](https://www.postgresqltutorial.com/postgresql-select/)


## 스프링 데이터 JPA
> ORM, 스프링 JPA 란?
> ORM (Object Relation Mapping) 과 JPA (Java Persistence API) 의 개념적인 불일치를 해결하는 프레임워크 입니다
* 객체 지향에서는 다양한 primitive types, class instance 등 다양한 유형을 가지고 있지만 Relational DB 의 Table 은 정해진 것만 존재하기 때문에 이에 대해 1:1 Mapping 이 사실상 어려울 것이다 
* 또한 객체는 상속 등의 구조가 있지만 테이블에서는 그러한 것이 없다 

> 테이블에서는 Id 혹은 Primary Key 만 같으면 같다고 볼 수 있는가? 객체에서는 hashCode 가 같으면 같은가? ... 이런 것들이 ORM 이다
> JPA 는 다양한 ORM 솔루션이 있는데 그 중 하나가 JPA 이고 Hibernate 기반에서 만들어져 있습니다
> 모든 Hibernate 에 대한 것을 JPA 가 다 커버하지는 않기 때문에 알아두어야 한다

> 스프링 부트 JPA 는?
> JPA 를 더 쉽게 사용할 수 있도록 구현되어 자동화 되어 있는 프레임워크 이며 JDBC -> Hibernate -> JPA -> Spring Boot

### 슬라이싱 테스트?
* @SpringMvcTest 에서 MockMvc 테스트와 마찬가지로 원하는 모듈만 임포트 해서 테스트 하기 위해 @DataJpaTest 를 추가합니다
  * 이러한 슬라이싱 테스트 시에는 반드시 Embedded Database 가 존재해야만 합니다 (여기서는 H2 를 test scope 으로 줍니다)
  * JPA @Entity 테이블은 반드시 @Id 를 가져야만 하는데 javax.annotation.persistence.Id 를 import 해야만 합니다
```bash
Hibernate: drop table account if exists
Hibernate: drop sequence if exists hibernate_sequence
Hibernate: create sequence hibernate_sequence start with 1 increment by 1
```

### 다시 별도의 데몬으로 테스트 합니다
```bash
docker run -p 5432:5432 --name psql_boot -e POSTGRES_ROOT_PASSWORD=root -e POSTGRES_USER=psyoblade -e POSTGRES_PASSWORD=pass -e POSTGRES_DB=springboot -d postgres
docker exec -it psql_boot bash
--
psql psyoblade springboot
```

### 레퍼런스
* [Hibernate ORM](http://hibernate.org/orm/what-is-an-orm)
* [Project Lombok](https://www.baeldung.com/intro-to-project-lombok)