package fa.training.dao;


import fa.training.Entity.Member;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;
import java.util.Optional;

public class MemberDaoImpl implements MemberDao {

    @Override
    public void create(Member member) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.persist(member);
    }

    @Override
    public void update(Member member) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.merge(member);
    }

    @Override
    public void delete(Member member) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.remove(member);
    }

    @Override
    public Optional<Member> findById(Long id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        return Optional.ofNullable(session.find(Member.class, id));
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Member> query = session.createQuery("FROM Member WHERE email = :email", Member.class);
        query.setParameter("email", email);
        return query.uniqueResultOptional();
    }

    @Override
    public List<Member> findAll() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Query<Member> query = session.createQuery("FROM Member", Member.class);
        return query.getResultList();
    }
}
