package ajbc.db_project.runner;

import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import org.slf4j.LoggerFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import ajbc.db_project.utils.MyConnectionString;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;

public class Runner {

	public static void main(String[] args) {

		Logger root = (Logger) LoggerFactory.getLogger(Logger.ROOT_LOGGER_NAME);
		root.setLevel(Level.ERROR);

		// TODO
		CodecRegistry pojoCodecRegistry = CodecRegistries
				.fromProviders(PojoCodecProvider.builder().automatic(true).build());
		CodecRegistry codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(),
				pojoCodecRegistry);

		ConnectionString connectionString = MyConnectionString.uri();
		MongoClientSettings settings = MongoClientSettings.builder().applyConnectionString(connectionString)
				.serverApi(ServerApi.builder().version(ServerApiVersion.V1).build()).codecRegistry(codecRegistry)
				.build();

		try (MongoClient mongoClient = MongoClients.create(settings);) {

//			Utils.creatHotelsCollection(mongoClient);
//			Utils.createCustomersCollection(mongoClient);
			BookingHandler handler = new BookingHandler(mongoClient);

//			// Question 1
//			List<Order> ordersOfCustomer = handler.getOrdersByCustomerId("62b2b6f585789e617be4c565");
//			System.out.println("All orders of Customer:");
//			ordersOfCustomer.forEach(System.out::println);

//			// Question 2
//			List<Hotel> hotelsByCity = handler.getHotelsByCity("Haifa");
//			System.out.println("hotels in Hiafa: ");
//			hotelsByCity.forEach(System.out::println);

//			// Question 3
//			boolean roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b2c33830b6140ecbdb605c"), LocalDate.of(2022, 10, 2), 2);
//			System.out.println("room is available: " + roomAvaliable);

			// Question 4
//			Order order = new Order(new ObjectId("62b2c33830b6140ecbdb605c"), new ObjectId("62b2b6f585789e617be4c565"), LocalDate.of(2022, 06, 22), LocalDate.of(2022, 10, 2), 10);
//			handler.placeOrder(order);

			// placing more orders to check availability
//			Order order1 = new Order(new ObjectId("62b2c33830b6140ecbdb605d"), new ObjectId("62b2b6f585789e617be4c566"), LocalDate.of(2022, 06, 22), LocalDate.of(2022, 10, 2), 10);
//			Order order2 = new Order(new ObjectId("62b2c33830b6140ecbdb605c"), new ObjectId("62b2b6f585789e617be4c567"), LocalDate.of(2022, 06, 22), LocalDate.of(2022, 10, 2), 10);
//			handler.placeOrder(order1);
//			handler.placeOrder(order2);

//			// checking availability  - all must return false
//			boolean roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b2c33830b6140ecbdb605e"), LocalDate.of(2022, 10, 3), 2);
//			System.out.println("room is available: " + roomAvaliable);
//			
//			roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b2c33830b6140ecbdb605e"), LocalDate.of(2022, 10, 01), 2);
//			System.out.println("room is available: " + roomAvaliable);
//			
//			roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b2c33830b6140ecbdb605e"), LocalDate.of(2022, 10, 3), 12);
//			System.out.println("room is available: " + roomAvaliable);
//			
//			roomAvaliable = handler.RoomIsAvaliable(new ObjectId("62b2c33830b6140ecbdb605e"), LocalDate.of(2022, 9, 20), 40);
//			System.out.println("room is available: " + roomAvaliable);

			// trying to place order - should return no available rooms
//			Order order = new Order(new ObjectId("62b2c33830b6140ecbdb605e"), new ObjectId("62b2b6f585789e617be4c565"), LocalDate.of(2022, 06, 22), LocalDate.of(2022, 10, 3), 12);
//			handler.placeOrder(order);

			// Question 5
//			handler.cancleOrderById("62b2d38b131af7675c596059");
//			handler.cancleOrderById("62b2d38b131af7675c599067");

//			// Question 6
//			handler.getSortedHotelsByIncome();

//			// Question 7 
//			double totalSum = handler.sumOfAllOrders();
//			System.out.println("Total sum of all orders: " + totalSum);

		}

	}

}
