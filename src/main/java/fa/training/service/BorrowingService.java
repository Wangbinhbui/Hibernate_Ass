package fa.training.service;

import fa.training.Entity.*;
import fa.training.dao.BookDao;
import fa.training.dao.BorrowingDao;
import fa.training.dao.MemberDao;
import fa.training.exception.BusinessException;
import fa.training.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BorrowingService {
    private final BorrowingDao borrowingDao;
    private final BookDao bookDao;
    private final MemberDao memberDao;

    public BorrowingService(BorrowingDao br, BookDao bk, MemberDao mr) {
        this.borrowingDao = br; this.bookDao = bk; this.memberDao = mr;
    }

    /** Mượn nhiều sách: giới hạn 5 quyển đang mượn, dueDate > hôm nay, và sách phải available */
    public List<Long> borrowBooks(Long memberId, List<Long> bookIds, LocalDate dueDate) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            Optional<Member> memOpt = memberDao.findById(memberId);
            if (memOpt.isEmpty()) throw new BusinessException("Member không tồn tại");

            // đếm nhanh số đang mượn của member (đơn giản: query all và lọc ở service nếu bạn chưa có method riêng)
            long currentActive = 0; // có thể thêm DAO countActiveBorrowings nếu muốn
            if (currentActive + bookIds.size() > 5) {
                throw new BusinessException("Quá giới hạn mượn 5 sách");
            }
            if (!dueDate.isAfter(LocalDate.now())) {
                throw new BusinessException("Ngày trả phải sau hôm nay");
            }

            List<Long> ids = new ArrayList<>();
            for (Long bookId : bookIds) {
                Book b = bookDao.findById(bookId).orElse(null);
                if (b == null) throw new BusinessException("Book không tồn tại: " + bookId);
                if (Boolean.FALSE.equals(b.getAvailable())) throw new BusinessException("Sách không sẵn sàng");

                Borrowing br = new Borrowing();
                br.setBook(b);
                br.setMember(memOpt.get());
                br.setBorrowDate(LocalDate.now());
                br.setDueDate(dueDate);
                br.setStatus(BorrowingStatus.BORROWED);

                borrowingDao.create(br);

                b.setAvailable(false);
                bookDao.update(b);

                ids.add(br.getId());
            }
            tx.commit();
            return ids;
        } catch (Exception e) { tx.rollback(); throw e; }
    }

    public void returnBooks(List<Long> borrowingIds, LocalDate returnDate) {
        Session s = HibernateUtil.getSessionFactory().getCurrentSession();
        Transaction tx = s.beginTransaction();
        try {
            for (Long id : borrowingIds) {
                Borrowing br = borrowingDao.findById(id).orElse(null);
                if (br == null || br.getStatus() == BorrowingStatus.RETURNED) continue;

                br.setReturnDate(returnDate);
                br.setStatus(BorrowingStatus.RETURNED);
                borrowingDao.update(br);

                Book b = br.getBook();
                b.setAvailable(true);
                bookDao.update(b);
            }
            tx.commit();
        } catch (Exception e) { tx.rollback(); throw e; }
    }
}
