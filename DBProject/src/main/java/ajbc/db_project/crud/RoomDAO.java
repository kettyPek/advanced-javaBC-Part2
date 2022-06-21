package ajbc.db_project.crud;


import com.mongodb.client.MongoCollection;

import ajbc.db_project.models.Room;

public class RoomDAO {

	private MongoCollection<Room> rooms;
	
	public RoomDAO(MongoCollection<Room> rooms) {
		this.rooms = rooms;
	}



}
