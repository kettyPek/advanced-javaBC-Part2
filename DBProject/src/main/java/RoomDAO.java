import java.util.List;

import com.mongodb.client.MongoCollection;

import ajbc.db_project.models.Room;

public class RoomDAO {
	
	public void insertOne(MongoCollection<Room> collection ,Room room) {
		collection.insertOne(room);
	}
	
	public void insertMany(MongoCollection<Room> collection ,List<Room> rooms) {
		collection.insertMany(rooms);
	}
	

}
