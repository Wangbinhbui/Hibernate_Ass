package fa.training.dao;


import fa.training.Entity.Book;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class BookDaoImpl implements BookDao {

    @Override
    public void create(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(book);
    }

    @Override
    public void update(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(book);
    }

    @Override
    public void delete(Book book) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.remove(book);
    }

    @Override
    public Optional<Book> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return Optional.ofNullable(session.find(Book.class, id));
    }

    @Override
    public List<Book> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Book> query = session.createQuery("FROM Book", Book.class);
        return query.getResultList();
    }
}
