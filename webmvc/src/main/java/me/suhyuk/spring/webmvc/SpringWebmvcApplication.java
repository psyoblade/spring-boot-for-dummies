package me.suhyuk.spring.webmvc;

import me.suhyuk.spring.webmvc.config.WebConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringWebmvcApplication implements CommandLineRunner {

	@Autowired
	private WebConfig appConfig;

	public static void main(String[] args) {
		SpringApplication.run(SpringWebmvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("hello spring world");
		System.out.println(appConfig.getClass().toGenericString());
	}
}
