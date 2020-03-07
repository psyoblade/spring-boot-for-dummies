package me.suhyuk.spring.webmvc;

import me.suhyuk.spring.prototype.SpringPrototypeApplicationTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest
public class SpringWebmvcApplicationTests {

	@Test
	public void contextLoads() {
	    assert(true);
	}

}
