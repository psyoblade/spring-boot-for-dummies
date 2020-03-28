package me.suhyuk.spring.data.redis;

import me.suhyuk.spring.data.redis.account.Account;
import me.suhyuk.spring.data.redis.account.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class RedisRunner implements ApplicationRunner {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        insertRedisKeyValues();
        createAccountObject();
    }

    private void insertRedisKeyValues() {
        ValueOperations<String, String> values = stringRedisTemplate.opsForValue();
        values.set("id", "psyoblade");
        values.set("name", "suhyuk");
    }

    private void createAccountObject() {
        Account account = new Account();
        account.setUsername("suhyuk");
        account.setEmail("psyoblade@suhyuk.me");

        Account foo = new Account();
        foo.setId("foo");
        foo.setUsername("bar");
        foo.setEmail("foo.com");

        Account savedAccount = accountRepository.save(account);
        Optional<Account> foundAccount = accountRepository.findById(savedAccount.getId());
        System.out.println(foundAccount.orElse(foo).getUsername());
        System.out.println(foundAccount.orElse(foo).getEmail());

    }
}
