package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import ajbc.models.Item;
import ajbc.models.ItemLocation;
import ajbc.models.Location;

public class ItemLocationDBService {

	public void addItemLocation(Connection connection, ItemLocation itemLocation) {

		String query = "Insert Into ItemLocation (ItemId, LocationId) values(?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, itemLocation.getItemID());
			statement.setInt(2, itemLocation.getLocationID());
			int rowsAffected = statement.executeUpdate();
			System.out.println("Success ! " + rowsAffected + " rows affected");

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ItemLocation getByItemID(Connection connection, int itemID) {

		String query = "select * from ItemLocation where ItemId = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, itemID);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				ItemLocation itemLocation = new ItemLocation();
				itemLocation.setItemID(resultSet.getInt(1));
				itemLocation.setLocationID(resultSet.getInt(2));
				return itemLocation;
			}

			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public ItemLocation UpdateByItemID(Connection connection, int itemID, ItemLocation itemLocation) {

		ItemLocation itemLocationFromDB = getByItemID(connection, itemID);
		if (itemLocationFromDB != null && !itemLocationFromDB.equals(itemLocation)) {
			String query = "update ItemLocation set LocationId=? where ItemId=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, itemLocation.getLocationID());
				statement.setInt(2, itemID);
				int rowsAffected = statement.executeUpdate();
				System.out.println("Success ! " + rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemLocationFromDB;
	}

	public ItemLocation DeleteByItemID(Connection connection, int itemID) {
		ItemLocation itemLocation = getByItemID(connection, itemID);
		if (itemLocation != null) {
			String query = "delete from ItemLocation where ItemId=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, itemID);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemLocation;
	}

	public ItemLocation DeleteByLocationID(Connection connection, int locationID) {
		ItemLocation itemLocation = getByItemID(connection, locationID);
		if (itemLocation != null) {
			String query = "delete from ItemLocation where LocationId=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, locationID);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemLocation;
	}

	public List<ItemLocation> addItemLocationsList(Connection connection, List<Item> items, List<Location> locations) {

		String query = "Insert Into ItemLocation (ItemId, LocationId) values(?, ?)";
		if (items.size() == locations.size()) {
			List<ItemLocation> itemLocations = new ArrayList<ItemLocation>();
			try (PreparedStatement preparedStatement = connection.prepareStatement(query);) {
				connection.setAutoCommit(false);
				for (int i=0; i<items.size(); i++) {
					preparedStatement.setInt(1, items.get(i).getId());
					preparedStatement.setInt(2, locations.get(i).getId());

					preparedStatement.addBatch();
					itemLocations.add(new ItemLocation(items.get(i).getId(), locations.get(i).getId()));
				}

				int[] affectedRecords = preparedStatement.executeBatch();
			    for(var record : affectedRecords)
			    	if(record==0)
			    		throw new SQLException("Data invalid, transanction was not completed");
			    connection.commit();
				return itemLocations;

			} catch (SQLException e) {
				if(connection != null) {
					try {
						connection.rollback();
					}catch(SQLException e2) {
						e2.printStackTrace();
					}
				}
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public List<ItemLocation> UpdateLocationsList(Connection connection, List<ItemLocation> itemslocations){
		
		String query = "update ItemLocation set LocationId=? where ItemId=?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
			connection.setAutoCommit(false);
			ItemLocation itemLocationFromDB ; 
		    for(var itemLocation : itemslocations) {
		    	itemLocationFromDB = getByItemID(connection, itemLocation.getItemID());
		    	if (itemLocationFromDB != null && !itemLocationFromDB.equals(itemLocation)) {
					try (PreparedStatement statement = connection.prepareStatement(query);) {
						statement.setInt(1, itemLocation.getLocationID());
						statement.setInt(2, itemLocation.getLocationID());
						int rowsAffected = statement.executeUpdate();
						System.out.println("Success ! " + rowsAffected + " rows affected");
					} catch (SQLException e) {
						e.printStackTrace();
					}
				}
		    }
		    int[] affectedRecords = preparedStatement.executeBatch();
		    for(var record : affectedRecords)
		    	if(record==0)
		    		throw new SQLException("Data invalid, transanction was not completed");
		    connection.commit();

		} catch (SQLException e) {
			if(connection != null) {
				try {
					connection.rollback();
				}catch(SQLException e2) {
					e2.printStackTrace();
				}
			}
			e.printStackTrace();
		}
		return null;
	}

}
