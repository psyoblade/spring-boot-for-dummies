package me.suhyuk.spring.data.mongo.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataMongoTest
public class AccountRepositoryTest {

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void findByEmail() {
        Account account = newAccount();
        accountRepository.save(account);

        Optional<Account> findById = accountRepository.findById(account.getId());
        assertThat(findById).isNotNull();

//        System.out.println(findById.toString());
//        Optional<Account> findByEmail = accountRepository.findByUsername(account.getUsername());
//        assertThat(findByEmail).isNotEmpty();
//        assertThat(findByEmail.get().getUsername()).isEqualTo("suhyuk");
    }

    private Account newAccount() {
        Account account = new Account();
        account.setEmail("psyoblade@me.com");
        account.setUsername("suhyuk");
        return account;
    }

}