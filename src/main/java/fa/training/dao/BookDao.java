package fa.training.dao;


import fa.training.Entity.Book;

import java.util.List;
import java.util.Optional;

public interface BookDao {
    void create(Book book);
    void update(Book book);
    void delete(Book book);
    Optional<Book> findById(Long id);
    List<Book> findAll();
}
