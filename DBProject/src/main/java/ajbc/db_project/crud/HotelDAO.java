package ajbc.db_project.crud;


import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;

import ajbc.db_project.models.Hotel;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Aggregates.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HotelDAO {
	 
	private MongoCollection<Hotel> hotels;
	
	public HotelDAO(MongoCollection<Hotel> hotels) {
		this.hotels = hotels;
	}
	
	public List<Hotel> getHotelsByCity(String city){
		return hotels.find(eq("rank", 5)).into(new ArrayList<>());
	}
	
	public int getNumOfRooms(ObjectId hotelId) {
		return hotels.aggregate(Arrays.asList(eq("_id",hotelId),unwind("$rooms"),Projections.include("rooms"))).into(new ArrayList<>()).size();
	}


}
