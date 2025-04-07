package app.dao;

import java.util.List;

public interface IDAO<T> {
    T getById(Long id);
    List<T> getAll();
    void create(T dto);
    void update(T dto);
    void delete(Long id);
}