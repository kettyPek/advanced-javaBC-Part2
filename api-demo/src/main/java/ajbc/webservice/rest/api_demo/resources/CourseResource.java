package ajbc.webservice.rest.api_demo.resources;

import java.util.List;

import ajbc.webservice.rest.api_demo.DBservice.CourseDBService;
import ajbc.webservice.rest.api_demo.models.Course;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("courses")
public class CourseResource {
	
	CourseDBService coursesDB = new CourseDBService();
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<Course> getAllCourses(){
		return coursesDB.getAllCourses();
	}
	
	@GET
	@Path("/{courseNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course getCourseByNumber(@PathParam("courseNumber") long courseNumber){
		return coursesDB.getCourseByNumber(courseNumber);
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Course addNewCourse(Course course) {
		return coursesDB.addCourse(course);
	}
	
	@PUT
	@Path("/{courseNumber}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Course updateStudent(@PathParam("courseNumber") long courseNumber,Course course) {
		return coursesDB.updateCourse(courseNumber, course);
	}
	
	@DELETE
	@Path("/{courseNumber}")
	@Produces(MediaType.APPLICATION_JSON)
	public Course deleteCourse(@PathParam("courseNumber") long courseNumber) {
		return coursesDB.deleteCourse(courseNumber);
	}

}
