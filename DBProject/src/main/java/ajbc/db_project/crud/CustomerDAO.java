package ajbc.db_project.crud;

import com.mongodb.client.MongoCollection;

import ajbc.db_project.models.Customer;

public class CustomerDAO {

	private MongoCollection<Customer> customers;

	public CustomerDAO(MongoCollection<Customer> customers) {
		this.customers = customers;
	}

}
