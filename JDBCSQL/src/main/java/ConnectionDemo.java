import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionDemo {

	public static void main(String[] args) {
		
		String conStr = "jdbc:sqlserver://localhost:1433;databaseName=JDBC-DEMO;servername=LAPTOP-3FJBAUJT;encrypt=false;";
		try (Connection connection = DriverManager.getConnection(conStr,"ketty","050296");){
			if(connection.isValid(5))
				System.out.println("Connected to DB");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
