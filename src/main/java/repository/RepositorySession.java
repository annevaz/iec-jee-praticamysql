package repository;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
public class RepositorySession implements Session {
    @PersistenceContext
    private EntityManager em;

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public CategoryRepository getCategoryRepository(){
        return new CategoryRepository().setEntityManager(em);
    }

    @Override
    @TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public ProductRepository getProductRepository(){
        return new ProductRepository().setEntityManager(em);
    }
}
