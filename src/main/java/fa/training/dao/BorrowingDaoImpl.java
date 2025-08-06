package fa.training.dao;

import fa.training.Entity.Borrowing;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class BorrowingDaoImpl implements BorrowingDao {

    @Override
    public void create(Borrowing borrowing) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(borrowing);
    }

    @Override
    public void update(Borrowing borrowing) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(borrowing);
    }

    @Override
    public void delete(Borrowing borrowing) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.remove(borrowing);
    }

    @Override
    public Optional<Borrowing> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return Optional.ofNullable(session.find(Borrowing.class, id));
    }

    @Override
    public List<Borrowing> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Borrowing> query = session.createQuery("FROM Borrowing", Borrowing.class);
        return query.getResultList();
    }
}
