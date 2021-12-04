package me.suhyuk.spring.admin.dao;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

@Getter
@Setter(AccessLevel.PROTECTED)
public class KafkaTopic {
    private final String clusterName;
    private final String topicName;
    private final Boolean isInternal;
    public KafkaTopic(String clusterName, String topicName, Boolean isInternal) {
        this.clusterName = clusterName;
        this.topicName = topicName;
        this.isInternal = isInternal;
    }
    @SneakyThrows(JsonProcessingException.class)
    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    }
}
