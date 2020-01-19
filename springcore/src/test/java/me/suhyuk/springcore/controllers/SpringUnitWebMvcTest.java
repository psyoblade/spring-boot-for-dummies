package me.suhyuk.springcore.controllers;

import me.suhyuk.springcore.entities.SpringUnit;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static reactor.core.publisher.Mono.when;

// TODO SpringBootTest 대신 WebMvcTest 만을 추가하면 HTTP 서버를 띄우지 않고 MVC Controllers 테스트가 가능합니다
@RunWith(SpringRunner.class)
@WebMvcTest
public class SpringUnitWebMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SpringUnit springUnit;

    @Test
    public void testMockBean() throws Exception {
        given(springUnit.getName())
                .willReturn("park.suhyuk");
        mockMvc.perform(get("/hello").accept(MediaType.TEXT_PLAIN))
                .andExpect(status().isOk())
                .andExpect(content().string("park.suhyuk"));
    }

}
