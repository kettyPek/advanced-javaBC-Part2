package ajbc.learn.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.DataSourceConnectionFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import ajbc.learn.dao.JdbcProductDao;
import ajbc.learn.dao.MongoProductDao;
import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import ajbc.learn.models.Supplier;

@EnableAspectJAutoProxy
@ComponentScan(basePackages = { "ajbc.learn.dao" , "ajbc.learn.aspects"})
@Configuration
@PropertySource("classpath:jdbc.properties")
public class AppConfig {

	private static final int INIT_SIZE = 10, MAX_SIZE = 100, MAX_WAIT = 500, MAX_IDLE = 50, MIN_IDLE = 2;

	@Value("${server_address}")
	private String serverAdress;
	@Value("${port}")
	private String port;
	@Value("${db_name}")
	private String dataBaseName;
	@Value("${server_name}")
	private String serverName;
	@Value("${password}")
	private String passWord;
	@Value("${user}")
	private String userName;
	@Value("${driver_class_name}")
	private String driverClassName;
	@Value("${dialect}")
	private String dialect;

	@Bean
	public JdbcTemplate jdbcTemplate(DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		// credentials
		dataSource.setUsername(userName);
		dataSource.setPassword(passWord);
		dataSource.setUrl(url());

		// preferences
		dataSource.setInitialSize(INIT_SIZE);
		dataSource.setMaxTotal(MAX_SIZE);
		dataSource.setMaxWaitMillis(MAX_WAIT);
		dataSource.setMaxIdle(MAX_IDLE);
		dataSource.setMinIdle(MIN_IDLE);

		return dataSource;
	}

	@Bean
	public Connection connection() throws SQLException {
		return DriverManager.getConnection(url(), userName, passWord);
	}

	@Bean
	public SessionFactory sessionFatory() {
		Properties props = new Properties();
		props.setProperty("hibernate.connection.driver_class", driverClassName);
		props.setProperty("hibernate.connection.url", url());
		props.setProperty("hibernate.connection.user", userName);
		props.setProperty("hibernate.connection.password", passWord);
		props.setProperty("hibernate.dialect", dialect);

		org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
		configuration.setProperties(props);

		configuration.addAnnotatedClass(Category.class);
		return configuration.buildSessionFactory();
	}

	@Bean
	public LocalSessionFactoryBean sessionFactory(DataSource dataSource) {
		LocalSessionFactoryBean factory = new LocalSessionFactoryBean();
		factory.setDataSource(dataSource);
		// add mapped classes
		factory.setAnnotatedClasses(Category.class, Supplier.class, Product.class);

		// add properties
		Properties props = new Properties();
		props.setProperty("hibernate.dialect", dialect);
		props.setProperty("hibernate.show_sql", "true");
		props.setProperty("hibernate.format_sql", "true");

		factory.setHibernateProperties(props);
		return factory;
	}

	@Bean
	public HibernateTemplate hibernateTemplate(SessionFactory sessionFactory) {
		return new HibernateTemplate(sessionFactory);
	}
	
//	@Bean 
//	public HibernateTrans

//	@Scope("prototype") // lazy instantiated 
	@Bean
	public MongoProductDao mongoDao() {
		return new MongoProductDao();
	}

	// ------- Connection --------

	// option 1 - manual wiring
////	@Lazy
////	@Scope("singleton") // default
//	@Bean
//	public JdbcProductDao jdbcDao() throws SQLException {
//		JdbcProductDao dao =  new JdbcProductDao();
//		dao.setConnection(connection()); // manual wiring
//		return dao;
//	}

	// option 2 - passing connection as an argument
//	@Bean
//	public JdbcProductDao jdbcDao(Connection connection) throws SQLException {
//		JdbcProductDao dao =  new JdbcProductDao();
//		dao.setConnection(connection); 
//		return dao;
//	}

	// option 3 - injection with @Autowired
//	@Bean
//	public JdbcProductDao jdbcDao(Connection connection) throws SQLException {
//		JdbcProductDao dao = new JdbcProductDao();
//		return dao;
//	}

	private String url() {
		return "jdbc:sqlserver://" + serverAdress + ":" + port + ";databaseName=" + dataBaseName + ";servername="
				+ serverName + ";encrypt=false;";
	}

}
