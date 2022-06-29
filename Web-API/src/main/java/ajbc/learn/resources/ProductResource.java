package ajbc.learn.resources;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.logging.ErrorManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ajbc.learn.dao.DaoException;
import ajbc.learn.dao.ProductDao;
import ajbc.learn.models.ErrorMessage;
import ajbc.learn.models.Product;

@RequestMapping("/products")
@RestController
public class ProductResource {
	
	@Autowired
	ProductDao dao;
	
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getAllProducts() throws DaoException{
//		List<Product> allProducts =  dao.getAllProducts();
//		if(allProducts == null)
//			return ResponseEntity.notFound().build();
//		return ResponseEntity.ok(allProducts);
//		
//	}
	
//	@RequestMapping(method = RequestMethod.GET)
//	public ResponseEntity<List<Product>> getProductsByRange(@RequestParam Optional<Double> min,@RequestParam Optional<Double> max) throws DaoException{
//		List<Product> allProducts;
//		if(min.isPresent() && max.isPresent())
//			allProducts =  dao.getProductsByPriceRange(min.get(), max.get());
//		else
//			allProducts = dao.getAllProducts();
//		if(allProducts == null)
//			return ResponseEntity.notFound().build();
//		return ResponseEntity.ok(allProducts);
//		
//	}
	
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Product>> getProductsByRange(@RequestParam Map<String,String> map) throws DaoException{
		List<Product> allProducts;
		Set<String> keys = map.keySet();
		
		if(keys.contains("min") && keys.contains("max"))
			allProducts =  dao.getProductsByPriceRange(Double.parseDouble(map.get("min")),Double.parseDouble(map.get("max")));
		else
			allProducts = dao.getAllProducts();
		if(allProducts == null)
			return ResponseEntity.notFound().build();
		return ResponseEntity.ok(allProducts);
		
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/{id}")
	public ResponseEntity<?> getProductsById(@PathVariable Integer id){
		try {
			Product product = dao.getProduct(id);
			return ResponseEntity.ok(product);
		} catch (DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("failed to get a product with id " + id);
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMsg);
		}
		
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, path = "/{id}")
	public ResponseEntity<?> addProduct(@RequestBody Product product, @PathVariable Integer id){
		try {
			product.setProductId(id);
			dao.updateProduct(product);
			product = dao.getProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}catch(DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not update product");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}	
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<?> addProduct(@RequestBody Product product){
		try {
			dao.addProduct(product);
			product = dao.getProduct(product.getProductId());
			return ResponseEntity.status(HttpStatus.CREATED).body(product);
		}catch(DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not insert product into DB");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
	public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
		try {
			Product product = dao.getProduct(id);
			dao.deleteProduct(id);
			return ResponseEntity.status(HttpStatus.OK).body(product);
		}catch(DaoException e) {
			ErrorMessage errorMsg = new ErrorMessage();
			errorMsg.setData(e.getMessage());
			errorMsg.setMsg("could not update product");
			return ResponseEntity.status(HttpStatus.valueOf(500)).body(errorMsg);
		}
	}	
	
	

}
