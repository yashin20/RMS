package project.RMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.RMS.dto.MemberDto;
import project.RMS.entity.Member;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.exception.DataNotFoundException;
import project.RMS.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원 생성 (Create Member)
     * 1. username 중복 검사
     * 2. password encoding
     */
    @Transactional
    public Member createMember(MemberDto.Request dto) {

        /*1. username 중복 검사*/
        duplicateValidation(dto);

        /*2. password encoding*/
        passwordEncoding(dto);

        /*3. dto to Entity*/
        Member entity = dto.toEntity();

        /*4. Entity Save*/
        return memberRepository.save(entity);
    }

    /*중복 검사 (username)*/
    public void duplicateValidation(MemberDto.Request dto) {
        if (memberRepository.findByUsername(dto.getUsername()).isPresent()){
            throw new DataAlreadyExistsException("이미 존재하는 Username 입니다.");
        }
    }

    /*password encoding*/
    public void passwordEncoding(MemberDto.Request dto) {
        String encoded = passwordEncoder.encode(dto.getPassword());
        dto.setPassword(encoded);
    }

    /**
     * 1. 회원 조회 (findById)
     * 2. 회원 조회 (findByUsername)
     */
    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원 입니다."));
    }

    public Member getMemberByUsername(String username) {
        return memberRepository.findByUsername(username)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 회원 입니다."));
    }

    /**
     * 회원 수정 (Update Member)
     */

    /**
     * 회원 삭제 (Delete Member)
     */


}
