package fa.training.service;

import fa.training.Entity.Book;
import fa.training.dao.BookDao;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class BookService {
    private final BookDao bookDao;

    public BookService(BookDao bookDao) { this.bookDao = bookDao; }

    public Long create(String title) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            Book b = new Book();
            b.setTitle(title);
            b.setAvailable(true);
            bookDao.create(b);
            tx.commit();
            return b.getId();
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public void update(Book b) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { bookDao.update(b); tx.commit(); }
        catch (Exception e) { tx.rollback(); throw e; }
    }

    /** Không cho xóa nếu sách đang được mượn (rule cơ bản) */
    public void delete(Book b, long activeBorrowCount) {
        if (activeBorrowCount > 0) {
            throw new RuntimeException("Không thể xóa sách đang được mượn");
        }
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { bookDao.delete(b); tx.commit(); }
        catch (Exception e) { tx.rollback(); throw e; }
    }

    public Optional<Book> findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { Optional<Book> r = bookDao.findById(id); tx.commit(); return r; }
        catch (Exception e) { tx.rollback(); throw e; }
    }

    public List<Book> findAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { List<Book> r = bookDao.findAll(); tx.commit(); return r; }
        catch (Exception e) { tx.rollback(); throw e; }
    }
}
