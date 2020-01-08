# 인프런 스프링 부트 개념과 활용

## 4부 스프링 부트 활용

### 스프링부트 핵심 기능

#### SpringApplication
* 로깅은 기본은 INFO 이지만 VM Option -Ddebug 혹은 프로그램 아규먼트로 --debug 
* ApplicationContext 보다 이후에 실행되어야 하는 Bean 은 ApplicaitonListener<ApplicationStartedEvent> 를 구현해서 적용할 수 있습니다 (이 때에는 Bean으로 등록)
* ApplicationContext 보다 먼저 실행되는 Bean 들에 대한 트리거 작업은 <ApplicationStartingEvent>를 구현해도 수행되지 않으므로 별도로 addListener 통해 등록이 필요하다
* WebApplicationType { Servlet (default), Reactive(Webflux, not servlet), None }
* Spring Application 의 프로그램 내에서는 JVM 옵션인 -Dfoo 정보는 받아올 수 없고  Program Args 인 --bar 정보만 활용할 수 있다

* 외부 설정
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

