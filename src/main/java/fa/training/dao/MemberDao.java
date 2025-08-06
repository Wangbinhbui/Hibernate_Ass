package fa.training.dao;


import fa.training.Entity.Member;

import java.util.List;
import java.util.Optional;

public interface MemberDao {
    void create(Member member);
    void update(Member member);
    void delete(Member member);
    Optional<Member> findById(Long id);
    Optional<Member> findByEmail(String email);
    List<Member> findAll();
}
