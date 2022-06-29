package ajbc.learn.program;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class RunnerHibernate2 {
	
	public static void main(String[] args) throws DaoException {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);){
	
			ProductDao dao = ctx.getBean("htDao",ProductDao.class);
			
			List<Product> products = dao.getProductsByPriceRange(5.0, 10.0);
			products.forEach(System.out::println);
			
			
		}
	}
	
	

}
