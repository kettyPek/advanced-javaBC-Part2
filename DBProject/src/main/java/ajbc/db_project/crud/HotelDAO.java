package ajbc.db_project.crud;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import ajbc.db_project.models.Hotel;

import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.*;

import java.util.ArrayList;
import java.util.List;

public class HotelDAO {

	private MongoCollection<Hotel> hotels;

	public HotelDAO(MongoCollection<Hotel> hotels) {
		this.hotels = hotels;
	}

	public List<Hotel> getHotelsByCity(String city) {
		return hotels.find(eq("address.city", city)).into(new ArrayList<>());
	}

	public int getNumOfRooms(ObjectId hotelId) {
		return hotels.find(eq("_id", hotelId)).first().getNumOfRooms();
	}

	public Hotel getHotelById(ObjectId hotelId) {
		return hotels.find(eq("_id", hotelId)).first();
	}

	public void addOrder(ObjectId hotelId, ObjectId orderId) {
		hotels.updateOne(eq("_id", hotelId), push("orders_ids", orderId));
	}

	public void deleteOrderById(ObjectId hotelId, ObjectId orderId) {
		hotels.updateOne(eq("_id", hotelId), pull("orders_ids", orderId));
	}

}
