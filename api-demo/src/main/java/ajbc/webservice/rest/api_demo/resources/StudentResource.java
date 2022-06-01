package ajbc.webservice.rest.api_demo.resources;

import java.net.URI;
import java.util.List;

import ajbc.webservice.rest.api_demo.DBservice.StudentDBService;
import ajbc.webservice.rest.api_demo.beans.StudentFilterBean;
import ajbc.webservice.rest.api_demo.models.Course;
import ajbc.webservice.rest.api_demo.models.Student;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.ResponseBuilder;
import jakarta.ws.rs.core.Response.Status;
import jakarta.ws.rs.core.UriInfo;
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("students")
public class StudentResource {
	
	StudentDBService studentsDB = new StudentDBService();
	
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	public List<Student> getAllStudents() {
//		return studentsDB.getAllStudents();
//	}
	
	@GET
	@Path("/{id}")
	public Student getStudentByID(@PathParam("id") long id) {
		return studentsDB.getStudentByID(id);
	}
	
//	// add new student
//	@POST
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Produces(MediaType.APPLICATION_JSON)
//	public Student addStudent(Student student) {
//		return studentsDB.addStudent(student);
//	}
	
	// add new student
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response addStudent(Student student, @Context UriInfo uriInfo) {
		Status status;
		URI uri = null;
		Student newStudent = studentsDB.addStudent(student);
		if(newStudent==null)
			status = Status.NOT_MODIFIED;
		else {
			status = Status.CREATED;
			uri = uriInfo.getAbsolutePathBuilder().path(""+newStudent.getID()).build(newStudent);
		}
		return Response.status(status).entity(newStudent).location(uri).build();
	}
	
	// updating an existing student
	@PUT
	@Path("/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Student updateStudent(@PathParam("id") long id,Student student) {
		return studentsDB.updateStudent(id, student);
	}
	
	// delete student from map
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Student deleteStudent(@PathParam("id") long id) {
		return studentsDB.deleteStudent(id);
	}
	
//	@GET
//	public List<Student> getStudentByAvarage(@QueryParam("minaAvarage") double minAvarage,@QueryParam("maxAvarage") double maxAvarage){
//		if(minAvarage>0 && maxAvarage>0)
//			return studentsDB.getSudentsByAvarage(minAvarage,maxAvarage);
//		return studentsDB.getAllStudents();
//	}
//	
//	@GET
//	public List<Student> getStudentByAvarage(@QueryParam("avarage") double avarage){
//		if(avarage >0)
//			return studentsDB.getSudentsByAvarage(avarage);
//		return studentsDB.getAllStudents();
//	}
	
	@GET
	public List<Student> getStudentByAvarage(@BeanParam StudentFilterBean studentsFilter){
		if(studentsFilter.getMinAvarage() >0 && studentsFilter.getMaxAvarage()>0)
			return studentsDB.getSudentsByAvarage(studentsFilter.getAvarage());
		return studentsDB.getAllStudents();
	}	
	
	// link to another resource
	@Path("/{id}/courses")
	public StudentCourseResource getCoursesOfStudents(@PathParam("id") long id) {
		return new StudentCourseResource();
	}
	
	
}
