package ajbc.learn.program;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ajbc.learn.config.AppConfig;
import ajbc.learn.models.Category;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class RunnerHiberante {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
				SessionFactory factory = ctx.getBean(SessionFactory.class);
				Session session = factory.openSession();) {
			
			// Read from DB
			Category cat1 = session.get(Category.class, 1); // select * from Categories where CategiryId = 1
			System.out.println(cat1);
			System.out.println("----------");
			
			// Write to DB
			Category cat2 = new Category();
			cat2.setCatName("Baby");
			cat2.setDescription("diapers, bottels");
			Transaction transanction = session.beginTransaction();
			try {
				session.persist(cat2);
				transanction.commit();
				System.out.println("cat2 in DB");
			}catch(Exception e) {
				transanction.rollback();
				System.out.println("Insertion rolled back");
			}
			
			
			
		}
	}

}
