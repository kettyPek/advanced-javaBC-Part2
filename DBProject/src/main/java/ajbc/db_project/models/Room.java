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
		this.number = number;
		this.hasBath = hasBath;
	}

	public Room() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isHasBath() {
		return hasBath;
	}

	public void setHasBath(boolean hasBath) {
		this.hasBath = hasBath;
	}

	@Override
	public String toString() {
		return "Room [id=" + id + ", number=" + number + ", hasBath=" + hasBath + "]";
	}

}
