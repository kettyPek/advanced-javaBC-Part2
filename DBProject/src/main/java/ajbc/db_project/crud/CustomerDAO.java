package ajbc.db_project.crud;

import org.bson.types.ObjectId;

import com.mongodb.client.MongoCollection;

import static com.mongodb.client.model.Updates.*;
import static com.mongodb.client.model.Filters.*;

import ajbc.db_project.models.Customer;

public class CustomerDAO {

	private MongoCollection<Customer> customers;

	public CustomerDAO(MongoCollection<Customer> customers) {
		this.customers = customers;
	}

	public void addOrder(ObjectId customerId, ObjectId orderId) {
		customers.updateOne(eq("_id", customerId), push("orders_ids", orderId));

	}

	public void deleteOrderById(ObjectId customerId, ObjectId orderId) {
		customers.updateOne(eq("_id", customerId), pull("orders_ids", orderId));
	}

}
