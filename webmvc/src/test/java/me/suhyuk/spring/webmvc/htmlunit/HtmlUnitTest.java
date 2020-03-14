package me.suhyuk.spring.webmvc.htmlunit;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import me.suhyuk.spring.webmvc.thymeleaf.ThymeleafController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest(ThymeleafController.class)
public class HtmlUnitTest {

    @Autowired
    WebClient webClient;

    @Test
    public void 웹클라이언트_예제() throws IOException {
        HtmlPage page = webClient.getPage("/c_psyoblade");
        HtmlHeading1 h1 = page.getFirstByXPath("//h1");
        assertThat(h1.getTextContent()).isEqualToIgnoringCase("psyoblade");

    }

}
