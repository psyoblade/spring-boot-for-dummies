package me.suhyuk.spring.data.jdbc.postgres;

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

//@Component
public class PostgresRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(PostgresRunner.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        try (Connection connection = dataSource.getConnection()) {
            String url = connection.getMetaData().getURL();
            String userName = connection.getMetaData().getUserName();
            String driverName = connection.getMetaData().getDriverName();
            logger.info("URL:'{}', UserName:'{}', driverName: '{}", url, userName, driverName);

            Statement statement = connection.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS Users (" +
                    "id int NOT NULL " +
                    ", name varchar(255)" +
                    ", age int" +
                    ", PRIMARY KEY (id)" +
                    ")";
            statement.executeUpdate(sql);
        }

        jdbcTemplate.execute("INSERT INTO Users (id, name, age) VALUES (1, 'park.suhyuk', 40)");
    }
}
