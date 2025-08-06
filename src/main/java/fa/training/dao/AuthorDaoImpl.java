package fa.training.dao;

import fa.training.Entity.Author;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class AuthorDaoImpl implements AuthorDao {

    @Override
    public void create(Author author) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(author);
    }

    @Override
    public void update(Author author) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(author);
    }

    @Override
    public void delete(Author author) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.remove(author);
    }

    @Override
    public Optional<Author> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return Optional.ofNullable(session.find(Author.class, id));
    }

    @Override
    public List<Author> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Author> query = session.createQuery("FROM Author", Author.class);
        return query.getResultList();
    }
}
