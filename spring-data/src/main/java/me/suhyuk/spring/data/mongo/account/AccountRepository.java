package me.suhyuk.spring.data.mongo.account;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AccountRepository extends MongoRepository<Account, String> {

    Optional<Account> findByEmail(String email);
    Optional<Account> findByUsername(String username);

}
