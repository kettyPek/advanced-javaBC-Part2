package ajbc.learn.mongodb.models;

import java.util.List;

import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

public class Movie {

	private ObjectId id;

	private int year;
	private List<String> cast;
	private String title;
	@BsonProperty(value = "num_mflix_comments")
	private int numOfComments;

	public Movie(ObjectId id, int year, List<String> cast, String title, int numOfComments) {
		this.id = id;
		this.year = year;
		this.cast = cast;
		this.title = title;
		this.numOfComments = numOfComments;
	}

	public Movie(int year, List<String> cast, String title, int numOfComments) {
		super();
		this.year = year;
		this.cast = cast;
		this.title = title;
		this.numOfComments = numOfComments;
	}

	public Movie() {

	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public List<String> getCast() {
		return cast;
	}

	public void setCast(List<String> cast) {
		this.cast = cast;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getNumOfComments() {
		return numOfComments;
	}

	public void setNumOfComments(int numOfComments) {
		this.numOfComments = numOfComments;
	}

	@Override
	public String toString() {
		return "Movie [id=" + id + ", year=" + year + ", cast=" + cast + ", title=" + title + ", numOfComments="
				+ numOfComments + "]";
	}

}
