# Spring Boot Web Application 개발

## JPA 영속성 컨텍스트의 이해
> **Persistence Context** : "엔티티를 영구 저장하는 환경"
> `EntityManager.persist(entity)` 는 persist 메소드는 영속성 컨텍스트에 저장한다는 의미입니다 
> 애플리케이션(Object)과 데이터베이스(Database) 사이에서 캐싱 혹은 다른 계층간의 변환을 수행

* EntityManager 에 1:1 로 하나의 컨텍스트 객체를 관리 운영되며, 생명주기를 가진다
  - 비영속 : Entity 객체를 생성만 하여 JPA 와 무관한 상태
  - 영속 : EntityManager 에 persist 호출 이후에 영속 상태로 빠짐
  - 준영속 : detach 메소드를 통해서 제거한 상태 
  - 삭제 : remove 통해서 영구 삭제하는 상태

* Persistence Context 1 Level Cache
  - persist 호출 시에 1차 캐시에 저장되어 있고, 이를 다시 가져오는 경우 1차 캐시를 반환한다
  - 다만, 1차 캐시에 없는 경우 데이터베이스에 조회하고, 1차 캐시에 저장하고 결과를 반환한다
  - 트랜잭션 내에서만 동작하는 캐시이므로, 동일한 데이터베이스 조회를 줄이는 효과가 있다고 볼 수 있다
  - Repeatable Read 를 Application 수준에서 제공

* "쓰기 지연 SQL 저장소" 동작
  - commit 메소드 호출 전의 모든 persist 동작은 1차 캐시를 거쳐, "쓰기 지연 저장소"에 저장됩니다
  - flush 과정에서 일괄 commit 됩니다 ` <property name="hibernate.jdbc.batch_size" value="10"/>` 설정으로 조정이 됩니다

* "변경 감지" 동작
  - 컨텍스트 내의 엔티티의 경우 변경된 경우 감지하여 commit 시에 상태를 저장합니다
  - 동일한 값으로 변경하는 경우에도 변경되지 않았기 때문에 수정되지 않습니다
  - 컨텍스트 내에서는 아무리 변경되어도 최종적으로 커밋 직전 상태가 이전과 같다면 변경되지 않습니다


## 시행착오
* [Spring Boot Documentation](https://spring.io/projects/spring-boot#learn)
* [H2 Database Engine](https://www.h2database.com/html/main.html)
  - Embedded mode : jdbc:h2:~/Datasets/h2/jpashop
  - 데이터베이스 파일 : ~/Datasets/h2/jpashop.mv.db
* [H2 Database tutorial](http://www.h2database.com/html/tutorial.html)
  - `bin/h2-server.sh` 을 아래와 같이 생성하고 출력 시에 포트가 TCP Server 포트는 다른것을 확인
```bash
#!/bin/sh
dir=$(dirname "$0")
java -jar "$dir/h2-1.4.199.jar" -webAllowOthers -tcpAllowOthers -tcpPort 8043
```
  - Server mode : `jdbc:h2:tcp://localhost:8043/~/jpashop` 으로 접속하면 성공
* Gradle 빌드의 경우 persistence.xml 파일에 아래와 같이 Entity 가 명시되어야만 한다
```xml
<persistence-unit name="jpashop">
  <class>me.suhyuk.springjpa.Member</class>
  <properties>
  ...
  </propertis>
</persistence-unit>
```
