package me.suhyuk.spring.data.mongo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Optional;

@SpringBootApplication
public class SpringDataMongoApplication {

	@Autowired
	private MongoTemplate mongoTemplate;

	@Autowired
	private AccountRepository accountRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringDataMongoApplication.class, args);
	}

	@Bean
	public ApplicationRunner applicationRunner() {
		return args -> {
		    Account account = insertAccountObject();
		    readAccountUsingRepository(account.getId());
		};
	}

	private void readAccountUsingRepository(String id) {
		Optional<Account> foundAccount = accountRepository.findById(id);
		System.out.println("##### LIVE ##### : 스프링 어플리케이션 기동" + foundAccount.toString());
	}

	private Account insertAccountObject() {
		Account account = new Account();
		account.setUsername("suhyuk");
		account.setEmail("psyoblade@me.com");
		return mongoTemplate.insert(account);
	}

}
