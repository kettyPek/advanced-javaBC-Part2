package ajbc.learn.program;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.ProductDao;

public class Runner {
	
	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		// The dependency
		ProductDao dao1;
		
		// The spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
		System.out.println("------------------------------");
		dao1 = ctx.getBean("jdbcDao",ProductDao.class);
		
//		System.out.println("The number of products is DB: " + dao1.count());
//		System.out.println("------------------------------");
		
	}

}
