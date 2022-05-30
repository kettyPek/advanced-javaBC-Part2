package ajbc.webservice.rest.api_demo.DBservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.models.Student;

public class StudentDBService {
	
	private MyDB db;
	private Map<Long,Student> students;
	
	public StudentDBService() {
		db = MyDB.getInstance();
		students = db.getStudents();
	}
	
	// returns all the students from the DB as a list
	public List<Student> getAllStudents(){
		return new ArrayList<Student>(students.values());
	}
	
	public Student getStudentByID(long id) {
		return students.get(id);
	}
	
	public Student addStudent(Student student) {
		students.put(student.getID(), student);
		return student;
	}
	
	public Student updateStudent(long id, Student student) {
		if(students.containsKey(id)) {
			Student current = students.get(id);
			current.setAverage(student.getAverage());
			current.setFirstName(student.getFirstName());
			current.setLastName(student.getLastName());
			
			students.put(id, current);
			return  current;
		}
		return null;
	}

	public Student deleteStudent(long id) {
		return students.remove(id);
	}


}
