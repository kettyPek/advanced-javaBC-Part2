package ajbc.models;

import java.util.Objects;

public class Employee {

	private long id;
	private String lastName;
	private String firstName;
	private String email;
	private String department;
	private float salary;

	public Employee(String lastName, String firstName, String email, String department, float salary) {
		setLastName(lastName);
		setFirstName(firstName);
		setEmail(email);
		setDepartment(department);
		setSalary(salary);
	}

	public Employee() {

	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public float getSalary() {
		return salary;
	}

	public void setSalary(float salary) {
		this.salary = salary;
	}

	public void setID(long id) {
		this.id = id;
	}

	public long getID() {
		return id;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", firstName=" + firstName + ", email=" + email
				+ ", department=" + department + ", salary=" + salary + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		return Objects.equals(department, other.department) && Objects.equals(email, other.email)
				&& Objects.equals(firstName, other.firstName) && id == other.id
				&& Objects.equals(lastName, other.lastName)
				&& Float.floatToIntBits(salary) == Float.floatToIntBits(other.salary);
	}
	
	

}
