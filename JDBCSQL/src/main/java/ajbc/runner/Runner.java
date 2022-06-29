package ajbc.runner;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import ajbc.dbservices.EmployeesDBService;
import ajbc.models.Employee;
import ajbc.utils.ConnectionBuilder;

public class Runner {
	
	public static void main(String[] args) {
		final String PROPERTIES_FILE = "demo.properties";
		
		try(Connection connection = new ConnectionBuilder(PROPERTIES_FILE).createConnection()){
			System.out.println("connected");
			
			EmployeesDBService dbService = new EmployeesDBService();
			
//			Employee emp1 = new Employee("pekarsky", "kety", "ket@mail.com", "Sleep", 10000);
//			Employee emp2 = new Employee("kravtzov", "dani", "dan@mail.com", "Food", 10000);
//			Employee emp3 = new Employee("pekarsky", "Nikol", "nik@mail.com", "Health", 20000);
//			
//			dbService.addEmployee(connection, emp1);
//			dbService.addEmployee(connection, emp2);
//			dbService.addEmployee(connection, emp3);
			
			Employee emp4 = dbService.getEmployee(connection, 1011);
			Employee emp5 = dbService.getEmployee(connection, 1014);
			
			System.out.println("some employees from DB");
			System.out.println(emp4);
			System.out.println(emp5);
			
//			Employee emp6 = dbService.UpdateEmployee(connection, 1011,emp4);
//			Employee emp7 = dbService.UpdateEmployee(connection, 1014,new Employee("pekarsky", "Nikol", "nik@mail.com", "Food", 20000));
//			
//			System.out.println("updated employees ");
//			System.out.println(emp6);
//			System.out.println(emp7);
			
//			System.out.println("Deleted empolyees");
//			Employee emp8 = dbService.DeleteEmployee(connection, 1099);
//			Employee emp9 = dbService.DeleteEmployee(connection, 1012);
//			
//			System.out.println(emp8);
//			System.out.println(emp9);
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
	}

}
