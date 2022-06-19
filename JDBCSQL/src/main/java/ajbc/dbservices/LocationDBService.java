package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import ajbc.models.Item;
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
		ItemLocationDBService dbService = new ItemLocationDBService();
		if (location != null) {
			String query = "delete from Location where id=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, id);
				dbService.DeleteByLocationID(connection, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return location;
	}
	
	public List<Location> addLocationsList(Connection connection, List<Location> locations){
		
		String query = "Insert Into Location (locationName, AccessCode) values(?, ?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
			connection.setAutoCommit(false);
		    int lastID = getLastId(connection);  
		    for(var location : locations) {
		    	preparedStatement.setString(1, location.getName());
		    	preparedStatement.setString(2, location.getAccessCode());

		        preparedStatement.addBatch();
		        lastID++;
		        location.setId(lastID);
		    }

		    int[] affectedRecords = preparedStatement.executeBatch();
		    for(var record : affectedRecords)
		    	if(record==0)
		    		throw new SQLException("Data invalid, transanction was not completed");
		    connection.commit();
		    return locations;

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
	
	public List<Location> UpdateLocationsList(Connection connection, List<Location> locations){
		
		String query = "update Location set locationName=?, AccessCode=? where id=?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
			connection.setAutoCommit(false);
		    Location locationFromDB ; 
		    for(var location : locations) {
		    	locationFromDB = getLocation(connection, location.getId());
		    	if  (locationFromDB!=null && !locationFromDB.equals(location)) {
		    		preparedStatement.setString(1, location.getName());
		    		preparedStatement.setString(2, location.getAccessCode());
		    		preparedStatement.setInt(3, location.getId());
		    		
		    		preparedStatement.addBatch();
		    	}
		    	else {
		    		location = null;
		    	}
		    }
		    int[] affectedRecords = preparedStatement.executeBatch();
		    for(var record : affectedRecords)
		    	if(record==0)
		    		throw new SQLException("Data invalid, transanction was not completed");
		    connection.commit();
		    return locations;

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
	
	private int getLastId(Connection connection) {
		int lastID = 0;
		String query = "SELECT IDENT_Current('Location')";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) 
				lastID = resultSet.getInt(1);
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lastID;
	}

}
