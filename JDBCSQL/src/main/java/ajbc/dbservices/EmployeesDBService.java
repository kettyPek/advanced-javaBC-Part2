package ajbc.dbservices;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ajbc.models.Employee;

public class EmployeesDBService {

	public void addEmployee(Connection connection, Employee employee) {

		try (Statement statement = connection.createStatement();) {

			String query = "Insert Into Employees\r\n" + "(lastName, firstName, email, department, salary) "
					+ "values('" + employee.getLastName() + "','" + employee.getFirstName() + "','"
					+ employee.getEmail() + "','" + employee.getDepartment() + "'," + employee.getSalary() + ")";

			int rowsAffected = statement.executeUpdate(query);
			System.out.println("Success ! " + rowsAffected + " rows affected");

			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee(Connection connection, long id) {
		String query = "select * from employees where id =" + id;
		
		try (Statement statement = connection.createStatement(); ResultSet resultSet = statement.executeQuery(query);) {
			if (resultSet.next()) {
				Employee emp = new Employee();
				emp.setID(resultSet.getLong(1));
				emp.setLastName(resultSet.getString(2));
				emp.setFirstName(resultSet.getString(3));
				emp.setEmail(resultSet.getString(4));
				emp.setDepartment(resultSet.getString(5));
				emp.setSalary(resultSet.getFloat(6));
				return emp;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Employee UpdateEmployee(Connection connection, long id, Employee emp) {

		Employee empFromDB = getEmployee(connection, id);
		if (empFromDB!=null && !emp.equals(empFromDB)) {
			try (Statement statement = connection.createStatement();) {
				String query = "update employees set lastName='" + emp.getLastName() + "', firstName='"
						+ emp.getFirstName() + "', email='" + emp.getEmail() + "', department='" + emp.getDepartment()
						+ "', salary='" + emp.getSalary() + "' where id=" + id;

				int rowsAffected = statement.executeUpdate(query);
				System.out.println("Success ! " + rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;
	}
	
	public Employee DeleteEmployee(Connection connection, long id) {
		Employee emp = getEmployee(connection,id);
		if(emp!=null) {
			try (Statement statement = connection.createStatement();) {
				String query = "delete from employees where id="+id;

				int rowsAffected = statement.executeUpdate(query);
				System.out.println(rowsAffected + " rows affected");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return emp;
	}

}
