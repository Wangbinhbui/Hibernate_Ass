package fa.training.dao;


import fa.training.Entity.Author;

import java.util.List;
import java.util.Optional;

public interface AuthorDao {
    void create(Author author);
    void update(Author author);
    void delete(Author author);
    Optional<Author> findById(Long id);
    List<Author> findAll();
}
