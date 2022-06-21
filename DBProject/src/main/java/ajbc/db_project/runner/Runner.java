package ajbc.db_project.runner;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

import java.time.LocalDate;
import java.util.List;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.bson.types.ObjectId;
import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import ajbc.db_project.models.Customer;
import ajbc.db_project.models.Hotel;
import ajbc.db_project.models.Order;
import ajbc.db_project.models.Room;
import ajbc.db_project.utils.MyConnectionString;
import ajbc.db_project.utils.Utils;
import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {
	
	public static void main(String[] args) {
		
		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// TODO 
		CodecRegistry pojoCodecRegistry = fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		
		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry).build();
		
		try (MongoClient mongoClient = MongoClients.create(settings);) {
			
			BookingHandler handler = new BookingHandler(mongoClient);
			
			// Question 1
//			List<Order> ordersOfCustomer = handler.getOrdersByCustomerId();
			
			// Question 2
			List<Hotel> hotelsByCity = handler.getHotelsByCity("Haifa");
			System.out.println("hotels in Hiafa: ");
			hotelsByCity.forEach(System.out::println);
			
			// Question 3
//			boolean roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b225c3775de946ace78551"), LocalDate.of(2022, 10, 2), 2);
//			System.out.println("room is avaliable: " + roomAvaliable);
		
			
			
			
		}
		
	}

}
