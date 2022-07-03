package ajbc.learn.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Component;

import ajbc.learn.models.Category;
import ajbc.learn.models.Product;

@SuppressWarnings("unchecked")
@Component(value = "htCategoryDao")
public class HibernateTemplateCategoryDao implements CategoryDao {

	@Autowired
	private HibernateTemplate template;
	
	@Autowired
	ProductDao productDao;

	@Override
	public void addCategory(Category category) throws DaoException {
		// open session/connection to DB
		template.persist(category);
		// close session

	}

	@Override
	public void updateCategory(Category category) throws DaoException {
		template.merge(category);
	}

	@Override
	public Category getCategory(Integer categoryId) throws DaoException {
		Category category = template.get(Category.class, categoryId);
		if (category == null)
			throw new DaoException("No such category in DB");
		return category;
	}

	@Override
	public void deleteCategory(Integer categoryId) throws DaoException {
		Category category = getCategory(categoryId);
		category.setInactive(1);
		productDao.deleteCategoryById(categoryId);
		updateCategory(category);
	}

	@Override
	public List<Category> getAllCategories() throws DaoException {
		DetachedCriteria criteria = DetachedCriteria.forClass(Category.class);
		return (List<Category>) template.findByCriteria(criteria);
	}
	

}
