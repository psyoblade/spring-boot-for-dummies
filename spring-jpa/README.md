# Spring Boot Web Application 개발

* I. JPA 영속성 컨텍스트의 이해
* II. Entity Mapping 이해
* III. 연관관계 매핑
* IV. 프록시와 연관관계 관리
* V. 값 타입
* VI. 객체지향 쿼리 언어


## I. JPA 영속성 컨텍스트의 이해
> **Persistence Context** : "엔티티를 영구 저장하는 환경"
> `EntityManager.persist(entity)` 는 persist 메소드는 영속성 컨텍스트에 저장한다는 의미입니다 
> 애플리케이션(Object)과 데이터베이스(Database) 사이에서 캐싱 혹은 다른 계층간의 변환을 수행
> JPA 는 interface, 실제 구현은 Hibernate, OpenJPA, EclipseLink 등이 있으며 Spring 은 Hibernate 를 사용합니다 
> ORM 기술의 구현체이며, Object Relational Mapping 즉, 자바 객체를 관계형 데이터베이스로 맵핑해 주는 기술을 말합니다.
> 또한, 인터페이스 이름만으로도 쿼리를 동적으로 만들어내는 것이 가능합니다
```java
interface MemberRepository {
  // select m from Member m where m.name = ?
  Optional<Member> findByName(String memberName);
}
```

### 1. EntityManager 에 1:1 로 하나의 컨텍스트 객체를 관리 운영되며, 생명주기를 가진다
  - 비영속 : Entity 객체를 생성만 하여 JPA 와 무관한 상태
  - 영속 : EntityManager 에 persist 호출 이후에 영속 상태로 빠짐
  - 준영속 : detach 메소드를 통해서 제거한 상태 
  - 삭제 : remove 통해서 영구 삭제하는 상태

### 2. Persistence Context 1 Level Cache
  - persist 호출 시에 1차 캐시에 저장되어 있고, 이를 다시 가져오는 경우 1차 캐시를 반환한다
  - 다만, 1차 캐시에 없는 경우 데이터베이스에 조회하고, 1차 캐시에 저장하고 결과를 반환한다
  - 트랜잭션 내에서만 동작하는 캐시이므로, 동일한 데이터베이스 조회를 줄이는 효과가 있다고 볼 수 있다
  - Repeatable Read 를 Application 수준에서 제공

### 3. "쓰기 지연 SQL 저장소" 동작
  - commit 메소드 호출 전의 모든 persist 동작은 1차 캐시를 거쳐, "쓰기 지연 저장소"에 저장됩니다
  - flush 과정에서 일괄 commit 됩니다 ` <property name="hibernate.jdbc.batch_size" value="10"/>` 설정으로 조정이 됩니다

### 4. "변경 감지" 동작
  - 컨텍스트 내의 엔티티의 경우 변경된 경우 감지하여 commit 시에 상태를 저장합니다
  - 동일한 값으로 변경하는 경우에도 변경되지 않았기 때문에 수정되지 않습니다
  - 컨텍스트 내에서는 아무리 변경되어도 최종적으로 커밋 직전 상태가 이전과 같다면 변경되지 않습니다
  
### 5. 영속성 컨텍스트 플러시 하는 방법
  - entityManager.flush()
  - transaction.commit()
  - JPQL 쿼리 (실행 시에는 강제로 flush 호출)

### 6. 준영속 상태로 만드는 방법
  - entityManager.detach(member)
  - entityManager.clear()
  - entityManager.close()


## II. Entity Mapping 이해

* 객체와 테이블 : @Entity, @Table
* 필드와 컬럼 : @Column
* 기본 키 매핑 : @Id
* 연관 관계 매핑 : @ManyToOne, @JoinColumn

```java
import com.sun.istack.internal.NotNull;
import javax.annotation.Generated;
import java.io.Serializable;

@Entity(name = "Account")
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
    - create : drop & create
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


## III. 연관관계 매핑
> 이전 장에서 관계형 데이터베이스 기준으로 설계를 하는 경우 Id 값만 가지는 N개 테이블인 OrderItem 테이블 사용시에 객체스러운 접근이 불가능한 상황이 발생하기 때문에 이를 연관관계 맵핑을 통해 해결하고자 합니다
> 객체의 참조와 테이블의 외래 키를 매핑합니다. 즉, **객체가 메모리 상에서는 레퍼런스로 관리되어야 하지만, 테이블은 외래키를 통해서 별도의 테이블로 유지**되어야 하는 것이 핵심입니다

* 테이블은 외래키를 통해 가져오지만, 객체는 레퍼런스를 통해 가져오기 때문에 객체지향 스럽게 모델링을 하는 경우에 어떻게 구현을 해야 하는가?

### 1. 단순 연관관계 매핑

#### 1.1 @ManyToOne + @JoinColumn 어노테이션을 통해 연결만 해줘도 연관관계 매핑이 가능
```java
public class Player {
  @Id @GeneratedValue
  @Column(name = "PLAYER_ID", nullable = false)
  private Long id;
  @JoinColumn(name = "TEAM_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Team userDetail;
  @Column(name = "PLAYER_NAME")
  private String name;
  public Player() {
  }
}

public class Team {
  @Id
  @GeneratedValue
  @Column(name = "TEAM_ID", nullable = false)
  private Long id;
  @Column(name = "TEAM_NAME")
  private String name;
  public Team() {
  }
}
```

### 2. 양방향 연관관계와 연관관계의 주인 1- 기본
> 관계형 데이터베이스에서는 FK로 연결되므로 양쪽은 편하게 가져올 수 있으나, 객체에서는 별도의 조치가 필요하다 (mappedBy 구문이 필요함)

* 객체는 단방향 관계를 각각 생성해 주어야하지만, 테이블의 연관관계는 FK 1개로 해결된다
  - 즉, 멤버가 팀의 레퍼런스를 변경할 수도 있고, 팀의 멤버를 제거할 수도 있기 때문에 어느 쪽이 FK 관계를 맵핑하고 있어야 하는지를 하나로 정해야 하고 이것이 연관관계의 주인(Owner)으로 지정해야만 한다
  - 그리고 연관관계의 주인만 읽고, 쓸수 있으며, Owner 가 아닌 경우에는 mappedBy 속성으로 지정해야 합니다
  - 일반적으로 외래키가 존재하는 클래스가 Owner 로 유지하는 것이 좋다

* 유의할 사항
  - 양방향 관계의 경우 ToString 이 서로 참조하고 있어 출력시에 무한루프에 빠지게 되어 OneToMany 테이블에만 관계객체를 포함하여야 합니다
  - 관계생성 시에 참조를 하기위한 Id 값이 생성되고 난 이후(팀이 있어야 멤버가 생성될 수 있다)에 정상적으로 저장됩니다
  - 즉, flush, clear 하지 않는다면 1차캐시의 객체를 그대로 가져오기 때문에 문제가 될 수 있고 단위 테스트에서도 문제가 됩니다
  - 안전한 방법은 양쪽 모두 레퍼런스를 넣어주는 것이 가장 좋습니다 (해서 양쪽에서 수정하지 않고, 멤버 추가시에 멤버객체를 같이 추가해줍니다)
    - Setter 메소드 대신 명령을 포함하는 메소드로 구현합니다 `setTeam` 대신 `changeTeam` 등으로 변경합니다
    - Owner 에 "편의 메소드"를 작성하든지, Mapped 쪽에 작성하든지 한 쪽에서만 작성하라

* 처음 설계 시에는 절대 양방향 매핑을 사용하지 않습니다
  - 아무리 복잡해도 단방향으로만 초기 설계와 구현을 하는 것이 좋습니다. 
  - 기본적으로 단방향만으로도 구현이 가능하고, 양방향 조회는 필요시에만 합니다 (편의 메소드 등 고민거리만 많아집니다)

* 아래와 같이 `Setter` 를 제거하고, Builder 와 changeTeam 함수를 통해 깔끔하게 정리되었습니다
```java
@NoArgsConstructor
public class Player {
  @Id @GeneratedValue
  @Column(name = "PLAYER_ID", nullable = false)
  private Long id;
  @JoinColumn(name = "TEAM_ID")
  @ManyToOne(fetch = FetchType.LAZY)
  private Team userDetail;
  @Column(name = "PLAYER_NAME")
  private String name;
  @Builder
  public Player(String name) {
    this.name = name;
  }
  public void changeTeam(Team userDetail) {
    this.userDetail = userDetail;
    this.userDetail.getPlayers().add(this);
  }
  @Override
  public String toString() {
    return "Player{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }
}
// main-class
public class SpringJpaApplication {
  public static void main(String[] args) {
    Team userDetail = Team.builder().name("엔씨다이노스").build();
    em.persist(userDetail);

    Player player = Player.builder().name("박수혁").build();
    player.changeTeam(userDetail);
    em.persist(player);
  }
}
```

* 이번에는 주문예제를 통해서 연관관계 매핑을 실습해보겠습니다
  - 여기서 중요한 부분은 자연스러운 스토리를 만드는 것으로 판단되며, 여러개의 주문 아이템을 하나의 주문에 추가할 수 있어야 합니다
  - 또한 양방향 관계를 만들어줄 필요가 있기 때문에 "편의 메소드" `Order.addOrderItem` 과 `OrderItem.registerOrder` 메소드를 구현합니다
```java
public class Order {

  @Id @GeneratedValue
  @Column(name = "ORDER_ID")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MEMBER_ID")
  private Member member;

  @Column(name = "ORDER_DATE")
  private LocalDateTime orderDate;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  public void selectMember(Member member) {
    this.member = member;
  }

  public void addOrderItem(OrderItem orderItem) {
    orderItem.registerOrder(this);
    this.orderItems.add(orderItem);
  }

  @Builder
  public Order(OrderStatus orderStatus) {
    this.orderDate = LocalDateTime.now();
    this.orderStatus = orderStatus;
  }
}

// main-class
public class SpringJpaApplication {
  public static void main(String[] args) {
    Item dosirak = Item.builder().name("도시락").price(100).build();
    em.persist(dosirak);
    Item sinRamen = Item.builder().name("신라면").price(200).build();
    em.persist(sinRamen);
    Item jinRamen = Item.builder().name("진라면").price(150).build();
    em.persist(jinRamen);

    Member member = Member.builder().name("박수혁").build();
    em.persist(member);

    OrderItem orderItem1 = OrderItem.builder().item(dosirak).count(5).build();
    em.persist(orderItem1);
    OrderItem orderItem2 = OrderItem.builder().item(sinRamen).count(10).build();
    em.persist(orderItem2);
    OrderItem orderItem3 = OrderItem.builder().item(jinRamen).count(10).build();
    em.persist(orderItem3);

    Order order = Order.builder().orderStatus(OrderStatus.ORDER).build();
    order.addOrderItem(orderItem1);
    order.addOrderItem(orderItem2);
    order.addOrderItem(orderItem3);
    member.doOrder(order);
    em.persist(order);
  }
}
```
* 아래의 질의문으로 전체 데이터를 조회할 수 있습니다
```bash
SELECT oi.*, o.*, i.* FROM ORDER_ITEM oi
INNER JOIN ORDERS o on oi.order_id = o.order_id
INNER JOIN ITEM i on oi.item_id = i.item_id;
```

### 3. 양방향 연관관계와 연관관계의 주인 2- 주의점, 정리

### 4. 다양한 연관관계 매핑
* N:1
* 1:N
* 1:1
* N:M

### 5. 고급 매핑

#### 5-1. 상속 전략 (Inheritance strategy)
> 관계적으로 필요하며, 추상화된 상위 테이블을 가지는 경우, 최상위 테이블을 추상 클래스로 생성합니다

* SINGLE_TABLE : 하나의 테이블에 모든 컬럼을 다 넣는 방법 (MongoDB style - default style)
  - Default 전략이며, discriminator column 이 자동으로 들어갑니다
  - 하나의 테이블이라 운영 및 조회 성능상 이점이 있다 <-> 다른 컬럼에 대해 Null 허용, 테이블 크기
* JOINED : 상속관계를 테이블을 분리하는 방법 (Object oriented style)
  - @DiscriminatorColumn 을 통해서 메인 테이블의 구분자가 있어 명시적으로 구분하기 좋다
  - 정규화의 장점, 참조 무결성 <-> 테이블이 늘어나서 관리 비용 및 조회 시에 성능 및 복잡성 다소 증가
* TABLE_PER_CLASS : 개별 테이블을 공통 컬럼을 유지해서 별도 테이블로 구성하는 방법 (Superset style)
  - 구분 컬럼도 필요없고, 개별 테이블이라 성능도 좋을 것 같지만, 코드 수정 및 유지보수에 최악의 구조
  - 모든 테이블을 Item 기준으로 조회하고 싶을 때에 Union 명령을 통해서 가져오는 성능이슈가 있다

#### 5-2. 매핑 정보 상속 (Mapped Superclass)
> 관계적으로 무관하지만, 항상 사용되는 공통 컬럼들이 존재하는 경우 (created, modified 등의 컬럼들) 추상클래스 상속을 통해 @MappedSuperclass 지정
> 운영시에 필요한 다양한 정보는 기본적으로 들어가기 때문에 BaseEntity 클래스를 생성해서 사용하는 것을 검토해야 한다

* 특징 및 장단점
  - 상속관계가 아니며, 엔티티도 아니라서 테이블과 매핑할 수 없으며, 조회가 불가능하다
  - 자식 클래스에 다양한 공통 필드(등록자, 생성일자 등)를 손쉽게 제공할 수 있다

#### 5-3. 실전 예제
> 상품의 종류는 음반, 도서, 영화가 있고 향후 확장가능성이 있으며, 모든 데이터의 등록 및 수정일은 필수 컬럼이다

```bash
Product = { Album, Book, Movie } -> Inheritance strategy
Common = { RegisteredDate, ModifiedDate } -> MappedSuperclass
```


## IV. 프록시와 연관관계 관리
> 왜 써야 하는지를 이해해야 한다. 연관관계가 있지만 필요한 정보만 가져오는 것이 효과적일 것이다. 즉, 가능하면 끝까지 기다렸다가 필요한 정보만 가져옵니다.
> Mock 객체와 유사하게 실제 데이터베이스 조회를 하지 않고 getReference (프록시 객체는 실제 객체를 상속받은 객체이다)통해서 사용하지 않는 객체를 줍니다

### 1. 프록시의 개념
* 프록시 객체는 처음 한 번만 초기화
* 프록시는 항상 유지되고 생성된 객체를 이용하여 프록시가 전달해줌
* 프록시는 원본 엔티티의 상속이므로 비교 시에 instance of 사용
* 영속성 컨텍스트에 엔티티가 이미 있다면 getReference 는 실제 엔티티를 반환
* 영속성 컨텍스트 지원을 못 받는 준영속상태일때 초기화 이슈 조심
  - em.detach(entity) 혹은 em.clear() 혹은 em.close() 호출 시에 영속 -> 준영속 상태가 됩니다

```bash
# 인스턴스 초기화 여부 확인
EntityManagerFactory().getPersistenceUnitUtil().isLoaded(Entity entity);
# 프록시 클래스 확인
entity.getClass().getName()
# 프록시 강제 초기화
org.hibernate.Hibernate.initialize(entity);
```

### 2. 즉시 로딩과 지연 로딩 
* 가급적 지연로딩을 사용해야 하는데, 즉시로딩의 경우 예상치 못한 SQL발생 가능성이 있다
  - 레퍼런스 하는 객체들이 늘어나는 경우 수많은 Join 이 미친듯이 늘어날 수 있겠구나
* 즉시로딩은 JPQL 에서 N+1 문제가 있을 수 있다
  - JPA 경우는 최적화된 Join 쿼리를 만들 수 있지만 JPQL 경우는 일단 기본 쿼리(1)가 나가고, 레퍼런스 참조에 따라 그 횟수(N)만큼 질의가 발생할 수 있다 
* @ManyToOne, @OneToOne **기본설정이 Eager 이므로 Lazy 설정**이 필요함
* @OneToMany, @ManyToMany 기본은 지연으로 설정되어 있음

> 위와 같이 다양한 방법을 해결하기 위해 몇 가지 접근방법을 제안합니다 (1: fetch join 방식, 2: entity-graph 방식, 3: batch size 방식)

### 3. 영속성 전이(Cascade) 와 고아 객체
> 부모 저장시에 자식도 다 같이 싸그리 저장하고 싶을 때에 사용하는 것이 영속성 전이(Cascade)라고 합니다. 데이터베이스의 삭제 혹은 Drop 시에 cascade 옵션과 유사하게 동작합니다

* 상위객체와 하위객체 모두를 매번 persist 해야 하는 불편함을 줄일 수가 있다 (ALL or PERSIST 정도만 씁니다)
* 영속성 정의와 연관관계 매핑은 전혀 관계가 없습니다
* 하나의 부모가 자식들의 객체를 관리하는 경우 (게시글 + 첨부파일)는 사용하기 용이하지만, **독립적인 객체인 경우는 사용해서는 안된**다
  - 즉, 라이프싸이클이 동일한 경우의 객체간에 관리에만 사용해야 합니다
* 고아객체(orphan remove) 는 엔티티간의 연관관계가 끊어진 경우 자동 삭제하는 옵션
  - 위험할 수 있기 때문에 하나의 라이프싸이클에서 사용 및 활용되는 경우에만 사용해야 합니다

#### 3-1. 영속전이 + 고아객체 그리고 생명주기
> CascadeType.ALL && orphanRemoval = true 옵션을 주는 경우

* 부모 엔티티를 통해 자식의 생명주기를 싸잡아서 관리할 수 있음
* 도메인 주도 설계의 Aggregate Root 개념 구현 시에 유용함
* 글로벌 패치 전략 (fetch = LAZY)
  - ManyToOne, OnoToOne 관계는 반드시 Lazy 전략으로 변경
* 영속성 전이 전략 (cascade = ALL)
  - 같은 맥락에 하나의 부모에 대한 관계가 순수하다면 ALL 설정


### 4. 실전 예제


### 5. 질문 사항
* 만약에 Lazy 전략으로 레퍼런스 가져오되 나중에 하나만 읽는 경우와, 둘다 읽는 경우 어떻게 동작하는가?


## V. 값 타입
> 복잡한 객체의 세상에서 조금이라도 단순화하기 위해 값 타입을 만들어내었다. 단순하고 안전하게 사용하기 위함
> 특히 embedded 유형의 객체에 대해서 사용할 때에는 절대 side-effect 없이 **레퍼런스를 공유해서는 안되고 복사해서 사용**해야만 합니다 
> 값 타입은 인스턴스가 달라도 값이 같으면 같은 것으로 봅니다. 복잡하게 사용하고 구성하는 것은 적절하지 않고 자주 변경되지 않는 것으로 사용해야합니다
> **값 타입 전체를 쉽게 쓰고, 지우는 경우에만 값 타입으로 써라** 그게 아니라면 Entity 를 사용하고 해당 복잡한 객체를 Embed 하여 써라

### 1. 값 타입의 이해와 활용

* 객체 타입의 한계
  - 항상 값의 복사를 통해 사용해야 부수효과 없는 안전한 코딩이 가능합니다
  - 임베디드 타입처럼 직접 정의한 타입은 레퍼런스 타입이라 참조값을 넣는 것을 피하기가 아주 아주 어렵다 (컴파일 수준에서 찾기 어렵다)
* 불변 객체
  - 이러한 문제점을 해결하기 위한 방법은 값 타입은 불변 객체로 설계해야만 합니다
  - 생성자로만 값을 설정할 수 있도록 (Builder 패턴 활용) 하고 수정자를 만들지 않도록 합니다
* 값 타입 컬렉션
  - 엔티티와는 다르게 id 가 없어야 하고, 이를 별도의 테이블로도 관리 되어야 합니다
  - 당연하지만 별도 테이블이므로 지연 로딩이 되어야 합니다.
  - 수정하기 위해서는 setter 가 없어야 하므로, 특정 레퍼런스 자체를 새로 set 하는 것이 최선입니다.
  - 특히 집합내의 임의의 값을 변경할 것으로 보이지만, **전체가 삭제되고 나머지가 다시 들어**가는 구조다 (OrderColumn 꼼수도 있다)

### 2. 값 타입 vs. 엔티티

* 값 타입
  - 식별자가 없다
  - 생명주기가 엔티티에 의존한다
  - 공유하지 않고 복사해서 사용한다
  - 불변객체로 만들어서 사용한다
* 엔티티 
  - 식별자가 있다
  - 생명주기 관리가 된다
  - 공유해서 사용할 수 있다 

### 3. 실전 예제
* 클래스 내의 멤버변수 액세스하는 모든 상황 시에 getter 통한 비교가 되어야 프록시 사용에도 문제 없이 객체 동등성 비교가 가능합니다

### 4. 질문 사항



## VI. 객체지향 쿼리 언어

## VII. 시행착오
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
  <class>Member</class>
  <properties>
  ...
  </propertis>
</persistence-unit>
```


## VIII. 새롭게 알게된 사실

### 1. 3가지 스프링 웹 개발 방식
#### 1.1 정적인 콘텐트를 반환
* 페이지 이름이 그대로 요청되는 경우는 static/<name>.html 이 있어서 Tomcat 이 직접 반환합니다
#### 1.2 동적인 콘텐트를 반환
* 반환값이 String 인 Controller 방식이며, viewResolver 가 Template 페이지(template/<name>.html)를 렌더링을 하여 반환합니다
#### 1.3 API 통하여 객체를 반환
* @ResponseBody 어노테이션이 반드시 필요하며, Http Body 에 직접 문자를 반환합니다
  - 기본 문자 (String)처리는 StringHttpMessageConvert 통하여 반환
  - 기본 객체 (Object)처리는 MappingJackson2HttpMessageConverter 통한 방식
* 기본은 Json 문서를 반환하지만, Request Header 의 Accept, Controller 의 Response Type 조합으로 결정됩니다

### 2. 스프링 의존성 주입방식
> 의존성 주입 (DI: Dependency Injection) : 생성자를 통하여 외부 의존성을 받을 수 있도록 인자에 추가되는 것을 말합니다
* @Autowired Bean 객체 : 스프링 프레임워크가 관리하는 객체, 즉 관리 대상 객체 - 다른 표현으로 @Component 라고 말합니다
* 어딘가에 생성자를 통해서 해당 Bean 객체를 생성할 수 있도록 환경이 제공되어야만 합니다 (@Controller, @Service, @Repository 등)

#### 2.1 ComponentScan
* @Component 종류의 어노테이션을 통해서 스프링이 자동으로 주입해주는 방식
  - @SpringBootApplication 패키지 하위에 존재하는 @Component 만 자동으로 Scan 됩니다
  - 빈 객체 등록시에 반드시 1개의 객체만 관리되는 Singleton 방식으로만 등록됩니다
  - *단점은 해당 컴포넌트 변경 수정 시에 여러군데 수정이 필요할 수 있다*
```java
@Service
class FooService { void bar() { /* ... */ } }

class UnitTest {
    @Autowired FooService fooService;
    @Test void test() { fooService.bar(); }
}
```
#### 2.2 Configuration
* @Configuration 종류의 어노테이션을 통해서 사용자 클래스를 @Bean 어노테이션을 통해서 주입하는 방식
  - *개별 구성을 해야 하는 불편함이 있지만, Concrete Class 변경이나 의존성이 변경되는 경우 한 군데만 바꾸면 된다*
```java
@Configuration
class MyConfig {
  @Bean
  public FooService fooService() {
      return new FooService(barRepository());
  }
  @Bean
  public BarRepository barRepository() {
      return new BarRepository();
  }
}
```
### 2.3 주입방식 3가지 (Constructor, Member Variable, Setter)
* Constructor : 가장 추천되는 방식으로 생성시점에 한 번만 호출되는 방식
* Member or Setter : 이 두가지 방식은 외부 노출 및 변경에 위험할 수 있어 추천되지 않습니다


### 3. 패키지 별 설계 방식
#### 3.1 Repository 패키지
* 레포지토리 객체는 기계적인 용어 CRUD (save, findById, findAll 등)을 사용하는 것이 좋고,
#### 3.2 Service 패키지
* 서비스 객체는 비지니스 적인 용어 (join, findMember) 등과 같이 업무에 관련된 도메인을 사용합니다
#### 3.3 스프링을 사용하는 이유
> `확장에는 열려있고, 수정, 변경에는 닫혀있다` 인터페이스를 통한 구현을 지향하여 데이터베이스 변경 시에도 수정 범위를 최소화 할 수 있다
```java
@Configuration
class CustomConfiguration {
  @Autowired DataSource dataSource;  
  @Bean
  public UserRepository userRepository() {
    // return new MemoryUserRepository();      // 이전 구현
    return new JdbcUserRepository(dataSource); // 신규 구현
  }
}
```
#### 3.4 단위테스트 내부에서 Transactional
> @Transactional 사용시에 단위 테스트 내에서는 모두 rollback 되므로, 테스트 시에 삭제가 필요없습니다


### 4. 자바

#### 4.1 자바 기본
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

#### 4.2 테스트 케이스의 중요성
> 개발의 60~70%를 테스트 코드 작성에 투입되고 있는가?

### 5. 인텔리제이 관련
* 구문변수를 자동 선언 : `Option + Command + V`
* 인덴테이션 자동 정리 : `Option + Command + L`
* 한라인으로 자동 정리 : `Option + Command + N`
* 애매한 경우 알아서 자동완성 : `Shift + Command + Enter`
* 리팩토링 컨텍스트 메뉴 출력 : `Ctrl + T` or `Shift + Ctrl + T`
* 인텔리제이 화면 단축키 출력 플러그인 : `Key Promoter X`, `Presentation Assistant`

### 6. 그레이들 관련
* 그레이들 리프래시 : `Shift + Command + I`

### 7. 기타 운영
* 잊고 있었는데, 관계형 데이터베이스의 경우 DBA가 필요하며, 상시 운영 및 Slow-query 등에 대한 모니터링 및 알림이 필요하다


## IX. 레퍼런스
* [스프링부트 2.3.12 레퍼런스](https://docs.spring.io/spring-boot/docs/2.3.12.RELEASE/reference/html)
