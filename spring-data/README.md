# 스프링 데이터

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


## 스프링 데이터 JPA (Java Persistence API)
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

### 테스트 환경과 라이브 환경의 분리
* application.yml 파일을 분리하고 사용하는 방법이 명시적이긴 합니다만, 귀찮을 수 있습니다
* 더욱 간단한 방법은 @DataJpaTest 을 통해서 slicing 테스트를 수행하는 경우 application.yml 설정의 영향을 받지 않습니다. 

### JPA 의 마법과 같은 구현
```java
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByUsername(String username);

    @Query(nativeQuery = true, value = "select * from account where password = ?1")
    Optional<Account> findByPassword(String password);
}
```
* 1. 위와 같이 JpaRepository 를 상속받아서 Account 객체의 username 필드만 존재하면 findByUsername 과 같이 이름만 규약에 맞게 생성하는 경우 findBy 메소드가 자동으로 구현됩니다
* 2. 필요에 따라서는 @Query 어노테이션을 통해서 직접 쿼리를 수행할 수도 있다
  * Optional<Account> 값을 반환하게 할 수도 있습니다
  * 조회 시에는 반드시 * 를 통해서 모두 가져와야 객체가 정상적으로 반환됩니다

### Flyway 를 통한 데이터베이스 마이그레이션
> 데이터베이스의 변경 사항을 차곡차곡 쌓아서 백업이 가능한 도구이며 flyway 는 sql 파일을 사용합니다
* resources/db/migration 경로에 V1__init.sql 형식의 파일로 init 명만 변경하여 작성하게 되면 해당 스키마를 계속 보전해 줍니다
* 한 번 생성된 파일은 절대 건드려서는 안 되면 온전히 새로운 파일을 만들어야 합니다

### 레퍼런스
* [Hibernate ORM](http://hibernate.org/orm/what-is-an-orm)
* [Project Lombok](https://www.baeldung.com/intro-to-project-lombok)
* [Spring Data JPA @Query](https://www.baeldung.com/spring-data-jpa-query)
* [Flyway Database Migration](https://flywaydb.org/getstarted/)


## 스프링 데이터 Redis
```xml
<xml>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
</xml>
```
```bash
docker run --name redis_boot -d redis redis-server --appendonly yes
--
docker run -p 6379:6379 --name redis_boot -d redis
docker exec -it redis_boot redis-cli
```

### (String)RedisTemplate 을 이용한 데이터 처리
* Redis 를 띄웠다면 StringRedisTemplate 또는 RedisTemplate 을 통해서 사용할 수 있습니다
```text
127.0.0.1:6379> keys *
1) "name"
2) "id"

127.0.0.1:6379> get name
"suhyuk"
```

* 외에도 참고할 만한 내용은 아래의 커맨드와 spring.redis.* 설정 파일
```text
keys {pattern}
get {key}
hgetall {key}
hget {key} {column}
```

### @RedisHash 를 이용한 Repository 연동
* 1. Repository 테스트를 위해 Account 클래스를 @RedisHash("accounts") 로 생성합니다
* 2. CrudRepository<DataType, KeyType> 을 상속받은 AccountRepository (Bean) 를 생성합니다
* 3. 이렇게 생성된 값은 아래와 같으며 이러한 해시 값은 해시값이므로 hashget or hashgetall 을 통해 가져올 수 있습니다
```text
127.0.0.1:6379> keys *
1) "accounts"
2) "accounts:0cc527a1-5325-4da9-89b9-e72f72d79cf7"
3) "name"
4) "id"

127.0.0.1:6379> hget accounts:0cc527a1-5325-4da9-89b9-e72f72d79cf7 email
"psyoblade@suhyuk.me"

127.0.0.1:6379> hgetall accounts:0cc527a1-5325-4da9-89b9-e72f72d79cf7
1) "_class"
2) "me.suhyuk.spring.data.redis.account.Account"
3) "id"
4) "0cc527a1-5325-4da9-89b9-e72f72d79cf7"
5) "username"
6) "suhyuk"
7) "email"
8) "psyoblade@suhyuk.me"
```

### 레퍼런스
* [Spring Data Redis](https://spring.io/projects/spring-data-redis)
* [Redis Comamnds](https://redis.io/commands)
* [Spring Profiles](https://www.baeldung.com/spring-profiles)


## 스프링 데이터 MongoDB
* 몽고디비 의존성 추가
```xml
<xml>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-mongodb</artifactId>
    </dependency>
    <dependency>
        <groupid>de.flapdoodle.embed</groupid>
        <artifactid>de.flapdoodle.embed.mongo</artifactid>
    </dependency>
</xml>
```

### 도커를 통한 MongoDB 연동
```bash
docker run -p 27017:27017 --name mongo_boot -d mongo
docker exec -it mongo_boot bash
```

### @Document 를 이용한 Repository 연동
* 1. Repository 테스트를 위해 Account 클래스를 @Document(collection = "accounts") 로 생성합니다
* 2. MongoTemplate 을 통해 손쉽게 insert(new Account()) 가 가능합니다
* 3. 이렇게 생성된 값은 아래와 같으며 몽고 클라이언트를 통해 데이터를 가져올 수 있습니다
* 4. 마찬가지로 MongoRepository<DocumentType, KeyType> 을 통해 Repository 를 사용할 수도 있습니다
* 몽고디비 데이터 조회
```text
> use test
> db.accounts.findOne()
{
	"_id" : ObjectId("5e7ec7a12d50655527300b31"),
	"username" : "psyoblade",
	"email" : "psyoblade@hyuk.me",
	"_class" : "me.suhyuk.spring.data.mongo.account.Account"
}
```

### Embedded 몽고디비를 통한 단위 테스트 예제
* de.flapdoodle.embed.mongo 라는 특이한 이름의 패키지를 통해 단위테스트를 수행할 수 있습니다
* 마찬가지로 @DataMongoTest 라는 슬라이싱 테스트를 통해 수행할 수 있으며 Repository 예제와 연동하여 테스트 합니다

### 레퍼런스
* [MongoDB support](https://docs.spring.io/spring-data/mongodb/docs/current/reference/html/#mongo.core)


## 삽질을 통해 얻은 스프링 관련 팁

### 스프링 프로파일 관련
* 프로파일 정보는 테스트 환경에서만 설정할 수 있으며, 개별 클래스 단위로 설정해 주어야만 정상적으로 동작합니다
* 테스트 환경에서는 패키지 내의 모든 컴포넌트 스캔이 이루어지므로 어플리케이션 수준에서 코드가 들어가 있다면 (ApplicationRunner) 반드시 ComponentScan Filter 를 통해 제거해 준 별도의 Test Class 를 생성해 주어야만 합니다

### 개발 프로세스 및 습관
* 어떠한 경우라도 반드시 테스트 코드와 개발 후 검증 코드를 만들어야만 한다. API 수준에서 맞는 것 같지만, 다르게 동작하는 경우가 너무나 많다
* 특히, 스프링의 경우 숨겨진 경우가 많고, 내부 구조를 명확히 모르기 때문에 완전히 동작하는 것을 확실히 알 수 있을 때 까지 단위 테스트를 작성해야만 한다