package repository;

import java.util.List;

import javax.persistence.EntityManager;

import model.Category;
import model.Product;

public class ProductRepository implements Repository<Product> {
    private EntityManager em;

    public ProductRepository setEntityManager(EntityManager em) {
        this.em = em;
        return this;
    }

    @Override
    public void insert(Product product) {
        em.persist(product);
    }

    @Override
    public void edit(Product product) throws Exception {
        em.merge(product);
    }

    @Override
    public void delete(Integer id) throws Exception {
        var product = em.find(Product.class, id);
        em.remove(product);
    }

    @Override
    public List<Product> listing() {
        return em.createQuery("SELECT p FROM Product p", Product.class)
            .getResultList();
    }

    @Override
    public Product byId(Integer id) throws Exception {
        return em.find(Product.class, id);
    }

    public List<Product> byCategory(Category category) throws Exception {
        return em.createQuery("SELECT p FROM Product P WHERE p.category.id = :categoryId", Product.class)
            .setParameter("categoryId", category.getId())
            .getResultList();
    }
}
