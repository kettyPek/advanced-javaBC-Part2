package ajbc.learn.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@NoArgsConstructor
@Getter
@Setter
public class Product {

	private Integer productId;
	private String productName;
	private Integer supplierId;
	private Integer categoryId;
	private String quantityPerUnit;
	private Double unitPrice;
	private Integer unitsInStock;
	private Integer unitsOnOrder;
	private Integer reorderLevel;
	private Integer discontinued;
	
	public Product(String productName, Integer supplierId, Integer categoryId, String quantityPerUnit, Double unitPrice,
			Integer unitsInStock, Integer unitsOnOrder, Integer reorderLevel, Integer discontinued) {
		this.productName = productName;
		this.supplierId = supplierId;
		this.categoryId = categoryId;
		this.quantityPerUnit = quantityPerUnit;
		this.unitPrice = unitPrice;
		this.unitsInStock = unitsInStock;
		this.unitsOnOrder = unitsOnOrder;
		this.reorderLevel = reorderLevel;
		this.discontinued = discontinued;
	}
	
	

}
