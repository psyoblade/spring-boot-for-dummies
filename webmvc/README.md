# 스프링 웹 MVC
> 웹 MVC 란 무엇이고, 어떻게 활용하는지 이해합니다

## 목차
### [1. 스프링 웹 MVC 소개](#스프링-웹-MVC)
### [2. HttpMessageConverters](#스프링_웹_MVC)
### 3. ViewResolve
### 4. 정적 리소스
### 5. 웹 JAR
### 6. index 페이지와 파비콘
### 7. Thymeleaf
### 8. HtmlUnit
### 9. ExceptionHandler
### 10. Spring HATEOAS
### 11. CORS

## 스프링 웹 MVC
> 스프링 부트가 아니라 스프링 5.0에서 제공하는 MVC 라는 점을 이해합니다.
> 기본적으로 스프링부트에서 제공되는 많은 기능들은 스프링에서 제공하고 있고, 이를 좀 더 편하게 사용할 수 있도록 구성되어 있습니다

* WebMvcAutoConfiguration 설정에서 자동으로 기동되기 때문에 편하게 스프링에서 REST Controller 를 사용할 수 있습니다
* 이 과정에서 다양한 Filter 가 적용이 되며 Http{Put, Patch, Form}FormContentFilter 를 통해서 다양한 Controller RequestMapping 정의가 쉬워진다
* 컨트롤러에서 GetMapping -> RequestMapping 과 같이 다양한 Request 에 대한 맵핑을 Filter 수준에서 이미 해주기 때문에 부트가 쉬워진다

