package repository;

import java.util.List;

import javax.persistence.EntityManager;

import model.Category;

public class CategoryRepository implements Repository<Category> {
    private EntityManager em;

    public CategoryRepository setEntityManager(EntityManager em) {
        this.em = em;
        return this;
    }

    @Override
    public void insert(Category category) {
        em.persist(category);
    }

    @Override
    public void edit(Category category) throws Exception {
        em.merge(category);
    }

    @Override
    public void delete(Integer id) throws Exception {
        var category = em.find(Category.class, id);
        em.remove(category);
    }

    @Override
    public List<Category> listing() {
        return em.createQuery("select c from Category c", Category.class)
            .getResultList();
    }

    @Override
    public Category byId(Integer id) throws Exception {
        return em.find(Category.class, id);
    }
}
