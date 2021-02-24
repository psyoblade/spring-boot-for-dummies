# 스프링 부트를 이용한 게시판 만들기

## 시작 프로젝트 생성
* gradle init 통해 프로젝트 생성
* build.gradle 파일 구성 (책 내용 참고)
* resources/mappers/create.sql 생성
* mysql docker 데몬 기동 seoul_popular_trip
* [Accessing data w/ MySQL](https://spring.io/guides/gs/accessing-data-mysql/)
* [Initialize Database](https://docs.spring.io/spring-boot/docs/2.1.18.RELEASE/reference/html/howto-database-initialization.html)

## 1. 기본 명령어 이해
> [@RequestBody, @ResponseBody](https://www.baeldung.com/spring-request-response-body)는 Body 정보를 주고 받을 때 사용하며, 
[@PostMapping](https://www.baeldung.com/spring-new-requestmapping-shortcuts)는 @RequestMapping + RequestMethod.POST 이다

### 1-1. RequestBody 어노테이션을 통해 Json 객체를 Java 객체로 변환할 수 있습니다
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
### 1-2. ResponseBody 어노테이션을 통해 Java Object 를 Json 메시지로 변환합니다
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

### 1-3. PostMapping 어노테이션 사용 시에 출력 미디어타입(JSON, XML 등)을 지정할 수 있습니다
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
* 클라이언트가 서버에 전달받고 싶은 포맷을 Accept 헤더를 통해 전달할 수 있습니다
```bash
bash> curl -i \
-H "Accept: application/xml" \
-H "Content-Type: application/json" \
-X POST --data '{"username": "psyoblade", "password": "password"}' \

"https://localhost:8080/.../requset"
```
* 단 스프링 서버에서는 xml 생성을 위해 필요한 라이브러리를 추가해야만 합니다
```yaml
    implementation group: 'com.fasterxml.jackson.dataformat', name: 'jackson-dataformat-xml', version: '2.12.1'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-core', version: '2.12.1'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.12.1'
    implementation group: 'com.fasterxml.jackson.core', name: 'jackson-annotations', version: '2.12.1'
```

### 1-4. 클라이언트 요청한 MediaType 미 지원시에 예외처리를 해주어야 정확한 오류 메시지를 전달합니다
* 예외처리가 되지 않으면 HttpMediaTypeNotAcceptableException 에러 메시지를 406 오류와 함께 뱉어냅니다
* [The HttpMediaTypeNotAcceptableException in Spring MVC](https://www.baeldung.com/spring-httpmediatypenotacceptable)
```java
@ResponseBody
@ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
public String handleHttpMediaTypeNotAcceptableException() {
    return "acceptable MIME type:" + MediaType.APPLICATION_JSON_VALUE;
}
```

## Q&A
* SpringBootServletInitializer 는 왜 상속 받아 사용해야 하는가?
  - application.sources(BoardApplication.class) 코드는 뭘 의미하는가?


