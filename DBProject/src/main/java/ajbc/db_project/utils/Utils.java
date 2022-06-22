package ajbc.db_project.utils;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.InsertManyResult;

import ajbc.db_project.models.Address;
import ajbc.db_project.models.Customer;
import ajbc.db_project.models.Hotel;
import ajbc.db_project.models.Order;
import ajbc.db_project.models.Room;

import static com.mongodb.client.model.Filters.*;
import com.mongodb.client.model.Projections;

import static com.mongodb.client.model.Projections.*;
import static com.mongodb.client.model.Aggregates.*;

public class Utils {

	public static void creaRoomsCollection(MongoClient mongoClient) {

		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		MongoCollection<Room> rooms = bookingResDb.getCollection("rooms", Room.class);
		rooms.insertMany(Arrays.asList(new Room(2, true), new Room(2, true), new Room(2, false), new Room(2, false),
				new Room(4, true), new Room(4, false), new Room(4, true), new Room(3, true), new Room(3, false)));
	}

	public static void creatHotelsCollection(MongoClient mongoClient) {

		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		MongoCollection<Hotel> hotels = bookingResDb.getCollection("hotels", Hotel.class);
		List<ObjectId> roomsFor2 = Arrays.asList(new ObjectId("62b21f11de44252d4a25597b"),
				new ObjectId("62b21f11de44252d4a25597c"), new ObjectId("62b21f11de44252d4a25597d"),
				new ObjectId("62b21f11de44252d4a25597e"));
		List<ObjectId> roomsFor3 = Arrays.asList(new ObjectId("62b21f11de44252d4a255982"),
				new ObjectId("62b21f11de44252d4a255983"));
		List<ObjectId> roomsFor4 = Arrays.asList(new ObjectId("62b21f11de44252d4a25597f"),
				new ObjectId("62b21f11de44252d4a255980"), new ObjectId("62b21f11de44252d4a255981"));
		hotels.insertMany(Arrays.asList(
				new Hotel("Hermoso", new Address("Alenbi", 15, "Tel Aviv", "Israel"), 5, roomsFor2, 1000, new ArrayList<ObjectId>()),
				new Hotel("Lindo", new Address("Rakefet", 39, "Haifa", "Israel"), 4, roomsFor4, 3200, new ArrayList<ObjectId>()),
				new Hotel("Bello", new Address("Herzel", 24, "Eilat", "Israel"), 4, roomsFor3, 4000, new ArrayList<ObjectId>())));

	}

	public static void createCustomersCollection(MongoClient mongoClient) {
		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		MongoCollection<Customer> customersCollection = bookingResDb.getCollection("customers", Customer.class);
		List<Customer> customersList = Arrays.asList(
				new Customer("alon", "choen", "Israel",new ArrayList<ObjectId>()),
				new Customer("david", "shalom", "Israel",new ArrayList<ObjectId>()), 
				new Customer("chen", "yosef", "Israel",new ArrayList<ObjectId>()),
				new Customer("mira", "heim", "Israel",new ArrayList<ObjectId>()));
		customersCollection.insertMany(customersList);
	}

	public static void createOrdersCollection(MongoClient mongoClient) {
		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		MongoCollection<Order> ordersCollection = bookingResDb.getCollection("orders", Order.class);
	}

}
