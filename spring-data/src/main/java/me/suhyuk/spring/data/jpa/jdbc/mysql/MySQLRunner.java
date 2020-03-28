package me.suhyuk.spring.data.jpa.jdbc.mysql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;

@Component
public class MySQLRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(MySQLRunner.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String userName = connection.getMetaData().getUserName();
            logger.info("URL:'{}', UserName:'{}'", url, userName);

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id int NOT NULL AUTO_INCREMENT" +
                    ", name varchar(255)" +
                    ", age int" +
                    ", PRIMARY KEY (id)" +
                    ")";
            statement.executeUpdate(sql);
        }

        jdbcTemplate.execute("INSERT INTO Users (name, age) VALUES ('park.suhyuk', 40)");
    }
}
