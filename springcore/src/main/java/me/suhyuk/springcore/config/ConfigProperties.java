package me.suhyuk.springcore.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/* Created by psyoblade on 2023-06-13 */
@Configuration
@ConfigurationProperties(prefix = "mail")
public class ConfigProperties {

    private String hostName;
    private int port;
    private String from;
    private String foo;

    private String existsInProperties; // = "declared-nick-name";
    private String existsNullInProperties = "declared-nick-name";
    private String notExistsInProperties = "declared-nick-name";
    private String notExistsAnywhere;


    // standard getters and setters

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getExistsInProperties() {
        if (existsInProperties == null) {
            return "get-null-nick-name";
        } else {
            return existsInProperties;
        }
    }

    public void setExistsInProperties(String existsInProperties) {
        this.existsInProperties = existsInProperties;
    }

    public String getNotExistsInProperties() {
        if (notExistsInProperties == null) {
            return "get-null-nick-name";
        } else {
            return notExistsInProperties;
        }
    }

    public void setNotExistsInProperties(String notExistsInProperties) {
        this.notExistsInProperties = notExistsInProperties;
    }

    public String getExistsNullInProperties() {
        if (existsNullInProperties == null) {
            return "get-null-nick-name";
        } else {
            return existsNullInProperties;
        }
    }

    public void setExistsNullInProperties(String existsNullInProperties) {
        this.existsNullInProperties = existsNullInProperties;
    }

    public String getNotExistsAnywhere() {
        if (notExistsAnywhere == null) {
            return "get-null-nick-name";
        } else {
            return notExistsAnywhere;
        }
    }

    public void setNotExistsAnywhere(String notExistsAnywhere) {
        this.notExistsAnywhere = notExistsAnywhere;
    }

    public String getFoo() {
        return foo;
    }

    public void setFoo(String foo) {
        this.foo = foo;
    }

    @Override
    public String toString() {
        return "ConfigProperties{" + "hostName='" + hostName + '\'' + ", port=" + port + ", from='" + from + '\''
            + ", foo='" + foo + '\'' + ", existsInProperties='" + existsInProperties + '\''
            + ", existsNullInProperties='" + existsNullInProperties + '\'' + ", notExistsInProperties='"
            + notExistsInProperties + '\'' + '}';
    }
}