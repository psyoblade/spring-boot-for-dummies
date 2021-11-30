package me.suhyuk.spring.admin.repository;

import me.suhyuk.spring.admin.common.Pair;
import me.suhyuk.spring.admin.domain.*;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * KafkaTopic 정보는 Create, Get, Delete 만 지원합니다
 */
@Repository
public class KafkaTopicInfoRepository implements IKafkaRepository<KafkaTopicInfo> {

    @Override
    public <S extends KafkaTopic> S save(S entity) {
        return null;
    }

    @Override
    public <S extends KafkaTopic> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<KafkaTopic> findById(Pair<String, String> stringStringPair) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Pair<String, String> stringStringPair) {
        return false;
    }

    @Override
    public Iterable<KafkaTopic> findAll() {
        return null;
    }

    @Override
    public Iterable<KafkaTopic> findAllById(Iterable<Pair<String, String>> pairs) {
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
    public void delete(KafkaTopic entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends KafkaTopic> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
