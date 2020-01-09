# 인프런 스프링 부트 개념과 활용

## 4부 스프링 부트 활용

### 스프링부트 핵심 기능

#### SpringApplication
* 로깅은 기본은 INFO 이지만 VM Option -Ddebug 혹은 프로그램 아규먼트로 --debug 
* ApplicationContext 보다 이후에 실행되어야 하는 Bean 은 ApplicaitonListener<ApplicationStartedEvent> 를 구현해서 적용할 수 있습니다 (이 때에는 Bean으로 등록)
* ApplicationContext 보다 먼저 실행되는 Bean 들에 대한 트리거 작업은 <ApplicationStartingEvent>를 구현해도 수행되지 않으므로 별도로 addListener 통해 등록이 필요하다
* WebApplicationType { Servlet (default), Reactive(Webflux, not servlet), None }
* Spring Application 의 프로그램 내에서는 JVM 옵션인 -Dfoo 정보는 받아올 수 없고  Program Args 인 --bar 정보만 활용할 수 있다

#### 외부 설정

##### 0. 기타 참고 항목들
```bash
# 테스트를 제외하고 패키지 생성
mvn clean package -DskipTests

# application.properties 대신 아규먼트로 파라메터 전달하기
java -jar target/springcore-0.0.1-SNAPHOST.jar --psyoblade.name=suhyuk.park

# resources/application.properties 설정은 굳이 넣어 주어야만 한다
# Project-Module 설정에서 반드시 Test Resources 설정 값을 추가해 주어야만 한다.
psyoblad.name = park suhyuk

# TODO random.int 함수를 통해 임의의 랜덤 값을 얻을 수 있다
psyoblade.age = ${random.int}

# TODO placeHolder : 앞에서 사용한 변수는 아래에서 다시 사용할 수 있다
psyoblade.fullName = The great wizard ${psyoblade.name}

# 서버 포트는 위의 랜덤함수를 사용해서는 안되며 아래와 같이 0으로 설정해야 합니다
# server.port = 0 # 항상 가용한 랜덤 포트를 설정합니다
```

##### 1. 프로퍼티 우선 순위 (application.properties)
* 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties
* 테스트에 있는 @TestPropertySource - 컴파일 시에 src/main 설정을 src/test 가 덮어쓴다.
* - 경우에 따라 일부 속성값이 src/main 에만 존재하는 경우 Test 시에 해당 application.properties 
* - 즉, application.properties 값이 덮어써져서 Injection 이 실패하므로 test 쪽에도 넣어주어야만 한다
* - 이러한 상황을 회피하는 방법은 @SpringBootTest(properties="psyoblade.age=${random.int}) 를 쓸 수 있다
* - 또 다른 회피 방안은 아예 test/resources/application.properties 파일을 지우고 src/main 을 쓰게하는 방법이 있다 
* - 추천 방식은 라이브에만 application.properties 사용하고 @SprintBootTest(properties= {"k1=v1", "k2=v2"}) 이다
* - 너무 많은 프로퍼티 값들이 있다면 test.properties 파일을 만들고 @TestPropertySource 어노테이션을 씁니다
* - 이 경우에는 (locations = "classpath:/test.properties") 에 지정된 파일에 있는 k:v 값만 override 됩니다
* @SpringBootTest 애노테이션의 properties 애트리뷰트
* 커맨드 라인 아규먼트
* SPRING_APPLICATION_JSON (환경 변수 또는 시스템 프로티) 에 들어있는 프로퍼티
* ServletConfig 파라미터
* ServletContext 파라미터
* java:comp/env JNDI 애트리뷰트
* System.getProperties() 자바 시스템 프로퍼티
* OS 환경 변수
* RandomValuePropertySource
* JAR 밖에 있는 특정 프로파일용 application properties
* JAR 안에 있는 특정 프로파일용 application properties
* JAR 밖에 있는 application properties
* JAR 안에 있는 application properties
* @PropertySource
* 기본 프로퍼티 (SpringApplication.setDefaultProperties)

* 프로파일
* 로깅
* 테스트
* Spring-dev-tools

### 각종 기술 연동
* 스프링 웹 MVC
* 스프링 데이터
* 스프링 시큐리티
* REST API 클라이언트
* 다루지 않은 내용들

