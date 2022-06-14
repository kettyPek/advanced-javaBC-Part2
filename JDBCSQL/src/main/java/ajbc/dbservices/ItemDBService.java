package ajbc.dbservices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
		if (item != null) {
			String query = "delete from Item where id=?";
			try (PreparedStatement statement = connection.prepareStatement(query);) {
				statement.setInt(1, id);
				ItemLocationDBService.DeleteByItemID(connection, id);
				int rowsAffected = statement.executeUpdate();
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return item;
	}

}
