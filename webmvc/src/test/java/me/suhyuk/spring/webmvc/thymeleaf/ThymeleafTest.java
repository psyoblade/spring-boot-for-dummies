package me.suhyuk.spring.webmvc.thymeleaf;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(ThymeleafController.class)
public class ThymeleafTest {

    @Autowired
    MockMvc mockMvc;

    // thymeleaf 의존성이 추가되지 않았다면 요청 Get URI 는 단순 URI 로 인식되지만 의존성 추가 후에는 view 의 template 를 찾는다
    @Test
    public void 타임리프_웹페이지_접근() throws Exception {
        mockMvc.perform(get("/c_psyoblade"))
                .andExpect(status().isOk())
                .andDo(print()) // thymeleaf 를 사용하기 때문에 서블릿 엔진이 아니라 타임리프카 만들어 주기 때문에 출력이 가능하다
                .andExpect(view().name("v_psyoblade")) // <-- 이것이 템플릿의 이름 View
                .andExpect(model().attribute("name", is("psyoblade")))
                .andExpect(content().string(containsString("Title")));
    }

}
