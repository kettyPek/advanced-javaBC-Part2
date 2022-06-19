package ajbc.models;

import java.util.Objects;

public class Location {
	
	private int id;
	private String name;
	private String AccessCode;
	
	public Location(int id, String name, String accessCode) {
		this.id = id;
		this.name = name;
		AccessCode = accessCode;
	}

	public Location() {
	}

	public Location(String name, String accessCode) {
		this(0, name, accessCode);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAccessCode() {
		return AccessCode;
	}

	public void setAccessCode(String accessCode) {
		AccessCode = accessCode;
	}

	@Override
	public String toString() {
		return "Location [id=" + id + ", name=" + name + ", AccessCode=" + AccessCode + "]";
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		return Objects.equals(AccessCode, other.AccessCode) && id == other.id && Objects.equals(name, other.name);
	}
	
	
	
	

}
