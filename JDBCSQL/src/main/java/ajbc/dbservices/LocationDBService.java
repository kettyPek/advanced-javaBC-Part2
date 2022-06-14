package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import ajbc.models.Location;

public class LocationDBService {

	public void addLocation(Connection connection, Location location) {
		
		String query = "Insert Into Location (locationName, AccessCode) values(?, ?)";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setString(1, location.getName());
			statement.setString(2, location.getAccessCode());
			int rowsAffected = statement.executeUpdate();
			System.out.println("Success ! " + rowsAffected + " rows affected");

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Location getLocation(Connection connection, int id) {

		String query = "select * from Location where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Location location = new Location();
				location.setId(resultSet.getInt(1));
				location.setName(resultSet.getString(2));
				location.setAccessCode(resultSet.getString(3));
				return location;
			}
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Location UpdateLocation(Connection connection, int id, Location location) {

		Location locationFromDB = getLocation(connection, id);
		if  (locationFromDB!=null && !locationFromDB.equals(location)) {
			String query = "update Location set locationName=?, AccessCode=? where id=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setString(1, location.getName());
				statement.setString(2, location.getAccessCode());
				statement.setInt(3, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println("Success ! " + rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return locationFromDB;
	}

	public Location DeleteLocation(Connection connection, int id) {
		Location location = getLocation(connection, id);
		if (location != null) {
			String query = "delete from Location where id=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, id);
				ItemLocationDBService.DeleteByLocationID(connection, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return location;
	}

}
