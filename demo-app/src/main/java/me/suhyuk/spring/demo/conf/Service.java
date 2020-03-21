package me.suhyuk.spring.demo.conf;

import org.springframework.stereotype.Component;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class Service {
    List<String> servers;

    String serviceId;

    String metadataId;

    @NotNull(message = "아이디는 널일 수 없습니다")
    String id;

    @Min(0) @Max(3600)
    @NotNull(message = "타임아웃 값은 널 일 수 없습니다")
    int timeout;

    public List<String> getServers() {
        return servers;
    }

    public void setServers(List<String> servers) {
        this.servers = servers;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMetadataId() {
        return metadataId;
    }

    public void setMetadataId(String metadataId) {
        this.metadataId = metadataId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTimeout() {
        return timeout;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    @Override
    public String toString() {
        return "Service{" +
                "servers=" + servers +
                ", serviceId='" + serviceId + '\'' +
                ", metadataId='" + metadataId + '\'' +
                '}';
    }
}
