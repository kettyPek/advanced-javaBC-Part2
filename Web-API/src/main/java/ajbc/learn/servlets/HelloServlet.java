package ajbc.learn.servlets;

import javax.servlet.http.HttpServlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HelloServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@RequestMapping("/sayhello")
	public String sayHello() {
		System.out.println("in");
		return "hello from server";
	}
}
