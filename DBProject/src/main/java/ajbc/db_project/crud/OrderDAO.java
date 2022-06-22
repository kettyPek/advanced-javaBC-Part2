package ajbc.db_project.crud;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Filters.*;

import ajbc.db_project.models.Order;

public class OrderDAO {

	private MongoCollection<Order> orders;

	public OrderDAO(MongoCollection<Order> orders) {
		this.orders = orders;
	}

	public List<Order> getOrdersByCustomerId(ObjectId customerId) {
		return orders.find(eq("customer_id", customerId)).into(new ArrayList<>());
	}

	public long avaliableRoomsByHotelId(ObjectId hotelId, LocalDate startDate, int numOfNights) {
		LocalDate endDate = startDate.plusDays(numOfNights);
		Bson filter = and(eq("hotel_id", hotelId),
				or(and(gte("end_date", endDate), lte("start_date", endDate)),
						and(gte("end_date", startDate), lte("start_date", startDate)),
						and(lte("end_date", endDate), gte("start_date", startDate))));
		return orders.countDocuments(filter);
	}

	public void insertOrder(Order order) {
		orders.insertOne(order);
	}

	public void deleteById(ObjectId orderId) {
		orders.deleteOne(eq("_id", orderId));

	}

	public Order getOrderById(ObjectId orderId) {
		return orders.find(eq("_id", orderId)).first();
	}

}
