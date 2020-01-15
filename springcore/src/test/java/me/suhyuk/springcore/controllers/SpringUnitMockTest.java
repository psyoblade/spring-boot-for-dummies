package me.suhyuk.springcore.controllers;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
// 기본이 MOCK 인데, 스프링 컨테이너가 목킹한 DispatcherServlet MockUp 이 뜨게 된다. 즉 내장 톰캣이 뜨지 않는다
// 해서 MockMVC 를 사용해야만 하는데 이 때에 AutoConfigureMockMvc 설정이 필요합니다
@AutoConfigureMockMvc
public class SpringUnitMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHello() throws Exception {
        // 아래와 같이 MVC 테스트를 편하게 할 수 있고 andDo 출력 내용을 assertion 할 수 있다
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("suhyuk"))
                .andDo(print());
    }
}
