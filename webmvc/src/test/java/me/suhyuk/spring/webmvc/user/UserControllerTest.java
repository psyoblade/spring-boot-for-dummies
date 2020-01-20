package me.suhyuk.spring.webmvc.user;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
// 우리는 slicing 기능 측, 웹만 사용할 것이므로
@WebMvcTest(UserController.class)
public class UserControllerTest {

    // HTTP 서버를 띄우지 않고 목업을 통해서 웹 액세스를 하는 것 처럼 동작하게 합니다
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("hello"));
    }

    /**
     * 클라이언트가 어떤 뷰를 원하는지 알아내는 (Resolver) 방법은 Accept header 정보입니다
     * @throws Exception
     */
    @Test
    public void testCreateUser() throws Exception {
        String userJson = "{\"username\":\"suhyuk\", \"password\":\"park\"}";
        mockMvc.perform(
                post("/users/create")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .content(userJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(equalTo("suhyuk"))))
                .andExpect(jsonPath("$.password", is(equalTo("park"))));
    }

    /**
     * Contents Negotiation Resolver 가 있으므로 요청은 Json 으로 보내고 응답은 XML 로 보내는 것을 테스트
     * 1차 테스트 실패 하는데 해당 HttpMessageConvertersAutoConfiguration 에서 사용하는 XmlConverter 가 없기 때문입니다
     * jackson-dataformat-xml 를 pom.xml 에 추가하여 해결합니다
     * @throws Exception
     */
    @Test
    public void testSendJsonReceiveXML() throws Exception {
        String userJson = "{\"username\":\"suhyuk\", \"password\":\"park\"}";
        mockMvc.perform(
                post("/users/create")
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .accept(MediaType.APPLICATION_XML)
                    .content(userJson))
                .andExpect(status().isOk())
                .andExpect(xpath("/User/username").string("suhyuk"))
                .andExpect(xpath("/User/password").string("park"));
    }

}

