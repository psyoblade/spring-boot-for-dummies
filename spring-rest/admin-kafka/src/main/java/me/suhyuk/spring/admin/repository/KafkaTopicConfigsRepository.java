package me.suhyuk.spring.admin.repository;

import me.suhyuk.spring.admin.common.Pair;
import me.suhyuk.spring.admin.domain.KafkaTopicConfigs;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 토픽 상세 정보는
 */
@Repository
public class KafkaTopicConfigsRepository implements CrudRepository<KafkaTopicConfigs, Pair<String, String>> {

    @Override
    public <S extends KafkaTopicConfigs> S save(S entity) {
        return null;
    }

    @Override
    public <S extends KafkaTopicConfigs> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<KafkaTopicConfigs> findById(Pair<String, String> stringStringPair) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Pair<String, String> stringStringPair) {
        return false;
    }

    @Override
    public Iterable<KafkaTopicConfigs> findAll() {
        return null;
    }

    @Override
    public Iterable<KafkaTopicConfigs> findAllById(Iterable<Pair<String, String>> pairs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Pair<String, String> stringStringPair) {

    }

    @Override
    public void delete(KafkaTopicConfigs entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends KafkaTopicConfigs> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
