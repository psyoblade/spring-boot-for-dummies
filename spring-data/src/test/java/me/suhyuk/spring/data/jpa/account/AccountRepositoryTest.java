package me.suhyuk.spring.data.jpa.account;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest // slicing test 를 위해 repository 와 관련된 bean 만 등록해서 테스트 코드를 만드는 것
public class AccountRepositoryTest {

    private Logger logger = LoggerFactory.getLogger(AccountRepositoryTest.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AccountRepository accountRepository;

    @Test
    public void 뜰까() {
        assert(true);
    }

    @Test
    public void 테스트_커넥션_비교() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metadata = connection.getMetaData();
            logger.info("{}, {}, {}", metadata.getURL(), metadata.getDriverName(), metadata.getUserName());
        }
    }

    @Test
    public void 계정생성_및_확인() {
        Account account = new Account();
        account.setUsername("박수혁");
        account.setPassword("패숴드");
        Account newAccount = accountRepository.save(account);
        assertThat(newAccount).isNotNull();

        Account existingAccount = accountRepository.findByUsername("박수혁");
        assertThat(existingAccount).isNotNull();

        Optional<Account> optional = accountRepository.findById(1L);
        assertThat(optional).isPresent();

        Optional<Account> accountByPass = accountRepository.findByPassword("패숴드");
        assertThat(accountByPass).isPresent();
    }

}