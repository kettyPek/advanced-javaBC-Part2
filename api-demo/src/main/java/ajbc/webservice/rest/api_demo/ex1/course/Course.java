package ajbc.webservice.rest.api_demo.ex1.course;

import java.util.ArrayList;
import java.util.List;

import ajbc.webservice.rest.api_demo.models.Student;

public class Course {
	
	private static int counter = 1000;
	private final long COURSE_NUMBER;
	private String name;
	private List<Student> students;
	
	public Course() {
		COURSE_NUMBER = generateCourseNumber();
	}
	
	public Course(String name) {
		COURSE_NUMBER = generateCourseNumber();
		this.name = name;
		this.students = new ArrayList<Student>();
	}
	
	private synchronized long generateCourseNumber() {
		return counter++;
	}
	
	public long getCourseNumber() {
		return COURSE_NUMBER;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	@Override
	public String toString() {
		return "Course [COURSE_NUMBER=" + COURSE_NUMBER + ", name=" + name + ", students=" + students + "]";
	}	

}
