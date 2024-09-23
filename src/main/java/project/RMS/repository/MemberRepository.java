package project.RMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.RMS.entity.Member;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    public Optional<Member> findByUsername(String username);

}
