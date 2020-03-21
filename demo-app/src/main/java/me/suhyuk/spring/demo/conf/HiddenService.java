package me.suhyuk.spring.demo.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HiddenService {
    private Logger logger = LoggerFactory.getLogger(HiddenService.class);

    public boolean executeQuery(String args) {
        logger.info("숨겨진 로컬 서비스 {} 가 수행 되었습니다.", args);
        return true;
    }

}
