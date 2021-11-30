# Spring 5 recipes - ch4 Spring REST
> REST 활용 예제

## 4-2. REST 서비스로 JSON 발행하기

### 4-2-1. Getter 규칙에 맞지 않는 외부 객체 사용 시에 ObjectMapper Visibility 설정
* KafkaAdminClient 같은 외부 라이브러를 이용하는 경우 Json 출력이 정상출력이 되는지
  - 직렬화를 위한 객체 구성이 어려운 외부 라이브러리를 사용하는 경우 ObjectMapper 사용 시에 주의가 필요하다
  - KafkaAdminClient 의 TopicListing 클래스의 경우 name 필드의 getter 가 name() 으로 되어 있어 출력이 안되기 때문에 아래와 같이 명시적으로 public 필드에 접근하는 것과 같이 Visibility 설정을 변경해야 합니다
```java
@Override
public String toString() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
    return mapper.writeValueAsString(object);
}
```
> which would make all member fields serializable without further annotations, instead of just public fields (default setting).
> <br>
> 결국 이 방식으로 출력하는 경우 객체로 넘길 수 없기 때문에 반환값이 모두 String 이 되어야만 Json 출력이 되며, 객체로 모두 관리하고 싶다면, 모든 객체를 동일하게 정의하여 직렬화할 수 있도록 구성해야만 한다

### 4-2-2. Kafka Admin 기능을 서비스에 내재화 하는 방법 설계
* Confluent API 기능이 제한적이라, KafkaAdminClient 라이브러리를 활용하는 경우 커플링을 어떻게 피할 수 있을까?
  - Interface 를 이용하여 제공하는 API 메소드를 추상화 할 것
  - Service 와 반환 DTO 객체의 구현에만 의존성을 가지도록 범위를 최소화 할 것
  - 쳐기서 DTO 객체의 반환 값에 대한 처리를 별도의 래퍼 클래스를 통해 반환할 것
* 외부 객체의 반환값을 인터페이스에서 가겨가기 싫은데 어떻게 하면 좋을까?
  - 래퍼 클래스를 선언하고, ObjectMapper 이용하여 toString 추상화 함수를 상속받은 개별 클래스를 구성하는 방안 
  - 이러한 경우 객체를 반환하지 못하고, String 으로 반환해야 하는 단점이 존재한다

> [엔터프라이즈 서비스 버스](https://ko.wikipedia.org/wiki/엔터프라이즈_서비스_버스) : 엔터프라이즈 서비스 버스(영어: Enterprise service bus, 약칭 ESB)는 서비스들을 컴포넌트화된 논리적 집합으로 묶는 핵심 미들웨어이며, 비즈니스 프로세스 환경에 맞게 설계 및 전개할 수 있는 아키텍처 패턴이다.


### 4-2-3. Kafka 혹은 Zookeeper 접근이 불가능한 경우에도 안정적인 서비스를 위한 설계
* Kafka 접근이 일시적으로 안 되는 경우 (몇 분까지 일시적이라고 볼 것인가?)
  - [Admin Client Configuration](https://docs.confluent.io/platform/current/installation/configuration/admin-configs.html) 페이지를 참고하여 학습합니다
  - 웹 관리도구의 경우 클러스터 서비스에 최대한 영향이 없어야 하며, 부하의 원인이 되어서는 안된다는 것이 원칙
    - API 및 Connection 타임아웃은 각각 6초, 3초로 접속 실패 시에 최대한 빠르게 종료합니다
    - 재시도 횟수는 500ms 백오프 후에 최대 1회만 수행하도록 하여 아래 설정으로 약 10시간 중지 후 재접속 성공
```java
    // API 요청에는 접속이 필수이므로, 접속의 타임아웃 값은 API 요청 타임아웃 보다는 작아야 한다 - 3/6초로 축소
    props.put(CommonClientConfigs.REQUEST_TIMEOUT_MS_CONFIG, 3000); // 커넥션 생성 요청 시에 타임아웃 시간 (default: 30 seconds)
    // 애플리케이션 기동 시에 리퀘스트 타임아웃 값보다는 API 타임아웃이 크게 설정되어야 하며, Client API 호출의 타임아웃 값 설정
    props.put(CommonClientConfigs.DEFAULT_API_TIMEOUT_MS_CONFIG, 6000); // API 호출 타임아웃 (default: 60 secs)

    // 아래의 RECONNECT 정보는 접속 실패 시에 지수적으로 늘려가면서 접속을 시도하는 시간을 의미 - 접속유지를 위해서는 설정을 유지할 필요 있음
    props.put(CommonClientConfigs.RECONNECT_BACKOFF_MAX_MS_CONFIG, 1000); // 연속적인 접속 실패시에 Backoff 지수적 상승 최대 시간 (default: 1 second)
    props.put(CommonClientConfigs.RECONNECT_BACKOFF_MS_CONFIG, 50); // Backoff 초기 시간 (default: 50ms -> 100ms ... 1000ms)

    // 아래의 RETRY 설정은 RECONNECT 실패가 반복되거나 여러가지 이유(타임아웃 등)로 접속에 실패한 경우 다시 시도하는 횟수 - 횟수만 1회로 조정
    props.put(CommonClientConfigs.RETRY_BACKOFF_MS_CONFIG, 500); // 리퀘스트 요청 실패 시에 대기 후에 다시 요청하는 시간 (default: 100ms)
    // 실패하는 경우 재시도하지 않으면, 일시적인 장애에도 서버를 계속 재시작 해주어야 하므로, 설정 조정이 필요함
    props.put(CommonClientConfigs.RETRIES_CONFIG, 1); // 리퀘스트 요청 실패 시에 최대 재시도하는 횟수 (default: 0 or 2147483647)
```
* Zookeeper 접근이 일시적으로 안 되는 경우
  - Kafka 접속만 된다면, 조회는 가능 하지만, POST 등의 변경 시에 500 오류발생
    - 이 경우에도 기존에 전송된 토픽이 생성되어 있는 경우가 있으므로, 주의가 필요함

### 4-2-4. 멀티 프로젝트 형태로 역할을 구분할 수 있도록 리팩토링
> `web <-> api <-> db` 와 같이 web 은 api 만 알면 구현이 가능하고, api 는 db 만 알면 구현이 가능해야 한다
> 즉, web 에서는 컨트롤러와 service 만 이용하여 단순한 호출을 통한 컨트롤러 구현만 존재해야 하며, 저장소의 구현과 무관하게 개발될 수 있어야 한다
> api 수준에서는 비지니스 로직 등이 포함될 수 있을 듯하고, 예외 처리에 대한 부분이 포함되면 좋을 것 같다
> common 경우에는 잘 변경되지 않는 domain 객체 및 crud 등의 기계적인 transaction 처리에 대한 부분을 처리할 수 있도록 구현되면 좋을 듯 하다
* [스프링 그레이들을 활용하여 멀티 프로젝트로 전환](https://jojoldu.tistory.com/123)
  - spring-rest-api : service 패키지 내에는 독립적인 기능을 제공할 수 있는 서비스들이 포함됩니다
    - 웹 서비스에서는 어떠한 서비스(kafka, druid)를 사용하더라도 서비스만 활용하면 domain 을 몰라야 합니다
  - spring-rest-common : domain(dto, dao), repository 즉, 저장소 관리를 위한 dao 객체를 저장
    - persistent 레이어에 대해 커플링 되어 있는 도메인 처리 구현이 포함됩니다.
  - spring-rest-web : 순수하게 컨트롤러만 존재하므로, controller 라는 패키지를 사용하지 않는 듯
* [멀티 모듈 설계 이야기](https://techblog.woowahan.com/2637/)
  - 결국에는 각 프로젝트의 역할을 명확하게 구분하고, 의존성을 최소화 하고, 개발 및 유지보수에 유리하게 하기 위함을 잊어서는 안되겠다 
  - api 프로젝트의 설계
    - 특정 서비스(kafka, druid 등)에 의존성을 가지는 서비스로 개발될 수 있습니다.
    - 필요에 따라서 별도의 모듈로 구분하여 의존성을 별도로 관리하는 것도 좋습니다. (api-kafka, api-druid)
    - 즉, 하나의 모듈이 수정/교체되는 경우에도 다른 서비스는 영향을 받지 않을 수 있기 때문입니다
  - common 프로젝트의 설계
    - 말 그대로 공통이므로, 최대한 의존관계가 없어야 하며, 순수 Java Class 로 구현되어야 합니다
  - web 프로젝트의 설계
    - 컨트롤러의 역할을 말하며, 순수하게 스펙만 정의할 수 있는 형태로 구현되어야 합니다
* 카프카, 드루이드 관리 도구 설계에 대한 방향
  - 패키지 이름에는 복수를 사용하면 표현이 달라질 수 있으므로 단수를 사용하기로 하며, web 의 경우에도 controller 등을 반드시 명시합니다
  - admin-web : kafka, druid 서비스를 이용하여 컨트롤러를 구현
    - me.suhyuk.spring.admin.controller.{kafka, druid} 패키지 구성
  - admin-api : kafka, druid 저장소를 접근하는 서비스를 구현
    - me.suhyuk.spring.admin.service.{kafka, druid} 패키지 구성
  - admin-kafka : kafka 에 의존적인 CRUD Domain (DTO, DAO) 및 Repository 구현
    - me.suhyuk.spring.admin.domain.{kafka, druid} 패키지 구성
    - 어차피 dao 를 사용할 일이 없기 때문에 dto, dao 패키지 레벨을 고려하지 않아도 될 것 같음
  - 문제점은 configuration 정보는 web 단에서 application.yml 파일로 유지되어야 하는데, 외부 의존성이 있는 경우는 분리가 어려움
    - application.yml 파일을 복제본을 유지하는 것도 조지 않으므로, web 단에서 kafka 의존성을 가져갈 수 밖에 없는 구조가 된다
    - 즉, external 의존성을 가진 경우는 스프링의 의존성 주입을 사용하는 순간 웹에 bound 되어 프로젝트 분리가 어렵게 되는 것 같다
