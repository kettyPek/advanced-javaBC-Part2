package ajbc.db_project.models;

import java.time.LocalDate;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import ajbc.db_project.crud.HotelDAO;

public class Order {

	private ObjectId id;
	@BsonProperty(value = "hotel_id")
	private ObjectId hotelId;
	@BsonProperty(value = "customer_id")
	private ObjectId customerId;
	@BsonProperty(value = "order_date")
	private LocalDate orderDate;
	@BsonProperty(value = "start_date")
	private LocalDate startDate;
	@BsonProperty(value = "end_date")
	private LocalDate endDate;
	@BsonProperty(value = "number_of_nights")
	private int numOfNights;
	@BsonProperty(value = "total_price")
	private double totalPrice;

	public Order(ObjectId id, ObjectId hotelId, ObjectId customerId, LocalDate orderDate, LocalDate startDate,
			int numOfNights, double totalPrice) {
		this.id = id;
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.numOfNights = numOfNights;
		this.totalPrice = totalPrice;
		endDate = startDate.plusDays(numOfNights);
	}

	public Order(ObjectId hotelId, ObjectId customerId, LocalDate orderDate, LocalDate startDate, int numOfNights,
			double totalPrice) {
		this.hotelId = hotelId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.startDate = startDate;
		this.numOfNights = numOfNights;
		this.totalPrice = totalPrice;
		endDate = startDate.plusDays(numOfNights);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public ObjectId getHotelId() {
		return hotelId;
	}

	public void setHotelId(ObjectId hotelId) {
		this.hotelId = hotelId;
	}

	public ObjectId getCustomerId() {
		return customerId;
	}

	public void setCustomerId(ObjectId customerId) {
		this.customerId = customerId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public int getNumOfNights() {
		return numOfNights;
	}

	public void setNumOfNights(int numOfNights) {
		this.numOfNights = numOfNights;
	}

	public double getTotalPrice() {
		return totalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		this.totalPrice = totalPrice;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", hotelId=" + hotelId + ", customerId=" + customerId + ", orderDate=" + orderDate
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", numOfNights=" + numOfNights + ", totalPrice="
				+ totalPrice + "]";
	}

}
