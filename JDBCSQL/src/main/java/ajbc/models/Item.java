package ajbc.models;

import java.time.LocalDate;
import java.util.Objects;

public class Item {
	
	private int id;
	private String name;
	private double unitPrice;
	private LocalDate  purchaseDate;
	private int quantity;
	
	public Item(int id, String name, double unitPrice, LocalDate purchaseDate, int quantity) {
		setId(id);
		setName(name);
		setUnitPrice(unitPrice);
		setPurchaseDate(purchaseDate);
		setQuantity(quantity);
	}

	public Item() {
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

	public double getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(double unitPrice) {
		this.unitPrice = unitPrice;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", unitPrice=" + unitPrice + ", purchaseDate=" + purchaseDate
				+ ", quantity=" + quantity + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Item other = (Item) obj;
		return id == other.id && Objects.equals(name, other.name) && Objects.equals(purchaseDate, other.purchaseDate)
				&& quantity == other.quantity
				&& Double.doubleToLongBits(unitPrice) == Double.doubleToLongBits(other.unitPrice);
	}
	
	
	
	
	
	
	
	

}
