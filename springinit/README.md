# 인프런 스프링 부트 개념과 활용

## 3부  스프링부트 원리

### 독립적으로 실행 가능한 JAR
* 과거에는 Java 가 Jar 파일을 읽을 방법이 마땅치 않아 Uber 라이브러리를 통해 모두 하나의 Jar 로 만드는 방법을 취했음
* 기본적으로 자바에는 내장 JAR를 로딩하는 표준적인 방법이 없다고 함
* 현재 스프링은 org.springframework.boot.loader.jar.JarFile 클래스에서 모든 jar 를 별도로 로딩할 수 있도록 자동으로 구성돈다
* unzip -q springinit.jar 통해 풀어볼 수 있다 
* 스프링 어플리케이션의 경우 JarLauncher 클래스를 통해 실행됩니다 
* MANIFEST.MF 파일에 StartClass 및 MainClass 정보를 확인할 수 있습니다
* 결국 jar 하나로 어플리케이션이 기동될 수 있는 것이 이러한 구조 덕분입니다
* Jar (MANIFEST) -> MainClass (Launcher) -> JarFile (Loader) -> StartClass (MyClass)

### 스프링 부트 원리
#### 기본 원리 
* 스프링 부트의 가장 기본은 spring-boot-starter-web 을 통해 이루어진다
* 이러한 종속성에 대한 관리를 받는 방법은 sprint-boot-starter-parent 입니다
* 이 parent 를 따라가면 올라가면 spring-boot-dependencies 내에서 모든 패키지의 버전을 관리합니다
#### 스프링부트 빈 등록
* 빈 등록은 두 단계를 나누어 등록되는데
* 첫 번째가 ComponentScan 을 통해 등록되는 단계
* 두 번째가 등록된 정보를 바탕으로 jar 파일에 등록된 spring.factories AutoConfiguration 을 통해 등록되는 단계
* 또한 이 단계에서 ConditionalOn 등의 조건을 통해 제어됩니다
* 결국 개발자가 재정의하거나 신경써야 할 부분을 알아서 다 등록 로딩해 준다
#### 독립적인 JAR 
* JarFile, JarLauncher 및 내장 웹서버 등을 통해서 독립적인 스프링 어플리케이션을 개발할 수 있다
* SpringBoot 는 다양한 웹서버(tomcat, undertow, jetty) 등을 이용하여 등록하는 프레임워크입니다

