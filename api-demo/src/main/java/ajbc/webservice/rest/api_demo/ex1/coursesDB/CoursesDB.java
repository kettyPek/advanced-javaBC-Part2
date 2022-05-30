package ajbc.webservice.rest.api_demo.ex1.coursesDB;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import ajbc.webservice.rest.api_demo.ex1.course.Course;

public class CoursesDB {
	
	private static CoursesDB instance = null;
	private  Map<Long, Course> courses;

	public static synchronized CoursesDB getInstance() {
		if(instance==null)
			instance = new CoursesDB();
		return instance;
	}
	
	private CoursesDB() {
		courses = new HashMap<Long, Course>();
		seed(); 
	}

	private void seed() {
		List<Course> coursesList = Arrays.asList(
				new Course("Java"),
				new Course("Python"), 
				new Course("C")
		);
		
		courses = coursesList.stream()
	      .collect(Collectors.toMap(Course::getCourseNumber, Function.identity()));
	}
   
	public Map<Long, Course> getCourses(){
		return courses;
	}

}
