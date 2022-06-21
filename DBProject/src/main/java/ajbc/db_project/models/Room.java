package ajbc.db_project.models;

import org.bson.types.ObjectId;

public class Room {

	private ObjectId id;
	private int number;
	private boolean hasBath;

	public Room(ObjectId id, int number, boolean hasBath) {
		this.id = id;
		this.number = number;
		this.hasBath = hasBath;
	}

	public Room(int number, boolean hasBath) {
		super();
		this.number = number;
		this.hasBath = hasBath;
	}

	public ObjectId getId() {
		return id;
	}

	public int getNumber() {
		return number;
	}

	public boolean isHasBath() {
		return hasBath;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", hasBath=" + hasBath + "]";
	}

}
