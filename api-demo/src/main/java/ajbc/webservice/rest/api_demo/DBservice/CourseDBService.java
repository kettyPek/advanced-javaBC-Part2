package ajbc.webservice.rest.api_demo.DBservice;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import ajbc.webservice.rest.api_demo.DB.CoursesDB;
import ajbc.webservice.rest.api_demo.models.Course;

public class CourseDBService {
	
	private CoursesDB db;
	private Map<Long,Course> courses;
	
	public CourseDBService() {
		db = CoursesDB.getInstance();
		courses = db.getCourses();
	}

	public List<Course> getAllCourses() {
		return new ArrayList<Course>(courses.values());
	}

	public Course getCourseByNumber(long courseNumber) {
		return courses.get(courseNumber);
	}

	public Course addCourse(Course course) {
		return courses.put(course.getCourseNumber(), course);
	}

	public Course updateCourse(long courseNumber, Course course) {
		if(courses.containsKey(courseNumber)) {
			Course current = courses.get(courseNumber);
			current.setName(course.getName());
			current.setStudents(course.getStudents());
			
			courses.put(courseNumber, current);
			return  current;
		}
		return null;
	}

	public Course deleteCourse(long courseNumber) {
		return courses.remove(courseNumber);
	}

}
