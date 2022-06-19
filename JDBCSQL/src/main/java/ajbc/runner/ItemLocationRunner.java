package ajbc.runner;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

			List<Item> items = Arrays.asList(
					new Item("cheeze", 5, LocalDate.of(2022, 06, 15), 4),
					new Item("coffee", 10, LocalDate.of(2022, 05, 04), 10),
					new Item("water", 15, LocalDate.of(2022, 06, 12), 6),
					new Item("bread", 23, LocalDate.of(2022, 06, 12), 7),
					new Item("butter", 44, LocalDate.of(2022, 06, 12), 5));

			List<Location> locations = Arrays.asList(
					new Location("A", "123"),
					new Location("B", "345"),
					new Location("C", "532"),
					new Location("D", "458"),
					new Location("E", "598"));
			

			ItemDBService itemDBService = new ItemDBService();
			LocationDBService locationDBService = new LocationDBService();
			ItemLocationDBService itemLocationDBService = new ItemLocationDBService();
			
			// Add one item to DB
			itemDBService.addItem(connection, new Item("candy bar", 2, LocalDate.of(2022, 06, 15), 23));
			
			// Add one location to DB
			locationDBService.addLocation(connection, new Location("K", "111"));
			
			// Add item-location to DB
			itemLocationDBService.addItemLocation(connection, new ItemLocation(1,1));
			
			// Add list of items to DB
			List<Item> addedItems = itemDBService.addItems(connection, items);
			System.out.println("Items added to DB : ");
			addedItems.forEach(System.out::println);
			
			// Add list of Locations to DB
			List<Location> addedLocations = locationDBService.addLocationsList(connection, locations);
			System.out.println("Locations added to DB : ");
			addedLocations.forEach(System.out::println);
			
			// Add list of items-locations to DB
			List<ItemLocation> addedItemsLocations  = itemLocationDBService.addItemLocationsList(connection, addedItems, addedLocations);
			System.out.println("Items - locations added to DB : ");
			addedItemsLocations.forEach(System.out::println);
			

//			System.out.println("---Added items---");
//			items = dbServiceItems.addItems(connection, items);
//			items.forEach(System.out::println);
//			
//			System.out.println("---Added locations---");
//			locations = dbServiceLocation.addLocationsList(connection, locations);
//			locations.forEach(System.out::println);

//			List<Item> itemsFromDB = new ArrayList<Item>();
//			itemsFromDB.add(dbServiceItems.getItem(connection, 13));
//			itemsFromDB.add(dbServiceItems.getItem(connection, 14));
//			itemsFromDB.add(dbServiceItems.getItem(connection, 15));
//
//			List<Location> locationsFromDB = new ArrayList<Location>();
//			locationsFromDB.add(dbServiceLocation.getLocation(connection, 4));
//			locationsFromDB.add(dbServiceLocation.getLocation(connection, 5)); 
//			locationsFromDB.add(dbServiceLocation.getLocation(connection, 6));

//			System.out.println("---Added Itemlocations---");
//			List<ItemLocation> itemLocations = dbServiceItemLocation.addItemLocationsList(connection,itemsFromDB,locationsFromDB);
//			itemLocations.forEach(System.out::println);

			// Updating list of items in DB 
//			itemsFromDB.forEach(item -> item.setQuantity(17));
//			itemsFromDB.add(new Item(55,"candy", 5, LocalDate.of(2022, 06, 15), 4));
//			System.out.println("---Updated items list---");
//			List<Item> updatedItems = dbServiceItems.UpdateItemsList(connection, itemsFromDB);
//			updatedItems.forEach(System.out::println);
			
			// Updating list of locations in DB 
//			locationsFromDB.forEach(location -> location.setAccessCode(location.getAccessCode() + "KK"));
//			locationsFromDB.add(new Location(77, "G", "532"));
//			System.out.println("---Updated locations list---");
//			List<Location> updatedLocations = dbServiceLocation.UpdateLocationsList(connection, locationsFromDB);
//			updatedLocations.forEach(System.out::println);
			
			// Updating list of items locations in DB 
//			List<ItemLocation> itemsLocations = new ArrayList<ItemLocation>();
//			itemsLocations.add(itemLocationDBService.getByItemID(connection, 13));
//			itemsLocations.add(itemLocationDBService.getByItemID(connection, 14));
//			itemsLocations.add(itemLocationDBService.getByItemID(connection, 15));
//			locationsFromDB.forEach(location -> location.setAccessCode(location.getAccessCode() + "KK"));
//			locationsFromDB.add(new Location(77, "G", "532"));
//			System.out.println("---Updated locations list---");
//			List<Location> updatedLocations = dbServiceLocation.UpdateLocationsList(connection, locationsFromDB);
//			updatedLocations.forEach(System.out::println);

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

//		ItemLocation Itemlocation1 = new ItemLocation(1, 3);
//		ItemLocation Itemlocation2 = new ItemLocation(3, 1);
//
//		ItemLocationDBService.addItemLocation(connection, Itemlocation1);
////		ItemLocationDBService.addItemLocation(connection, Itemlocation2);
//
//		ItemLocation Itemlocation4 = ItemLocationDBService.getByItemID(connection, 1);
//		ItemLocation Itemlocation5 = ItemLocationDBService.getByItemID(connection, 2);
//
//		System.out.println("some itemlocations from DB");
//		System.out.println(Itemlocation4);
//		System.out.println(Itemlocation5);

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
