package business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import model.Category;
import repository.CategoryRepository;
import repository.Session;

@Stateless
public class CategoryService {
    @EJB
    private Session repository;
    private CategoryRepository categoryRepository;

    @PostConstruct
    public void initialize() {
        categoryRepository = repository.getCategoryRepository();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insert(Category category) {
        categoryRepository.insert(category);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void edit(Category category) throws Exception {
        categoryRepository.edit(category);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Integer id) throws Exception {
        categoryRepository.delete(id);
    }

    public List<Category> listing() {
        return categoryRepository.listing();
    }

    public Category byId(Integer id) throws Exception {
        return categoryRepository.byId(id);
    }
}
