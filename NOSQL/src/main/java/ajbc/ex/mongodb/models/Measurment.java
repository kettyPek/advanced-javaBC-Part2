package ajbc.ex.mongodb.models;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public class Measurment {

	@SerializedName("_id")
	private ObjectId id;
	private double height;
	private double width;
	private double depth;

	public Measurment(ObjectId id, double height, double width, double depth) {
		this.id = id;
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	public Measurment(double height, double width, double depth) {
		this.height = height;
		this.width = width;
		this.depth = depth;
	}

	public Measurment() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWidth() {
		return width;
	}

	public void setWidth(double width) {
		this.width = width;
	}

	public double getDepth() {
		return depth;
	}

	public void setDepth(double depth) {
		this.depth = depth;
	}

	@Override
	public String toString() {
		return "Measurment [id=" + id + ", height=" + height + ", width=" + width + ", depth=" + depth + "]";
	}

}
