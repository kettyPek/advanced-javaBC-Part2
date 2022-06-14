package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ajbc.models.ItemLocation;

public class ItemLocationDBService {

	public static void addItemLocation(Connection connection, ItemLocation itemLocation) {

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

	public static ItemLocation getByItemID(Connection connection, int itemID) {

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

	public static ItemLocation UpdateByItemID(Connection connection,int itemID, ItemLocation itemLocation) {

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

	public static ItemLocation DeleteByItemID(Connection connection, int itemID) {
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
	
	public static ItemLocation DeleteByLocationID(Connection connection, int locationID) {
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

}
