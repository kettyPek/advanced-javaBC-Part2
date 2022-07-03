package ajbc.learn.resources;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.dao.SupplierDao;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Supplier;

@RequestMapping("/suppliers")
@RestController
public class SuppliersResource {

	@Autowired
	SupplierDao dao;
	@Autowired
	ProductDao daoProduct;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Supplier>> getAllSuppliers() throws DaoException {
		List<Supplier> allSuppliers = dao.getAllCSuppliers();
		if (allSuppliers == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(allSuppliers);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getSupplierById(@PathVariable Integer id) {
		try {
			Supplier supplier = dao.getSupplier(id);
			return ResponseEntity.ok(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("failed to get a product with id " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> upadteSupplier(@RequestBody Supplier supplier, @PathVariable Integer id) {
		try {
			supplier.setSupplierId(id);
			dao.updateSupplier(supplier);
			supplier = dao.getSupplier(id);
			return ResponseEntity.status(HttpStatus.OK).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not update product");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addSupplier(@RequestBody Supplier supplier) {
		try {
			dao.addSupplier(supplier);
			supplier = dao.getSupplier(supplier.getSupplierId());
			return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not insert product into DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
		try {
			dao.deleteSupplier(id);
			Supplier supplier = dao.getSupplier(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(supplier);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not insert product into DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

}
