package me.suhyuk.spring.admin.configuration;

import lombok.Getter;
import lombok.Setter;
import org.apache.kafka.clients.CommonClientConfigs;
import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.util.*;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@EnableConfigurationProperties
@Getter
@Setter
public class KafkaConfiguration {

    private List<KafkaCluster> clusters;

    public static class AdminClients {
        Map<KafkaCluster, AdminClient> adminClients;
        public AdminClients(Map<KafkaCluster, AdminClient> adminClients) {
            this.adminClients = adminClients;
        }
        public AdminClient of(String clusterName) {
            KafkaCluster kafkaCluster = new KafkaCluster();
            kafkaCluster.setClusterName(clusterName);
            return adminClients.get(kafkaCluster);
        }
    }

    @Getter
    @Setter
    public static class KafkaCluster {
        private String clusterName;
        private String clusterId;
        private List<String> bootstrapServers;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            KafkaCluster that = (KafkaCluster) o;
            return Objects.equals(clusterName, that.clusterName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(clusterName);
        }
    }

    @Bean
    public AdminClients registerAdminClients() {
        Map<KafkaCluster, AdminClient> adminClients = new HashMap<>();
        for (KafkaCluster kafkaCluster : clusters) {
            List<String> bootstrapServers = kafkaCluster.getBootstrapServers();
            String bootstrapServerConfig = bootstrapServers.stream().collect(Collectors.joining(","));
            Properties props = new Properties();
            props.put(CommonClientConfigs.BOOTSTRAP_SERVERS_CONFIG, bootstrapServerConfig);

            // API 요청에는 접속이 필수이므로, 접속의 타임아웃 값은 API 요청 타임아웃 보다는 작아야 한다 - 3/6초로 축소
            props.put(CommonClientConfigs.REQUEST_TIMEOUT_MS_CONFIG, 3000); // 커넥션 생성 요청 시에 타임아웃 시간 (default: 30 seconds)
            // 애플리케이션 기동 시에 리퀘스트 타임아웃 값보다는 API 타임아웃이 크게 설정되어야 하며, Client API 호출의 타임아웃 값 설정
//            props.put(CommonClientConfigs.DEFAULT_API_TIMEOUT_MS_CONFIG, 6000); // API 호출 타임아웃 (default: 60 secs)

            // 아래의 RECONNECT 정보는 접속 실패 시에 지수적으로 늘려가면서 접속을 시도하는 시간을 의미 - 접속유지를 위해서는 설정을 유지할 필요 있음
            props.put(CommonClientConfigs.RECONNECT_BACKOFF_MAX_MS_CONFIG, 1000); // 연속적인 접속 실패시에 Backoff 지수적 상승 최대 시간 (default: 1 second)
            props.put(CommonClientConfigs.RECONNECT_BACKOFF_MS_CONFIG, 50); // Backoff 초기 시간 (default: 50ms -> 100ms ... 1000ms)

            // 아래의 RETRY 설정은 RECONNECT 실패가 반복되거나 여러가지 이유(타임아웃 등)로 접속에 실패한 경우 다시 시도하는 횟수
            props.put(CommonClientConfigs.RETRY_BACKOFF_MS_CONFIG, 100); // 리퀘스트 요청 실패 시에 대기 후에 다시 요청하는 시간 (default: 100ms)
            // 실패하는 경우 재시도하지 않으면, 일시적인 장애에도 서버를 계속 재시작 해주어야 하므로, 설정 조정이 필요함
            props.put(CommonClientConfigs.RETRIES_CONFIG, 1); // 리퀘스트 요청 실패 시에 최대 재시도하는 횟수 (default: 0 or 2147483647)

            adminClients.put(kafkaCluster, AdminClient.create(props));
        }
        return new AdminClients(adminClients);
    }

}
