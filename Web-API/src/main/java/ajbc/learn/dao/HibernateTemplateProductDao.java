package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;

@SuppressWarnings("unchecked")
@Component(value = "htDao")
public class HibernateTemplateProductDao implements ProductDao{
	
	@Autowired
	private HibernateTemplate template;

	@Override
	public void addProduct(Product product) throws DaoException {
		// open session/connection to DB
		template.persist(product);
		// close session
		
	}

	@Override
	public void updateProduct(Product product) throws DaoException {
		template.merge(product);
	}

	@Override
	public Product getProduct(Integer productId) throws DaoException {
		Product product =  template.get(Product.class, productId);
		if(product == null)
			throw new DaoException("No such product in DB");
		return product;
	}

	@Override
	public void deleteProduct(Integer productId) throws DaoException {
		Product prod = getProduct(productId);
		prod.setDiscontinued(1);
		updateProduct(prod);
	}

	
	@Override
	public List<Product> getAllProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsByPriceRange(Double min, Double max) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.between("unitPrice", min, max);
		criteria.add(criterion);
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsInCategory(Integer categoryId) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.eq("categoryId", categoryId);
		criteria.add(criterion);
		return(List<Product>) template.findByCriteria(criteria);
	}
	
	@Override
	public List<Product> getProductsBySupplier(Integer supplierId) throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		Criterion criterion = Restrictions.eq("supplierId", supplierId);
		criteria.add(criterion);
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsNotInStock() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("unitsInStock", 0));
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getProductsOnOrder() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.gt("unitsOnOrder", 0));
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public List<Product> getDiscontinuedProducts() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.add(Restrictions.eq("discontinued", 1));
		return(List<Product>) template.findByCriteria(criteria);
	}

	@Override
	public long count() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Product.class);
		criteria.setProjection(Projections.rowCount());
		return (long)template.findByCriteria(criteria).get(0);
	}
	
	@Override
	public void deleteCategoryById(int categoryId) throws DaoException{
		List<Product> products = getProductsInCategory(categoryId);
		for(int i=0; i<products.size();i++) {
			products.get(i).setCategoryId(null);
			products.get(i).setCategory(null);
			updateProduct(products.get(i));
		}
	}
	
	@Override
	public void deleteSupplierById(int supplierId) throws DaoException{
		List<Product> products = getProductsBySupplier(supplierId);
		for(int i=0; i<products.size();i++) {
			products.get(i).setSupplier(null);
			products.get(i).setSupplierId(null);
			updateProduct(products.get(i));
		}
	}

	

	
	
	
	

}
