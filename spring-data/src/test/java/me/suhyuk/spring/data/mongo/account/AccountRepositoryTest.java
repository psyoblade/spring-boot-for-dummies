package me.suhyuk.spring.data.mongo.account;

import me.suhyuk.spring.data.mongo.Account;
import me.suhyuk.spring.data.mongo.AccountRepository;
import me.suhyuk.spring.data.mongo.SpringDataMongoApplicationTest;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(classes = SpringDataMongoApplicationTest.class)
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void assertion() {
        assert (true);
    }

    @Test
    public void findByEmail() {
        Optional<Account> beforeInsert = accountRepository.findByUsername("psyoblade@me.com");
        assertThat(beforeInsert).isEmpty();

        Account account = newAccount();
        Account savedAccount = accountRepository.save(account);
        System.out.println(savedAccount.toString());

        Optional<Account> findById = accountRepository.findById(savedAccount.getId());
        assertThat(findById).isNotNull();

        Optional<Account> afterInsert = accountRepository.findByEmail(account.getEmail());
        System.out.println("삽입 후에 확인 : " + afterInsert.toString());
        assertThat(afterInsert).isNotEmpty();
        assertThat(afterInsert.get().getUsername()).isEqualTo("suhyuk");
    }

    private Account newAccount() {
        Account account = new Account();
        account.setEmail("psyoblade@me.com");
        account.setUsername("suhyuk");
        return account;
    }

}