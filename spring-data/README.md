# 스프링 데이터

## Hikari Connection Pooling
> 레퍼런스에 포함된 FAQ 문서를 참고하여 기본 값과 최적치를 본인이 설정해야 합니다 

### H2 Console 연결
* 콘솔 접근이 가능하도록 설정 변경 후 http://localhost:8080/h2-console 접속
```properties
spring.h2.console.enabled=true
```

### MySQL JDBC 설정
* pom.xml 파일에 mysql jdbc 설정
```xml
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
</dependency>
```

### MySQL Docker 인스턴스 생성 및 삭제 
* 커널을 공유하기 때문에 빨리 설치 및 테스트를 할 수 있습니다
```bash
$> docker run -p 3306:3306 --name mysql_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=springboot -e MYSQL_USER=psyoblade -e MYSQL_PASSWORD=2 -d mysql
$> docker ps
CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                               NAMES
37662b2af923        mysql               "docker-entrypoint.s…"   12 seconds ago      Up 11 seconds       0.0.0.0:3306->3306/tcp, 33060/tcp   mysql_boot

$> docker exec -i -t mysql_boot bash
$> mysql -u psyoblade -p

$> docker stop mysql_boot
$> docker rm mysql_boot
```

### MariaDB Docker 인스턴스 
* MySQL 경우 라이센스를 사서 써야 하므로 Community 버전인 MariaDB 추천
```bash
$> docker run -p 3306:3306 --name mysql_boot -e MYSQL_ROOT_PASSWORD=1 -e MYSQL_DATABASE=springboot -e MYSQL_USER=psyoblade -e MYSQL_PASSWORD=2 -d mariadb
```

### PostgreSQL 사용
* MariaDB 경우도 GPL2 이므로 소스공개 의무가 있고, 상용화에도 문제가 없는 PostgreSQL 추천

## 레퍼런스
* [HikariCp](https://github.com/brettwooldridge/HikariCP)
* [HikariCp FAQ](https://github.com/brettwooldridge/HikariCP#frequently-used)
