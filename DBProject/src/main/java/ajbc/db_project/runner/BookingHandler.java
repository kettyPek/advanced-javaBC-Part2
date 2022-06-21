package ajbc.db_project.runner;

import java.time.LocalDate;
import java.util.List;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

import ajbc.db_project.crud.CustomerDAO;
import ajbc.db_project.crud.HotelDAO;
import ajbc.db_project.crud.OrderDAO;
import ajbc.db_project.crud.RoomDAO;
import ajbc.db_project.models.Customer;
import ajbc.db_project.models.Hotel;
import ajbc.db_project.models.Order;
import ajbc.db_project.models.Room;

public class BookingHandler {

	private HotelDAO hotelDAO;
	private RoomDAO roomDAO;
	private OrderDAO orderDAO;
	private CustomerDAO customerDAO;

	public BookingHandler(MongoClient mongoClient) {
		MongoDatabase bookingResDb = mongoClient.getDatabase("booking_reservations");
		hotelDAO = new HotelDAO(bookingResDb.getCollection("hotels", Hotel.class));
		roomDAO = new RoomDAO(bookingResDb.getCollection("rooms", Room.class));
		orderDAO = new OrderDAO(bookingResDb.getCollection("orders", Order.class));
		customerDAO = new CustomerDAO(bookingResDb.getCollection("customers", Customer.class));
	}
	
	public List<Order> getOrdersByCustomerId(ObjectId customerId){
		return orderDAO.getOrdersByCustomerId(customerId);
	}
	
	public List<Hotel> getHotelsByCity(String city) {
		return hotelDAO.getHotelsByCity(city);
	}

	public boolean RoomIsAvaliable(ObjectId hotelId, LocalDate startDate, int numOfNights) {
		long takenRooms = orderDAO.avaliableRoomsByHotelId(hotelId, startDate, numOfNights);
		int totalRoomsInHotel = hotelDAO.getNumOfRooms(hotelId);
		System.out.println(totalRoomsInHotel);
		return totalRoomsInHotel - takenRooms > 0 ? true : false;
	}

}
