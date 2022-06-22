package ajbc.db_project.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Hotel {

	private ObjectId id;
	private String name;
	private Address address;
	private int rank;
	@BsonProperty(value = "rooms_ids")
	private List<ObjectId> roomsId;
	@BsonProperty(value = "price_per_night")
	private double pricePerNight;
	@BsonProperty(value = "orders_ids")
	private List<ObjectId> ordersId;
	@BsonProperty(value = "number_of_rooms")
	private int numOfRooms;

	public Hotel(ObjectId id, String name, Address address, int rank, List<ObjectId> roomsId, double pricePerNight,
			List<ObjectId> ordersId,int numOfRooms) {
		this.id = id;
		this.name = name;
		this.address = address;
		this.rank = rank;
		this.roomsId = roomsId;
		this.pricePerNight = pricePerNight;
		this.ordersId = ordersId;
		this.numOfRooms = numOfRooms;
	}

	public Hotel(String name, Address address, int rank, List<ObjectId> roomsId, double pricePerNight,
			List<ObjectId> ordersId) {
		this.name = name;
		this.address = address;
		this.rank = rank;
		this.roomsId = roomsId;
		this.pricePerNight = pricePerNight;
		this.ordersId = ordersId;
		numOfRooms = roomsId.size();
	}

	public Hotel() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public List<ObjectId> getRoomsId() {
		return roomsId;
	}

	public void setRoomsId(List<ObjectId> roomsId) {
		this.roomsId = roomsId;
	}

	public double getPricePerNight() {
		return pricePerNight;
	}

	public void setPricePerNight(double pricePerNight) {
		this.pricePerNight = pricePerNight;
	}

	public List<ObjectId> getOrdersId() {
		return ordersId;
	}

	public void setOrdersId(List<ObjectId> ordersId) {
		this.ordersId = ordersId;
	}

	public int getNumOfRooms() {
		return numOfRooms;
	}

	public void setNumOfRooms(int numOfRooms) {
		this.numOfRooms = numOfRooms;
	}

	@Override
	public String toString() {
		return "Hotel [id=" + id + ", name=" + name + ", address=" + address + ", rank=" + rank + ", roomsId=" + roomsId
				+ ", pricePerNight=" + pricePerNight + ", ordersId=" + ordersId + ", numOfRooms=" + numOfRooms + "]";
	}

}
