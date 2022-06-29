package ajbc.learn.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Component(value = "jdbcDao")
@Getter
@Setter
@NoArgsConstructor
public class JdbcProductDao implements ProductDao {

	@Autowired
	private JdbcTemplate template;

	RowMapper<Product> rowMapper = (rs, rowNum) -> {
		Product prod = new Product();
		prod.setProductId(rs.getInt(1));
		prod.setProductName(rs.getString(2));
		prod.setSupplierId(rs.getInt(3));
		prod.setCategoryId(rs.getInt(4));
		prod.setQuantityPerUnit(rs.getString(5));
		prod.setUnitPrice(rs.getDouble(6));
		prod.setUnitsInStock(rs.getInt(7));
		prod.setUnitsOnOrder(rs.getInt(8));
		prod.setReorderLevel(rs.getInt(9));
		prod.setDiscontinued(rs.getInt(10));
		return prod;

	};

	// CRUD

	@Override
	public void addProduct(Product product) throws DaoException {
		String sql = "insert into Products values (?,?,?,?,?,?,?,?,?)";
		int changedRows = template.update(sql, product.getProductName(), product.getSupplierId(),
				product.getCategoryId(), product.getQuantityPerUnit(), product.getUnitPrice(),
				product.getUnitsInStock(), product.getUnitsOnOrder(), product.getReorderLevel(),
				product.getDiscontinued());
		if (changedRows == 0)
			throw new DaoException("Inserting to DB - failed");
		System.out.println("product was inserted successfully");
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		String sql = "update Products set productName=?, supplierId=?, categoryId=?, quantityPerUnit=?, unitPrice=?, unitsInStock=?, unitsOnOrder=?, reorderLevel=?, discontinued=?  where productId=?";
		int changedRows = template.update(sql, product.getProductName(), product.getSupplierId(),
				product.getCategoryId(), product.getQuantityPerUnit(), product.getUnitPrice(),
				product.getUnitsInStock(), product.getUnitsOnOrder(), product.getReorderLevel(),
				product.getDiscontinued(), product.getProductId());
		if (changedRows == 0)
			throw new DaoException("Updating DB - failed");
		System.out.println("product was updated successfully");
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		String sql = "select * from Products where productId=?";
		return template.queryForObject(sql, rowMapper, productId);
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		String sql = "update Products set discontinued = 1 where productId = ?";
		int changedRows = template.update(sql, productId);
		if (changedRows == 0)
			throw new DaoException("Deleting product - failed");
		System.out.println("Product deleted successfully");
	}

	@Override
	public List<Product> getAllProducts() throws DaoException {
		String sql = "select *  from Products ";
		return template.query(sql,rowMapper);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		String sql = "select *  from Products where unitPrice >= ? and unitPrice <= ? ";
		return template.query(sql, rowMapper, min, max);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		String sql = "select *  from Products where categoryId = ? ";
		return template.query(sql, rowMapper, categoryId);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		String sql = "select *  from Products where unitsInStock = 0 ";
		return template.query(sql, rowMapper);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		String sql = "select *  from Products where unitsOnOrder != 0 ";
		return template.query(sql, rowMapper);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		String sql = "select *  from Products where discontinued != 0 ";
		return template.query(sql, rowMapper);
	}

	@Override
	public long count() throws DaoException {
		String sql = "select count(*) from Products ";
		return template.queryForObject(sql, long.class);
	}
	

}
