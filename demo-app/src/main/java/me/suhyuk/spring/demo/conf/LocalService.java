package me.suhyuk.spring.demo.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LocalService {

    private Logger logger = LoggerFactory.getLogger(LocalService.class);

    public void executeProcedure(String message) {
        logger.info("로컬 서비스가 시작 되었습니다 - {}", message);
    }

    public boolean executeQuery(String args) {
        logger.info("로컬 서비스 {} 가 수행 되었습니다.", args);
        return true;
    }

}
