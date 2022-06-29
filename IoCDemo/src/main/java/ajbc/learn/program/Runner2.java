package ajbc.learn.program;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.LoggerFactory;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ajbc.learn.config.AppConfig;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.Category;

public class Runner2 {
	
	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);
		
		// The spring container
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

		JdbcTemplate template = ctx.getBean(JdbcTemplate.class);
		
		RowMapper<Category> rowMapper = new RowMapper<Category>() {

			@Override
			public Category mapRow(ResultSet rs, int rowNum) throws SQLException {
				Category cat = new Category();
				cat.setCategoryId(rs.getInt(1));
				cat.setCatName(rs.getString("categoryName"));
				cat.setDescription(rs.getString("description"));
				cat.setPicture(rs.getBytes("picture"));
				return cat;
			}
		};
		
		// CRUD
//		insertNewShipper(template);
//		updateShippersPhone(template, 4,"(012) 345-6789");
		
		// query that return a single value
//		printNumOfProducts(template);
//		printShippersName(template,4);		
//		printCityOfCustomerById(template,"FISSA");
		
//		printProductsDetails(template,3);
//		printAnyOrderofEmployeeById(template,5);
//		printAllShippers(template);
		
//		printCategoryById(template,rowMapper,2);
		printAllCategories(template,rowMapper);
			
		
		
	}
	
	private static void printAllCategories(JdbcTemplate template, RowMapper<Category> rowMapper) {
		String sql = "select * from Categories ";
		List<Category> categories = template.query(sql,rowMapper);
		categories.forEach(System.out::println);
		
	}

	private static void printCategoryById(JdbcTemplate template, RowMapper<Category> rowMapper, int id) {
		String sql = "select * from Categories where CategoryId = ? ";
		Category cat = template.queryForObject(sql,rowMapper,id);
		System.out.println(cat);
		
	}

	private static void printAllShippers(JdbcTemplate template) {
		String sql = "select * from Shippers ";
		List<Map<String,Object>> data = template.queryForList(sql);
		System.out.println(data);
		
	}

	private static void printAnyOrderofEmployeeById(JdbcTemplate template, int employeeId) {
		String sql = "select OrderID from orders where EmployeeID = ?";
		List<Map<String,Object>> data = template.queryForList(sql, employeeId);
		System.out.println(data);
		
	}

	private static void printProductsDetails(JdbcTemplate template, int id) {
		String sql = "select * from Products where ProductId=?";
		Map<String, Object> data = template.queryForMap(sql, id);
		System.out.println(data);
	}

	private static void printCityOfCustomerById(JdbcTemplate template, String string) {
		String sql = "select city from Customers where CustomerId=?";
		String city = template.queryForObject(sql,String.class,string);
		System.out.println("name : " + city);
		
	}

	private static void printShippersName(JdbcTemplate template, int id) {
		String sql = "select companyName from shippers where ShipperID=?";
		String name = template.queryForObject(sql,String.class,id);
		System.out.println("name : " + name);
		
	}

	private static void printNumOfProducts(JdbcTemplate template) {
		String sql = "select count(*) from products";
		int count = template.queryForObject(sql, int.class);
		System.out.println("count : " + count);
		
	}

	private static void updateShippersPhone(JdbcTemplate template, int id, String phone) {
		String sql = "update shippers set Phone=? where ShipperID=?";
		int changedRows = template.update(sql,phone,id);
		System.out.println("rows affected : " + changedRows);
		
	}

	private static void insertNewShipper(JdbcTemplate template) {
		String sql = "insert into shippers values (?,?)";
		int changedRows = template.update(sql, "imports","054-543-7654");
		System.out.println("rows affected : " + changedRows);
		
		
	}

}
