package me.suhyuk.springinit;

import me.suhyuk.springlib.Holoman;
//import org.apache.catalina.Context;
//import org.apache.catalina.LifecycleException;
//import org.apache.catalina.startup.Tomcat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

@SpringBootApplication
@EnableConfigurationProperties(HolomanProperties.class)
public class SpringinitApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(SpringinitApplication.class);
//		application.setWebApplicationType(WebApplicationType.NONE);
		application.run(args); // 클래스를 넣어주면 안된다
//		application.run(SpringinitApplication.class, args);
//		startTomcat();
	}

	/*
	public static void startTomcat() throws LifecycleException {
		Tomcat tomcat = new Tomcat();
		tomcat.setPort(8080);

		System.out.println("Starting tomcat");
		Context context = tomcat.addContext("", "/");
		HttpServlet servlet = new HttpServlet() {
			@Override
			protected void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
				PrintWriter writer = res.getWriter();
				writer.println("<html><head><title>");
				writer.println("안녕하세요");
				writer.println("</title></head></html>");
			}
		};
		String servletName = "HelloServlet";
		tomcat.addServlet("", servletName, servlet);
		context.addServletMappingDecoded("/hello", servletName);
		tomcat.start();
		tomcat.getServer().await();
	}
	*/

	// @Bean // 단지 빈 선언만 했을 뿐인 데에도 이 메소드는 호출되고 activate 됩니다
	public Holoman getHoloman() {
		Holoman holoman = new Holoman();
		holoman.setName("박수혁");
		holoman.setHowLong(30);
		return holoman;
	}

	// 이번에는 application.properties 파일에 값을 설정하고 싶다면
	@Bean
	public Holoman getHolomanProperties(HolomanProperties properties) {
		Holoman holoman = new Holoman();
		holoman.setName(properties.getName());
		holoman.setHowLong(properties.getHowLong());
		return holoman;
	}

}
