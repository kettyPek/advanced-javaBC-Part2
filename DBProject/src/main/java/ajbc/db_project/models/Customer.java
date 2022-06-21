package ajbc.db_project.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Customer {
	
	private ObjectId id;
	@BsonProperty(value = "first_name")
	private String firstName;
	@BsonProperty(value = "last_name")
	private String lastName;
	private String country;
	@BsonProperty(value = "orders_ids")
	private List<ObjectId> ordersId;

	public Customer(ObjectId id, String firstName, String lastName, String country, List<ObjectId> ordersId) {
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.ordersId = ordersId;
	}
	
	public Customer(String firstName, String lastName, String country, List<ObjectId> ordersId) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
		this.ordersId = ordersId;
	}
	
	public Customer(String firstName, String lastName, String country) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.country = country;
	}

	public ObjectId getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getCountry() {
		return country;
	}
	public List<ObjectId> getOrdersId() {
		return ordersId;
	}
	@Override
	public String toString() {
		return "Customer [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", country=" + country
				+ ", ordersId=" + ordersId + "]";
	}
	
	

}
