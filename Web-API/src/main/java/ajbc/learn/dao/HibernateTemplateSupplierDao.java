package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Supplier;

@SuppressWarnings("unchecked")
@Component(value = "htSupplierDao")
public class HibernateTemplateSupplierDao implements SupplierDao {

	@Autowired
	private HibernateTemplate template;
	
	@Autowired
	private ProductDao productDao;

	@Override
	public void addSupplier(Supplier supplier) throws DaoException {
		// open session/connection to DB
		template.persist(supplier);
		// close session

	}

	@Override
	public void updateSupplier(Supplier supplier) throws DaoException {
		template.merge(supplier);
	}

	@Override
	public Supplier getSupplier(Integer supplierId) throws DaoException {
		Supplier supplier = template.get(Supplier.class, supplierId);
		if (supplier == null)
			throw new DaoException("No such category in DB");
		return supplier;
	}

	@Override
	public void deleteSupplier(Integer supplierId) throws DaoException {
		Supplier supplier = getSupplier(supplierId);
		supplier.setInactive(1);
		updateSupplier(supplier);
		productDao.deleteSupplierById(supplierId);
	}

	@Override
	public List<Supplier> getAllCSuppliers() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Supplier.class);
		return (List<Supplier>) template.findByCriteria(criteria);
	}

}
