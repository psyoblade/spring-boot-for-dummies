package me.suhyuk.springcore;

import me.suhyuk.springcore.listener.BeforeApplicationContextListener;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

@SpringBootApplication
// @EnableConfigurationProperties(ExternalProperties.class) // 여기에 등록하면 2개 등록하려고 하는 바람에 오류가 난다
public class SpringCoreApplication {

    // 3rd Party Configuration: 이미 server.port 정보는 어딘가에 존재하는 클래스인 경우 @Component 붙일 수 없다
	@Bean
	public ServerProperties serverProperties() {
		return new ServerProperties();
	}

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(SpringCoreApplication.class);
	    app.addListeners(new BeforeApplicationContextListener());
	    app.setWebApplicationType(WebApplicationType.NONE);
	    app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
