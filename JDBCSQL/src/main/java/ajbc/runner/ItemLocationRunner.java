package ajbc.runner;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

import ajbc.dbservices.ItemDBService;
import ajbc.dbservices.ItemLocationDBService;
import ajbc.dbservices.LocationDBService;
import ajbc.models.Item;
import ajbc.models.ItemLocation;
import ajbc.models.Location;
import ajbc.utils.ConnectionBuilder;

public class ItemLocationRunner {

	public static void main(String[] args) {

		final String PROPERTIES_FILE = "demo.properties";
		try (Connection connection = new ConnectionBuilder(PROPERTIES_FILE).createConnection()) {
			System.out.println("connected");

//			checkItemMethods(connection);
//			checkLocationMethods(connection);
//			checkItemLocationMethods(connection);
			

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void checkItemMethods(Connection connection) {
		ItemDBService dbService = new ItemDBService();

		Item item1 = new Item(1, "milk", 5, LocalDate.of(2022, 06, 12), 2);
		Item item2 = new Item(2, "bread", 7, LocalDate.of(2022, 02, 02), 5);
		Item item3 = new Item(3, "butter", 10, LocalDate.of(2022, 05, 11), 7);

//		dbService.addItem(connection, item1);
//		dbService.addItem(connection, item2);
//		dbService.addItem(connection, item3);

		Item item4 = dbService.getItem(connection, 2);
		Item item5 = dbService.getItem(connection, 4);
//		
//		System.out.println("some items from DB");
//		System.out.println(item4);
//		System.out.println(item5);

		Item item6 = dbService.UpdateItem(connection, 5, item4);
		Item item7 = dbService.UpdateItem(connection, 3, new Item(3, "super butter", 8, LocalDate.of(2002, 06, 03), 2));

		System.out.println("updated items :");
		System.out.println("item6: " + item6);
		System.out.println("item7: " + item7);

		Item item8 = dbService.DeleteItem(connection, 2);
		Item item9 = dbService.DeleteItem(connection, 4);

		System.out.println("Deleted items");
		System.out.println(item8);
		System.out.println(item9);
	}
	
	public static void checkLocationMethods(Connection connection) {
		LocationDBService dbService = new LocationDBService();

		Location location1 = new Location(1, "A", "111");
		Location location2 = new Location(2, "B", "123");
		Location location3 = new Location(3, "C", "222");

//		dbService.addLocation(connection, location1);
//		dbService.addLocation(connection, location2);
//		dbService.addLocation(connection, location3);

		Location location4 = dbService.getLocation(connection, 2);
		Location location5 = dbService.getLocation(connection, 4);
		
		System.out.println("some locations from DB");
		System.out.println(location4);
		System.out.println(location5);

		Location location6 = dbService.UpdateLocation(connection, 5, location2);
		Location location7 = dbService.UpdateLocation(connection, 3, new Location(3, "D", "567"));

		System.out.println("updated locations :");
		System.out.println("location6: " + location6);
		System.out.println("location7: " + location7);

		Location location8 = dbService.DeleteLocation(connection, 2);
		Location location9 = dbService.DeleteLocation(connection, 4);

		System.out.println("Deleted items");
		System.out.println(location8);
		System.out.println(location9);
	}
	
	public static void checkItemLocationMethods(Connection connection) {

		ItemLocation Itemlocation1 = new ItemLocation(1,3);
		ItemLocation Itemlocation2 = new ItemLocation(3,1);


		ItemLocationDBService.addItemLocation(connection, Itemlocation1);
//		ItemLocationDBService.addItemLocation(connection, Itemlocation2);

		ItemLocation Itemlocation4 = ItemLocationDBService.getByItemID(connection, 1);
		ItemLocation Itemlocation5 = ItemLocationDBService.getByItemID(connection, 2);
		
		System.out.println("some itemlocations from DB");
		System.out.println(Itemlocation4);
		System.out.println(Itemlocation5);

//		ItemLocation Itemlocation6 = ItemLocationDBService.UpdateByItemID(connection,1,new ItemLocation(1,1));
//		ItemLocation Itemlocation7 = ItemLocationDBService.UpdateByItemID(connection,5,new ItemLocation(5,3));
//
//		System.out.println("updated locations :");
//		System.out.println("location6: " + Itemlocation6);
//		System.out.println("location7: " + Itemlocation7);
//
//		ItemLocation Itemlocation8 = ItemLocationDBService.DeleteByItemID(connection, 1);
//
//		System.out.println("Deleted items");
//		System.out.println(Itemlocation8);
	}

}
