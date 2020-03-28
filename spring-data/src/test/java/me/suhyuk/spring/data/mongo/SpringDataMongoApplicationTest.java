package me.suhyuk.spring.data.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

@SpringBootApplication
@ActiveProfiles("test")
@ComponentScan(excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, value = { SpringDataMongoApplication.class}))
public class SpringDataMongoApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongoApplicationTest.class, args);
		System.out.println("##### TEST ##### : 스프링 어플리케이션 기동");
	}

}
