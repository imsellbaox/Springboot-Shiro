package studyspringboot.spring_shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

/**
 *
 */
@SpringBootApplication
public class SpringShiroApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(SpringShiroApplication.class, args);
		Environment e = context.getBean(Environment.class);
		System.out.println("http://localhost:"+e.getProperty("local.server.port"));
	}

}
