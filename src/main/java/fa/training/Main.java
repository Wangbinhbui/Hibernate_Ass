package fa.training;

import fa.training.dao.*;
import fa.training.service.*;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        AuthorDao authorDao = new AuthorDaoImpl();
        BookDao bookDao = new BookDaoImpl();
        MemberDao memberDao = new MemberDaoImpl();
        BorrowingDao borrowingDao = new BorrowingDaoImpl();

        // Service
        AuthorService authorService = new AuthorService(authorDao);
        BookService bookService = new BookService(bookDao);
        MemberService memberService = new MemberService(memberDao);
        BorrowingService borrowingService = new BorrowingService(borrowingDao, bookDao, memberDao);

        Long book1 = bookService.create("Clean Code");
        Long book2 = bookService.create("Refactoring");
        Long memberId = memberService.register("Alice", "alice@example.com", "0900000000");

        borrowingService.borrowBooks(memberId, List.of(book1, book2), LocalDate.now().plusDays(7));
        System.out.println("Done borrow!");

        bookService.findAll().forEach(b ->
                System.out.println(b.getId() + " - " + b.getTitle() + " - available=" + b.getAvailable())
        );
    }
}
