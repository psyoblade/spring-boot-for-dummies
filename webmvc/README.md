# 스프링 웹 MVC
> 웹 MVC 란 무엇이고, 어떻게 활용하는지 이해합니다

## 목차
### [1. 스프링 웹 MVC 소개](#1-스프링-웹-MVC-소개)
### [2. HttpMessageConverters](#2-HttpMessageConverters)
### [3. ViewResolve](#3-ViewResolve)
### [4. 정적 리소스](#4-정적-리소스)
### [5. 웹 JAR](5-웹-JAR)
### [6. index-페이지와-파비콘](6-index-페이지와-파비콘)
### [7. Thymeleaf](7-Thymeleaf)
### [8. HtmlUnit](8-HtmlUnit)
### [9. ExceptionHandler](9-ExceptionHandler)
### [10. Spring HATEOAS](10-Spring-HATEOAS)
### [11. CORS](11-CORS)

## 1. 스프링 웹 MVC 소개
> 스프링 부트가 아니라 스프링 5.0에서 제공하는 MVC 라는 점을 이해합니다.
> 기본적으로 스프링부트에서 제공되는 많은 기능들은 스프링에서 제공하고 있고, 이를 좀 더 편하게 사용할 수 있도록 구성되어 있습니다

* WebMvcAutoConfiguration 설정에서 자동으로 기동되기 때문에 편하게 스프링에서 REST Controller 를 사용할 수 있습니다
* 이 과정에서 다양한 Filter 가 적용이 되며 Http{Put, Patch, Form}FormContentFilter 를 통해서 다양한 Controller RequestMapping 정의가 쉬워진다
* 컨트롤러에서 GetMapping -> RequestMapping 과 같이 다양한 Request 에 대한 맵핑을 Filter 수준에서 이미 해주기 때문에 부트가 쉬워진다

### 2. HttpMessageConverters
> Request → Resolver → Filter → Converter → 비즈니스 로직 처리 → Converter → Response 과정을 통해서 요청부터 응답까지 수행됩니다
> 이 과정에서 클라이언트 요청을 내부 객체로 변환 혹은 변환된 객체를 반환하는 과정의 변환기 역할

### 3. ViewResolve
> 클라이언트의 요청이 어떤 요청인지에 따라 어느 컨트롤러 혹은 역할에 전달할 것인지를 결정하는 역할

### 4. 정적 리소스
> 클라이언트의 요청에 동적으로 생성되지 않고 초기화되어 있어 생성된 객체 혹은 리소스의 값을 그대로 전달하는 경우를 말합니다

#### ResourceHttpRequestHandler
* 기본적으로 resources/static 경로에 생성된 모든 HTML 파일 들은 WebMvcConfigurer 의 addResourceHandlers 를 통해서 정적 리소스로 할당됩니다
* 이를 관장하는 녀석이 ResourceHttpRequestHandler 인데 Response 헤더에 있는 Last-Modified 정보를 통해서 Request 시에 [If-Modified-Since](https://itstory.tk/entry/Spring-MVC-LastModified-IfModifiedSince-%EC%BA%90%EC%8B%9C-%EC%84%A4%EC%A0%95) 정보를 통해서 해당 시점 이후에 수정되지 않았다면 304 응답을 보내고 캐싱된 정보를 사용합니다.

### 5. 웹 JAR
> jquery, bootstrap 등을 jar 파일로 dependency 를 추가, template 을 사용하여 jss 등을 사용할 수 있습니다. 또한 webjars-locator 를 이용하면 아래와 같이 버전을 명시하지 않아도 됩니다
```html
<!--script src="/webjars/jquery/3.3.1/jquery.min.js"></script-->
<script src="/webjars/jquery/jquery.min.js"></script>
<script>
    $(function() {
        alert("ready2!");
    })
</script>
```

### 6. index 페이지와 파비콘

### 7. Thymeleaf
> 뷰를 유연하게 제공하기 위해 활용하는 템플릿 엔진

### 8. HtmlUnit
> 좀 더 상세한 Http 테스트를 dom 객체 액세스 수준으로 할 수 있게하는 라이브러리 

### 9. ExceptionHandler
> 클래스 혹은 글로벌 수준에서 Http 상태에 따른 예외 처리를 가능하게 하는 기능

### 10. Spring HATEOAS
> 요청 데이터에 대한 연관 링크 정보를 같이 뭍혀서 보내는 기능

### 11. CORS
> Cross Origin Resource Sharing 의 약자로 SOP (Single Origin Policy)의 제약을 해결하기 위한 기술, 즉 다른 Origin (scheme:host:port) 으로는 전송할 수 없으나 이를 제한적으로 풀어주는 기능 
