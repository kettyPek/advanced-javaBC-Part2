package ajbc.learn.program;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.jdbc.core.JdbcTemplate;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.models.Product;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

@ComponentScan(basePackages = { "ajbc.learn.dao" })
public class RunnerProduct {

	public static void main(String[] args) throws DaoException {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// The spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcProductDao dao = ctx.getBean("jdbcDao",JdbcProductDao.class);

		Product product = new Product("Malabi", 1, 3, "2 units", 10.5, 20, 5, 7, 4);

//		dao.addProduct(product);

//		product.setProductId(78);
//		product.setUnitPrice(9.5);
//		product.setProductName("MalabiD");
//		dao.updateProduct(product);

//		Product product2 = dao.getProduct(3);
//		System.out.println(product2);

//		dao.deleteProduct(78);

//		List<Product> products = dao.getAllProducts();
//		products.forEach(System.out::println);

//		List<Product> productsByRange = dao.getProductsByPriceRange(5.0,10.0);
//		productsByRange.forEach(System.out::println);

//		List<Product> productsByCategoty = dao.getProductsInCategory(3);
//		productsByCategoty.forEach(System.out::println);

//		List<Product> productsNotInStock = dao.getProductsNotInStock();
//		productsNotInStock.forEach(System.out::println);

//		List<Product> productsOnOrder = dao.getProductsOnOrder();
//		productsOnOrder.forEach(System.out::println);
		
//		List<Product> productsDiscontinued= dao.getDiscontinuedProducts();
//		productsDiscontinued.forEach(System.out::println);
		
		long rows = dao.count();
		System.out.println("There is " + rows + " products");
		
		ctx.close();

	}

}
