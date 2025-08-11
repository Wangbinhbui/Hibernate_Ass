package fa.training.service;

import fa.training.Entity.Member;
import fa.training.dao.MemberDao;
import fa.training.exception.BusinessException;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;

public class MemberService {
    private final MemberDao memberDao;

    public MemberService(MemberDao memberDao) { this.memberDao = memberDao; }

    public Long register(String name, String email, String phone) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            if (memberDao.findByEmail(email).isPresent()) {
                throw new BusinessException("Email đã tồn tại");
            }
            Member m = new Member();
            m.setName(name);
            m.setEmail(email);
            m.setPhone(phone);
            memberDao.create(m);
            tx.commit();
            return m.getId();
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public Optional<Member> findById(Long id) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { Optional<Member> r = memberDao.findById(id); tx.commit(); return r; }
        catch (Exception e) { tx.rollback(); throw e; }
    }

    public Optional<Member> findByEmail(String email) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { Optional<Member> r = memberDao.findByEmail(email); tx.commit(); return r; }
        catch (Exception e) { tx.rollback(); throw e; }
    }

    public List<Member> findAll() {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try { List<Member> r = memberDao.findAll(); tx.commit(); return r; }
        catch (Exception e) { tx.rollback(); throw e; }
    }
}
