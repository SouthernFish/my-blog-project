package tong.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @Author TR
 * @Create 2021/8/11 11:05
 * @Title: EurekaSpringApplication
 * @Description: 服务注册中心启动类
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaSpringApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EurekaSpringApplication.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(EurekaSpringApplication.class, args);
	}
}
