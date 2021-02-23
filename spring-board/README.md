# 스프링 부트를 이용한 게시판 만들기

## 시작 프로젝트 생성
* gradle init 통해 프로젝트 생성
* build.gradle 파일 구성 (책 내용 참고)
* resources/mappers/create.sql 생성
* mysql docker 데몬 기동 seoul_popular_trip
* [Accessing data w/ MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Initialize Database](https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/howto-database-initialization.html)

## 기본 명령어 이해
> 스프링 [@RequestBody, @ResponseBody](https://www.baeldung.com/spring-request-response-body) 페이지를 참고합니다

* @RequestBody 어노테이션은 HttpRequest body 를 Java Object 로 Deserialization 합니다
```java
@PostMapping("/request")
public ResponseEntity postController(@RequestBody LoginForm loginForm) {
    exampleService.fakeAuthenticate(loginForm);
    return ResponseEntity.ok(HttpStatus.OK);
}
```
* 스프링은 어노테이션을 추가하는 것만으로, JSON 을 Java type 으로 변환하여 전달합니다.
```bash
bash> curl -i \
-H "Accept: application/json" \
-H "Content-Type: application/json" \
-X POST --data '{"username": "psyoblade", "password": "password"}' \
"https://localhost:8080/.../requset"
```

* @ResponseBody 어노테이션은 반대로 Java Object 를 JSON 으로 Serialize 하여 HttpResponse 객체로 반환합니다
```java
public class ResponseTransfer {
    private String text;
    // standard getters/setters
}

@Controller
@RequestMapping("/post")
public class ExamplePostController {
    @Autowired
    ExampleService exampleService;

    @PostMapping
    @ResponseBody
    public ResponseTransfer postResponseController(@RquestBody LoginForm loginForm) {
        return new ResponseTransfer("Thanks");
    }    
}
```

* ResponseBody 어노테이션 사용 시에 Content Type 지정을 할 수도 있습니다
```java
@PostMapping(value = "/content", produces = MediaType.APPLICATION_XML_VALUE)
@ResponseBody
public ResponseTransfer postResponseXmlContent(@RequsetBody LoginForm loginForm) {
    return new ResponseTransfer("XML Content");
}

@PostMapping(value = "/content", produces = MediaType.APPLICATION_JSON_VALUE)
@ResponseBody
public ResponseTransfer postResponseJsonContent(@RequsetBody LoginForm loginForm) {
    return new ResponseTransfer("JSON Content");
}
```
* XML 로 반환하고 싶다면 Accept 에서 xml 로 지정합니다
```bash
bash> curl -i \
-H "Accept: application/xml" \
-H "Content-Type: application/json" \
-X POST --data '{"username": "psyoblade", "password": "password"}' \

"https://localhost:8080/.../requset"
```

## Q&A
* SpringBootServletInitializer 는 왜 상속 받아 사용해야 하는가?
  - application.sources(BoardApplication.class) 코드는 뭘 의미하는가?


