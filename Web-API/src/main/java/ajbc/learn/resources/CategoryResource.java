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

import ajbc.learn.dao.CategoryDao;
import ajbc.learn.dao.DaoException;

import ajbc.learn.models.Category;
import ajbc.learn.models.ErrorMessage;

@RequestMapping("/categories")
@RestController
public class CategoryResource {

	@Autowired
	CategoryDao dao;

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Category>> getAllCategories() throws DaoException {
		List<Category> allCategories = dao.getAllCategories();
		if (allCategories == null)
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		return ResponseEntity.ok(allCategories);
	}

	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getCategoryById(@PathVariable Integer id) {
		try {
			Category category = dao.getCategory(id);
			return ResponseEntity.ok(category);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("failed to get a product with id " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> updateCategory(@RequestBody Category category, @PathVariable Integer id) {
		try {
			category.setCategoryId(id);
			dao.updateCategory(category);
			category = dao.getCategory(id);
			return ResponseEntity.status(HttpStatus.OK).body(category);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not update product");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addCategory(@RequestBody Category category) {
		try {
			dao.addCategory(category);
			category = dao.getCategory(category.getCategoryId());
			return ResponseEntity.status(HttpStatus.CREATED).body(category);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not insert product into DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteCategory(@PathVariable Integer id) {
		try {
			dao.deleteCategory(id);
			Category category = dao.getCategory(id);
			return ResponseEntity.status(HttpStatus.CREATED).body(category);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not insert product into DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}

}
