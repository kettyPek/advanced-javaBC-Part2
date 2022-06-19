package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ajbc.models.Item;

public class ItemDBService {

	public void addItem(Connection connection, Item item) {

		try (Statement statement = connection.createStatement();) {

			String query = "Insert Into Item (itemName, unitPrice, purchaseDate, quantity)"
					+ "values('%s', %s, '%tD',%d)".formatted(item.getName(), item.getUnitPrice(),
							item.getPurchaseDate(), item.getQuantity());
			System.out.println(query);
			int rowsAffected = statement.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Item getItem(Connection connection, int id) {

		String query = "select * from Item where id = ?";
		try (PreparedStatement statement = connection.prepareStatement(query);) {
			statement.setInt(1, id);
			ResultSet resultSet = statement.executeQuery();
			if (resultSet.next()) {
				Item item = new Item();
				item.setId(resultSet.getInt(1));
				item.setName(resultSet.getString(2));
				item.setUnitPrice(resultSet.getDouble(3));
				item.setPurchaseDate(resultSet.getDate(4).toLocalDate());
				item.setQuantity(resultSet.getInt(5));
				return item;
			}
			
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Item UpdateItem(Connection connection, int id, Item item) {

		Item itemFromDB = getItem(connection, id);
		if  (itemFromDB!=null && !itemFromDB.equals(item)) {
			String query = "update Item set itemName=?, unitPrice=?, purchaseDate=?, quantity=? where id=?";
			System.out.println(query);
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setString(1, item.getName());
				statement.setDouble(2, item.getUnitPrice());
				statement.setObject(3, item.getPurchaseDate());
				statement.setInt(4, item.getQuantity());
				statement.setInt(5, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println("Success ! " + rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return itemFromDB;
	}

	public Item DeleteItem(Connection connection, int id) {
		Item item = getItem(connection, id);
		ItemLocationDBService dbService = new ItemLocationDBService();
		if (item != null) {
			String query = "delete from Item where id=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, id);
				dbService.DeleteByItemID(connection, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return item;
	}
	
	public List<Item> addItems(Connection connection, List<Item> items){
		String query = "Insert Into Item (itemName, unitPrice, purchaseDate, quantity) values(?,?,?,?)";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
			connection.setAutoCommit(false);
		    int lastID = getLastId(connection);  
		    for(var item : items) {
		        preparedStatement.setString(1, item.getName());
		        preparedStatement.setDouble(2, item.getUnitPrice());
		        preparedStatement.setObject(3, item.getPurchaseDate());
		        preparedStatement.setInt(4, item.getQuantity());

		        preparedStatement.addBatch();
		        lastID++;
		        item.setId(lastID);
		    }
		    int[] affectedRecords = preparedStatement.executeBatch();
		    for(var record : affectedRecords)
		    	if(record==0)
		    		throw new SQLException("Data invalid, transanction was not completed");
		    connection.commit();
		    return items;
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
	
	public List<Item> UpdateItemsList(Connection connection, List<Item> items){
		
		String query = "update Item set itemName=?, unitPrice=?, purchaseDate=?, quantity=? where id=?";
		try(PreparedStatement preparedStatement = connection.prepareStatement(query);){
			connection.setAutoCommit(false);
		    Item itemFromDB ; 
		    for(int i=0; i<items.size(); i++) {
		    	itemFromDB = getItem(connection, items.get(i).getId());
		    	if  (itemFromDB!=null && !itemFromDB.equals(items.get(i))) {
		    		preparedStatement.setString(1, items.get(i).getName());
		    		preparedStatement.setDouble(2, items.get(i).getUnitPrice());
		    		preparedStatement.setObject(3, items.get(i).getPurchaseDate());
		    		preparedStatement.setInt(4, items.get(i).getQuantity());
		    		preparedStatement.setInt(5, items.get(i).getId());
		    		
		    		preparedStatement.addBatch();
		    	}
		    	else {
		    		items.set(i, null);
				}
			}
		    int[] affectedRecords = preparedStatement.executeBatch();
		    for(var record : affectedRecords)
		    	if(record==0)
		    		throw new SQLException("Data invalid, transanction was not completed");
		    connection.commit();
			return items;
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
		String query = "SELECT IDENT_Current('Item')";
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
