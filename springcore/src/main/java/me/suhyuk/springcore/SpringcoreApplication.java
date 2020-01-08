package me.suhyuk.springcore;

import me.suhyuk.springcore.listener.BeforeApplicationContextListener;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringcoreApplication {

	public static void main(String[] args) {
	    SpringApplication app = new SpringApplication(SpringcoreApplication.class);
	    app.addListeners(new BeforeApplicationContextListener());
	    app.setWebApplicationType(WebApplicationType.NONE);
	    app.setBannerMode(Banner.Mode.OFF);
		app.run(args);
	}

}
