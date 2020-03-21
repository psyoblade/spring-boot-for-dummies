package me.suhyuk.spring.demo;

import me.suhyuk.spring.demo.conf.AppConfig;
import me.suhyuk.spring.demo.conf.LocalService;
import me.suhyuk.spring.demo.conf.ServiceConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void 어플리케이션기동() {
		assert(true);
	}

	@Autowired
	private AppConfig appConfig;

	@Test
	public void 설정파일_읽어오기() {
	    assertEquals("tablesnap-import", appConfig.getService().getServiceId());
	}

	@Test
	public void 타임아웃_제약() {
		assertTrue(0 <= appConfig.getService().getTimeout());
		assertTrue(3600 >= appConfig.getService().getTimeout());
	}

	@Autowired LocalService localService;
	@Autowired LocalService clonedLocalService;

	@Test
	public void 직접_의존성_주입() {
		localService.executeProcedure("테스트");
		assertTrue(localService.executeQuery("매개변수1"));
		assertEquals(localService, clonedLocalService);
	}

	@Autowired
	private ServiceConfig serviceConfig;

	@Test
	public void 생성자를_통한_의존성_주입 () {
	    serviceConfig.executeQuery("매개변수2");
	}

	@Autowired
	ApplicationContext applicationContext;

	@Test
	public void 직접_빈가져오기() {
		LocalService localService = (LocalService) applicationContext.getBean(LocalService.class);
		localService.executeQuery("직접 가져온 명령어 수행");
	}
}
