package project.RMS;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.RMS.dto.MemberDto;
import project.RMS.entity.Member;
import project.RMS.entity.MemberRole;
import project.RMS.service.MemberService;

//Test Data 주입
@Profile("local")
@Component
@RequiredArgsConstructor
public class InitData {

    private final InitMemberService initMemberService;

    @PostConstruct
    public void init() {initMemberService.init();}

    @Component
    @RequiredArgsConstructor
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;
        private final PasswordEncoder passwordEncoder;
        private final MemberService memberService;

        @Transactional
        public void init() {
            //customer1 생성
            createTestMember("member1", "1234", "CUSTOMER");

            //customer2 생성
            createTestMember("member2", "1234", "CUSTOMER");

            //supplier1 생성
            createTestMember("member3", "1234", "SUPPLIER");

            //ADMIN 생성

        }

        public void createTestMember(String username, String password, String role) {
            MemberDto.Request request = new MemberDto.Request();
            request.setUsername(username);
            request.setPassword(password);
            request.setNickname("custom_nickname");
            request.setPhone("010-1111-1111");
            request.setEmail("testData@example.com");
            request.setRole_string(role);

            memberService.createMember(request);
        }

    }
}
