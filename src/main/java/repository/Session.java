package repository;

import javax.ejb.Local;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

@Local
public interface Session {
    @TransactionAttribute(TransactionAttributeType.NEVER)
    public CategoryRepository getCategoryRepository();

    @TransactionAttribute(TransactionAttributeType.NEVER)
    public ProductRepository getProductRepository();
}
