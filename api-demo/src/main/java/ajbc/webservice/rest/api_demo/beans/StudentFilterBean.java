package ajbc.webservice.rest.api_demo.beans;

import jakarta.ws.rs.QueryParam;

public class StudentFilterBean {
	
	@QueryParam("avarage") double avarage;
	@QueryParam("minaAvarage") double minAvarage;
	@QueryParam("maxAvarage") double maxAvarage;
	

	public double getAvarage() {
		return avarage;
	}
	public void setAvarage(double avarage) {
		this.avarage = avarage;
	}
	public double getMinAvarage() {
		return minAvarage;
	}
	public void setMinAvarage(double minAvarage) {
		this.minAvarage = minAvarage;
	}
	public double getMaxAvarage() {
		return maxAvarage;
	}
	public void setMaxAvarage(double maxAvarage) {
		this.maxAvarage = maxAvarage;
	}
	
	

}
