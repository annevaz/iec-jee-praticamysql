package business;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

import model.Category;
import model.Product;
import repository.ProductRepository;
import repository.Session;

@Stateless
public class ProductService {
    @EJB
    private Session repository;
    private ProductRepository productRepository;

    @PostConstruct
    public void initialize() {
        productRepository = repository.getProductRepository();
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void insert(Product product) {
        productRepository.insert(product);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void edit(Product product) throws Exception {
        productRepository.edit(product);
    }

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public void delete(Integer id) throws Exception {
        productRepository.delete(id);
    }

    public List<Product> listing() {
        return productRepository.listing();
    }

    public Product byId(Integer id) throws Exception {
        return productRepository.byId(id);
    }

    public List<Product> byCategory(Category category) throws Exception {
        return productRepository.byCategory(category);
    }
}
