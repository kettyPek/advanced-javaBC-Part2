package ajbc.db_project.runner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.google.gson.Gson;

import ajbc.db_project.crud.CustomerDAO;
import ajbc.db_project.crud.HotelDAO;
import ajbc.db_project.crud.OrderDAO;
import ajbc.db_project.crud.RoomDAO;
import ajbc.db_project.models.Customer;
import ajbc.db_project.models.Hotel;
import ajbc.db_project.models.Order;
import ajbc.db_project.models.Room;

import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Projections.*;

public class BookingHandler {

	private Gson gson = new Gson();

	private HotelDAO hotelDAO;
	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;
	private MongoCollection<Document> ordersDocCollection;

	public BookingHandler(MongoClient mongoClient) {
		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		hotelDAO = new HotelDAO(bookingResDb.getCollection("hotels", Hotel.class));
		orderDAO = new OrderDAO(bookingResDb.getCollection("orders", Order.class));
		customerDAO = new CustomerDAO(bookingResDb.getCollection("customers", Customer.class));
		ordersDocCollection = bookingResDb.getCollection("orders");
	}

	public List<Order> getOrdersByCustomerId(String customerId) {
		return orderDAO.getOrdersByCustomerId(new ObjectId(customerId));
	}

	public List<Hotel> getHotelsByCity(String city) {
		return hotelDAO.getHotelsByCity(city);
	}

	public boolean RoomIsAvaliable(ObjectId hotelId, LocalDate startDate, int numOfNights) {
		long takenRooms = orderDAO.avaliableRoomsByHotelId(hotelId, startDate, numOfNights);
		int totalRoomsInHotel = hotelDAO.getNumOfRooms(hotelId);
		return totalRoomsInHotel - takenRooms > 0 ? true : false;
	}

	public void placeOrder(Order order) {
		if (!RoomIsAvaliable(order.getHotelId(), order.getStartDate(), order.getNumOfNights()))
			System.out.println("There is no avaliable rooms");
		else {
			Order orderWithPrice = setOrderTotalPrice(order);
			orderDAO.insertOrder(orderWithPrice);
			hotelDAO.addOrder(orderWithPrice.getHotelId(), orderWithPrice.getId());
			customerDAO.addOrder(orderWithPrice.getCustomerId(), orderWithPrice.getId());
			System.out.println("The order is placed");
		}

	}

	private Order setOrderTotalPrice(Order order) {
		double totalPrice = hotelDAO.getHotelById(order.getHotelId()).getPricePerNight() * order.getNumOfNights();
		order.setTotalPrice(totalPrice);
		return order;
	}

	public void cancleOrderById(String orderIdStr) {
		ObjectId orderId = new ObjectId(orderIdStr);
		Order order = orderDAO.getOrderById(orderId);
		if (order != null) {
			orderDAO.deleteById(orderId);
			hotelDAO.deleteOrderById(order.getHotelId(), orderId);
			customerDAO.deleteOrderById(order.getCustomerId(), orderId);
			System.out.println("Order deleted");
		} else
			System.out.println("Order does not exist");
	}

	public void getSortedHotelsByIncome() {
		Bson group = group("$hotel_id",sum("income","$total_price"));
		Bson sort = sort(Sorts.descending("income"));
		List<Document> sortedHotelIds =  ordersDocCollection.aggregate(Arrays.asList(group,sort)).into(new ArrayList<>());
		sortedHotelIds.forEach(System.out::println);
	}

	public void sumOfAllOrders() {

		Document sumOfOrders = ordersDocCollection.aggregate(Arrays.asList(group(null, sum("income", "$total_price")),
				project(fields(Projections.include("income"), Projections.excludeId())))).first();
		System.out.println(sumOfOrders);
	}
	
	public void profitbleMonths(int top) {
		Bson group = group(dataToString(("date",dateFromString("dateString", "$start_date")),("format","%Y-%m")));
		List<Document> proMonths = ordersDocCollection.aggregate(Arrays.asList(null)).into(new ArrayList<>());
	}


}
