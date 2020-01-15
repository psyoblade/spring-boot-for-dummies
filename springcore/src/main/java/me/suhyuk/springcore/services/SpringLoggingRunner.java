package me.suhyuk.springcore.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class SpringLoggingRunner implements ApplicationRunner {

    private Logger logger = LoggerFactory.getLogger(AppRunnerUsingBean.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("정보성 로그를 남기자");
        logger.debug("디버그 로그를 남기자");
        logger.error("에러 로그를 남기자");
    }
}
