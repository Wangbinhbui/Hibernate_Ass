package fa.training.dao;


import fa.training.Entity.Borrowing;

import java.util.List;
import java.util.Optional;

public interface BorrowingDao {
    void create(Borrowing borrowing);

    void update(Borrowing borrowing);

    void delete(Borrowing borrowing);

    Optional<Borrowing> findById(Long id);

    List<Borrowing> findAll();
    // Additional methods can be added as needed
}
