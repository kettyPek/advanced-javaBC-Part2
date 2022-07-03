package ajbc.learn.dao;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import ajbc.learn.models.Supplier;

@Transactional(rollbackFor = { DaoException.class }, readOnly = true)
public interface SupplierDao {

	// CRUD operations
	@Transactional(readOnly = false)
	public default void addSupplier(Supplier supplier) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	@Transactional(readOnly = false)
	public default void updateSupplier(Supplier supplier) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	public default Supplier getSupplier(Integer supplierId) throws DaoException {
		throw new DaoException("Method not implemented");
	}
	
	@Transactional(readOnly = false)
	public default void deleteSupplier(Integer categoryId) throws DaoException {
		throw new DaoException("Method not implemented");
	}

	// QUERIES
	public default List<Supplier> getAllCSuppliers() throws DaoException {
		throw new DaoException("Method not implemented");
	}

}