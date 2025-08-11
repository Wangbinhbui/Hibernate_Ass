package fa.training.service;

import fa.training.Entity.Author;
import fa.training.dao.AuthorDao;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class AuthorService {
    private final AuthorDao authorDao;

    public AuthorService(AuthorDao authorDao) { this.authorDao = authorDao; }

    public void create(Author a) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            authorDao.create(a);
            tx.commit();
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public void update(Author a) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            authorDao.update(a);
            tx.commit();
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public void delete(Long id) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            Optional<Author> a = authorDao.findById(id);
            a.ifPresent(authorDao::delete);
            tx.commit();
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public Optional<Author> findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            Optional<Author> res = authorDao.findById(id);
            tx.commit();
            return res;
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public List<Author> findAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            List<Author> res = authorDao.findAll();
            tx.commit();
            return res;
        } catch (Exception e) { tx.rollback(); throw e; }
    }
}
