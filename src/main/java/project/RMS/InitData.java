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
    static class InitMemberService {
        @PersistenceContext
        private EntityManager em;
        @Autowired private PasswordEncoder passwordEncoder;
        @Autowired private MemberService memberService;

        @Transactional
        public void init() {
            MemberDto.Request request = new MemberDto.Request();
            request.setUsername("customer1");
//            request.setPassword(passwordEncoder.encode("1q2w3e4r~!"));
            request.setPassword("1234");
            request.setNickname("nickname1");
            request.setPhone("010-1111-1111");
            request.setEmail("customer1@example.com");
            request.setRole(MemberRole.CUSTOMER);

            memberService.createMember(request);
        }

    }
}
