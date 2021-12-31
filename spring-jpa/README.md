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
  
* 영속성 컨텍스트 플러시 하는 방법
  - entityManager.flush()
  - transaction.commit()
  - JPQL 쿼리 (실행 시에는 강제로 flush 호출)

* 준영속 상태로 만드는 방법
  - entityManager.detach(member)
  - entityManager.clear()
  - entityManager.close()

## Entity Mapping 이해

* 객체와 테이블 : @Entity, @Table
* 필드와 컬럼 : @Column
* 기본 키 매핑 : @Id
* 연관 관계 매핑 : @ManyToOne, @JoinColumn

```java
import com.sun.istack.internal.NotNull;

import javax.annotation.Generated;
import java.io.Serializable;


@Entity
@Table(name = "ACCOUNT")
@NoArgsConstructor
@Getter
@Setter
@TableGenerator( // 테이블 제너레이터를 사용하는 경우에는 반드시 @Id 명시되어 있어야 테이블 생성이 됩니다
        name = "ACCOUNT_TAB_GENERATOR",
        table = "SEQ_TAB_ACCOUNT",
        pkColumnValue = "SEQ_ACCOUNT",
        allocationSize = 50
)
@SequenceGenerator(
        name = "ACCOUNT_SEQ_GENERATOR",
        sequenceName = "SEQ_ACCOUNT",
        initialValue = 1,  // 초기 값
        allocationSize = 50 // 미리 50개를 선점해 놓고, 여러번 오가는 네트워크 횟수를 줄이기 워한 방법
)
public class Account implements Serializable {  // Composite 키를 사용하는 경우는 반드시 직렬화 가능해야 합니다
  @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ACCOUNT_SEQ_GENERATOR")
  private Long id; // 키로 사용하는 경우 equals 사용이 필요하여 long 이 아니라 Long 클래스여야 합니다

  @Id @GeneratedValue(strategy = GenerationType.TABLE, generator = "ACCOUNT_TAB_GENERATOR")
  private Long num;

  @Column(
          name = "USERNAME"
          , updatable = false // 변경되지 않음
          , nullable = false // not null
          , length = 10
          , unique = true // add unique constraint - 복합키 구성 및 RENAME 할 수 없으므로
          , columnDefinition = "varchar(50) default 'EMPTY'" // 직접 명시
  )
  private String name;
  
  @Enumerated(EnumType.STRING) // Ordinal: 순서만 저장 (0~...), String: 값을 저장 (ADMIN ...)
  private RoleType roleType;

  // Java8 이후부터는 LocalDate, LocalDateTime 을 사용하면 Temporal 사용을 걱정할 필요가 없음
  private LocalDate xCreateDate;
  private LocalDateTime xCreateDateTime;

}
```
* 주의할 점
  - final class, enum, interface, inner 클래스 사용하면 안됨
  - 저장할 필드에 final 사용 금지
* 애플리케이션 실행 시점의 DDL
  - **로컬 장비에서만 사용할 것** - `<property name="hibernate.hbm2ddl.auto" value="create" />`
    - create : drop & lcreate
    - create-drop : drop & create & drop
    - update : alter add column
    - validate : check diff columns
    - none : 사용하지 않음
  - 개발 방언 (데이터베이스 별로 다른 쿼리) 지원
    - varchar (mysql), varchar2 (oracle)
* 컴포지트 키를 사용하는 경우 직렬화
  - 키에 해당하는 값을 먼저 전달할 필요가 있는 것으로 보이며, 직렬화가 필요하다고 함
* GeneratedValue 값의 전략 별 차이점
  - Identity : 컨텍스트 내에서만 값을 생성 및 조회하여 외부 영향을 받지 않고 생성 (추천되는 방법)
  - Sequence : persist 호출 시에 sequence 테이블로 부터 조회가 일어남, 다만 트랜잭션을 보장하여, 커밋되기 전까지 시퀀스값이 보장됨
  - Table : persist 호출 시에 table 조회가 일어나며, 트랜잭션 내이지만, 실제 테이블 값이 변경되어 외부영향이 있음


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
  - 해당 섹션에 추가된 클래스는 무조건 생성될 수 있으므로 항상 주의 또 주의해야 한다
```xml
<persistence-unit name="jpashop">
  <class>me.suhyuk.springjpa.domain.Member</class>
  <properties>
  ...
  </propertis>
</persistence-unit>
```


## 새롭게 알게된 사실

### 자바
* Map.put 함수의 반환값은 이전값을 반환하게 되는데 처음 입력되는 값은 없기 때문에 null 값을 반환한다
* map 함수는 입력이 Function 이고 출력이 특정 타입인 함수를 말한다
  - `Collection<T> = collection.map(Function<T>)`
  - 즉, 외부에 영향을 주지도, 받지도 않기 때문에 순수한 함수적인 특성을 가지며, 함수를 전달한다는 개념만 기억하면 된다
* forEach 경우는 일반적인 Collection 내부의 요소에 대해 특정 함수를 적용하기 위한 함수를 말한다
  - `void = collection.forEach(o -> apply(o))`
  - 즉, 외부함수를 사용할 수도 있고, 외부에 영향을 줄 수도 있다
```java
class Member {
  List<Member> generateMembers(List<String> names) {
    List<Member> members = new ArrayList<>();
    names.stream().map(name -> Member.builder().name(name).build()).forEach(member -> members.add(memberRepository.save(member)));
    return members;
  }
}
```
* repository 객체 반환 시에 isPresent 대신에 ifPresent 구문을 통해 예외를 반환할 수 있습니다
```java
class Repository {
  private void validateDuplicateMember(Member member) {
    memberRepository.findById(member.getId()).ifPresent(m -> {
      throw new IllegalStateException(String.format("이미 존재하는 고객'%s' 입니다", m));
    });
  }
}
```


### 인텔리제이 관련
* 현재 선언된 구문의 변수를 자동 선언 : `Option + Command + V`
* 인텔리제이 화면 단축키 출력 플러그인 : `Key Promoter X`, `Presentation Assistant`

### 그레이들 관련
* 그레이들 리프래시 : `Shift + Command + I`

## 레퍼런스
* [스프링부트 2.3.12 레퍼런스](https://docs.spring.io/spring-boot/docs/2.3.12.RELEASE/reference/html)
