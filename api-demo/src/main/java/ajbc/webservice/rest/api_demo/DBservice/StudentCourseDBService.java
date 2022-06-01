package ajbc.webservice.rest.api_demo.DBservice;

import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.MyDB;
import ajbc.webservice.rest.api_demo.exceptions.MissingDataException;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;

public class StudentCourseDBService {
	
	private MyDB studentsDB;
	private Map<Long,Student> students;
	private Map<Long,Course> courses;
	
	public StudentCourseDBService() {
		studentsDB = MyDB.getInstance();
		students = studentsDB.getStudents();
	}

	public List<Course> getCoursesOfStudent(long id) {
		List<Course> studentCourses = null;
		Student student = students.get(id);
		if(student == null)
			throw new MissingDataException("student is not in the DB"+ id);
		studentCourses = students.get(id).getCourses();
		return students.get(id).getCourses();
	}

}
