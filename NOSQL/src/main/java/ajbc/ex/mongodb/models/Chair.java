package ajbc.ex.mongodb.models;

import org.bson.types.ObjectId;

import com.google.gson.annotations.SerializedName;

public class Chair {

	@SerializedName("_id")
	private ObjectId id;
	private String manufacturer;
	private String modelName;
	private boolean isStool;
	private double price;
	private Measurment measurment;

	public Chair(ObjectId id, String manufacturer, String modelName, boolean isStool, double price,
			Measurment measurment) {
		this.id = id;
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.isStool = isStool;
		this.price = price;
		this.measurment = measurment;
	}

	public Chair(String manufacturer, String modelName, boolean isStool, double price, Measurment measurment) {
		this.manufacturer = manufacturer;
		this.modelName = modelName;
		this.isStool = isStool;
		this.price = price;
		this.measurment = measurment;
	}

	public Chair() {
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public boolean isStool() {
		return isStool;
	}

	public void setStool(boolean isStool) {
		this.isStool = isStool;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Measurment getMeasurment() {
		return measurment;
	}

	public void setMeasurment(Measurment measurment) {
		this.measurment = measurment;
	}

	@Override
	public String toString() {
		return "Chair [id=" + id + ", manufacturer=" + manufacturer + ", modelName=" + modelName + ", isStool="
				+ isStool + ", price=" + price + ", measurment=" + measurment + "]";
	}

}
