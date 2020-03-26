package me.suhyuk.spring.data.jpa.account;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    @Query(nativeQuery = true, value = "select * from account where password = ?1")
    Optional<Account> findByPassword(String password);
}
