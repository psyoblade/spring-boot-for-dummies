# 4부 스프링 부트 활용

## 목차
1. [스프링 어플리케이션](#스프링-어플리케이션)
2. [외부설정 (Configuration)](#외부설정)
3. [프로파일 (Profile)](#프로파일)
4. [로깅설정 (Logging)](#로깅설정)
9. [참고항목 (References)](#참고-항목들)


## 스프링 어플리케이션
> 스프링 부트 어플리케이션의 실행 시에 어떤 순서대로 실행되는 지에 대해 이해하고 구현해야만 합니다

### 스프링 어플리케이션 기동 시에 ApplicationContext 보다 먼저 혹은 이후에 실행하고싶은 경우
* 로깅은 기본은 INFO 이지만 VM Option -Ddebug 혹은 프로그램 아규먼트로 --debug 
* ApplicationContext 보다 이후에 실행되어야 하는 Bean 은 ApplicaitonListener<ApplicationStartedEvent> 를 구현해서 적용할 수 있습니다 (이 때에는 Bean으로 등록)
* ApplicationContext 보다 먼저 실행되는 Bean 들에 대한 트리거 작업은 <ApplicationStartingEvent>를 구현해도 수행되지 않으므로 별도로 addListener 통해 등록이 필요하다
* WebApplicationType { Servlet (default), Reactive(Webflux, not servlet), None }
* Spring Application 의 프로그램 내에서는 JVM 옵션인 -Dfoo 정보는 받아올 수 없고  Program Args 인 --bar 정보만 활용할 수 있다

## 외부설정
> application.properties 파일 혹은 application.yml 파일에 설정을 통해 주입되는 값들의 우선순위에 대한 설정

### 프로퍼티 우선 순위
* 유저 홈 디렉토리에 있는 spring-boot-dev-tools.properties
* 테스트에 있는 @TestPropertySource - 컴파일 시에 src/main 설정을 src/test 가 덮어쓴다.
  - 경우에 따라 일부 속성값이 src/main 에만 존재하는 경우 Test 시에 해당 application.properties 
  - 즉, application.properties 값이 덮어써져서 Injection 이 실패하므로 test 쪽에도 넣어주어야만 한다
  - 이러한 상황을 회피하는 방법은 @SpringBootTest(properties="psyoblade.age=${random.int}) 를 쓸 수 있다
  - 또 다른 회피 방안은 아예 test/resources/application.properties 파일을 지우고 src/main 을 쓰게하는 방법이 있다 
  - 추천 방식은 라이브에만 application.properties 사용하고 @SprintBootTest(properties= {"k1=v1", "k2=v2"}) 이다
  - 너무 많은 프로퍼티 값들이 있다면 test.properties 파일을 만들고 @TestPropertySource 어노테이션을 씁니다
  - 이 경우에는 (locations = "classpath:/test.properties") 에 지정된 파일에 있는 k:v 값만 override 됩니다
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
* 타입에 안전한 프로퍼티 @ConfigurationProperties
  - 별도의 파일의 convention 규칙에 따라 적용하는 것이 애매한 경우 class 로 구성할 수 있으므로 type-safe 하다
  - bean 으로 등록하여 다른 빈에 주입할 수 있다 @me.suhyuk.springcore.config.Externalproperties
* 융통성 있는 바인딩
  - context-path, context_path, contextPath 모두 동일하게 인식
* 프로퍼티 타입 컨버전 
  - @DurationUnit(DurationUnit.SECONDS) 설정하는 경우 25, 25s 등으로 설정해도 인식가능
* 프로퍼티 값 검증
  - @Validated 설정을 통해서 JSR-303 (ex_ @NotNull, @NotEmpty, @Range 등) 적용이 가능

## 프로파일
> 특정 환경에서만 동작하게끔 하는 스프링의 기능 @Profile("prod") 인 경우는 프로파일이 "prod"인 경우만 활성화 됩니다

### 기본 프로파일과 prod, test 설정을 같이 유지하고 싶은 경우
* application.yml 파일에 spring.profiles.active 설정을 유지해야 합니다
* 개별 application-${type}.yml 파일을 생성하고 각 type 별 Profile 설정된 Configuration 클래스를 생성합니다
* application.yml 파일에 spring.profiles.include 형식으로 포함시킬 수도 있습니다
* `Profile("test")` 와 같은 `Configuration`이 있는 경우 애플리케이션 로딩이 실패할 수 있다는 점에 유의
  * 예를 들어 prod, test 2개의 프로파일이 명시된 프로젝트라면 둘 중 하나를 반드시 ActiveProfiles 설정을 해야만 한다
  * 또한 test 프로파일이 있는 경우에는 해당 값을 읽어들이지만, 없다면 이전 마지막 프로파일을 읽어들임에 주의해야 한다

## 로깅설정
> 스프링에서 어떻게 로깅이 수행되고, 활용하는 지에 대해 이해합니다.
> 스프링은 slf4j 등의 파사드 패턴을 활용하여 다양한 JUL, Log4j, Logback 등의 로거를 활용할 수 있도록 한다

### [스프링5 로거 변경사항](https://docs.spring.io/spring/docs/5.0.0.RC3/spring-framework-reference/overview.html#overview-logging)
* 스프링5 에서의 로깅은 commons-logging -> slf4j -> logback 을 사용합니다
  - 스프링 코어와 내부 상황에 따라 복잡하지만 JUL, log4j 등도 결국 logback 로거를 사용합니다
  - 참고로 SLF4J 는 Simple Logging Facade for Java 의 약어입니다
* [로깅 옵션](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/html/boot-features-logging.html)

| 옵션| 설명 | 기타|
| ------------ | ----------- | ------------------- |
| --debug | 내부 컨테이너, Hibernate 및 Spring 에 대한 것만 디버깅으로 출력 | - |
| --trace | 모든 로그를 디버깅으로 출력 | - |
| spring.output.ansi.enabled | 컬러 출력 | always, detect, never |
| logging.file | 로깅 파일 출력 | - |
| logging.path | 로깅 디렉토리 출력 | - |
| logging.level.{package} | *특정 패키지에 대한 디버깅 설정* | ...me.suhyuk.springcore.services=debug |
| ${ENV_VARS} | 환경변수 값을 활용하여 변경 | ... level="${CUSTOM_LEVEL}"/> |

* [별도의 로그백 설정파일](https://docs.spring.io/spring-boot/docs/2.1.8.RELEASE/reference/html/howto-logging.html#howto-configure-logback-for-logging)
  - 이름을 logback-spring.xml 로 명시해야 로그백 파일 안에서 프로파일 혹은 환경변수를 사용할 수 있다
  - logback.xml 파일을 사용하는 경우, 너무 일찍 로딩이 되기 때문입니다
  
* 로그 종속성 설정 (기본 logging slf4j)
```xml
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-logging</artifactId>
</dependency>
```

## 테스트
> 우선 spring-boot-starter-test 참조하고, @SpringBootTest, @RunWith(SpringRunner.class) 추가합니다.
> 무엇보다도 SpringBootTest(webEnvironment = "") 설정에 따라 서버를 띄울지 여부를 판단합니다

### 스프링 부트 테스트의 시작은 pom.xml 수정부터
* artifactId "spring-boot-starter-test" 추가하면 됩니다
* 그리고 기본 @SpringBootTest 설정이 MOCK 인데, 스프링 컨테이너가 목킹한 DispatcherServlet MockUp 이 뜨게 된다
* 즉, 즉 내장 톰캣이 뜨지 않으며, MockMVC 를 사용해야만 하는데 이 때에 @AutoConfigureMockMvc 설정이 필요합니다
* SpringBootApplication 을 찾아서 필요한 컴포넌트를 목킹하여 테스트 할 수 있는 환경을 제공해 준다

### 스프링부트 테스트가 너무 무겁다면 슬라이싱 테스트를 추천
* JsonTest 통한 실험

## 참고 항목들
* 잘 모르고 있던 Autowired 변수의 특징
  - 해당 어노테이션이 붙어있는 String 문자열이 하나 있다면 ComponentScan 된 것 중에 하나만 반환값이 String Bean 이 주입된다
* pom.xml 파일의 경우 버전은 parent 에서 관리됩니다
* 파라메터를 통해서 프로파일을 변경하고자 하는 경우
  - ```bash
    java -jar target/springcore-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
    ```
* 실행 시에 참고할 만한 사항들
```bash
# 테스트를 제외하고 패키지 생성
mvn clean package -DskipTests

# application.properties 대신 아규먼트로 파라메터 전달하기
java -jar target/springcore-0.0.1-SNAPHOST.jar --psyoblade.name=suhyuk.park

# resources/application.properties 설정은 굳이 넣어 주어야만 한다
# Project-Module 설정에서 반드시 Test Resources 설정 값을 추가해 주어야만 한다.
psyoblad.name = park suhyuk

# random.int 함수를 통해 임의의 랜덤 값을 얻을 수 있다
psyoblade.age = ${random.int}

# placeHolder : 앞에서 사용한 변수는 아래에서 다시 사용할 수 있다
psyoblade.fullName = The great wizard ${psyoblade.name}

# 서버 포트는 위의 랜덤함수를 사용해서는 안되며 아래와 같이 0으로 설정해야 합니다
server.port = 0 # 항상 가용한 랜덤 포트를 설정합니다

```
* application.yml 자동완성 Annotation Processors 설정에서 Enable annotation processing 키고 Rebuild
  - 안타깝지만 application.properties 에서는 Ultimate Edition 에서만 동작한다
* [마크다운 사용법](https://gist.github.com/ihoneymon/652be052a0727ad59601)

