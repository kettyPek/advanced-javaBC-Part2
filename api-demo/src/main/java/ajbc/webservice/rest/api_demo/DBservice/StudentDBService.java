package ajbc.webservice.rest.api_demo.DBservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.models.Course;
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
		if(students.get(student.getID())!=null)
			return null;
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
	
	
	// get list of students with avarage higher than given avarage
		public List<Student> getSudentsByAvarage(double avarage){
			return students.values().stream().filter(s -> s.getAverage()>avarage).collect(Collectors.toList());
		}
		
	// get list of students by avarage in range
	public List<Student> getSudentsByAvarage(double minAvarage, double maxAvarage){
		return students.values().stream().filter(s -> s.getAverage()>=minAvarage && s.getAverage()<=maxAvarage).collect(Collectors.toList());
	}
}
