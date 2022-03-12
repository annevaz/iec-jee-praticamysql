package repository;

import java.util.List;

public interface Repository<T> {
    void insert(T category);

    void edit(T category) throws Exception;

    void delete(Integer id) throws Exception;

    List<T> listing();

    T byId(Integer id) throws Exception;
}
