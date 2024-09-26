package project.RMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.RMS.dto.MemberDto;
import project.RMS.entity.Member;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.service.MemberService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 로그인 화면 (Login Page) - "/members/login"
     */
    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false) String error,
                            Model model) {
        model.addAttribute("loginForm", new MemberDto.Request());
        if (error != null) {
            model.addAttribute("errorMessage", "아이디 또는 비밀번호가 잘못되었습니다.");
        }

        return "members/loginForm";
    }

    /**
     * 회원가입 (Join Member) - "/members/join"
     * 1. 회원가입 페이지 (GET)
     * 2. 회원가입 절차 수행 (POST)
     */

    //1. 회원가입 페이지 (GET)
    @GetMapping("/join")
    public String joinForm(Model model) {
        model.addAttribute("joinForm", new MemberDto.Request());

        return "members/joinForm";
    }

    //2. 회원가입 절차 수행 (POST)
    @PostMapping("/join")
    public String join(@ModelAttribute("joinForm") @Validated(MemberDto.Request.Create.class)MemberDto.Request dto,
                       BindingResult bindingResult,
                       Model model) {

        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {

            FieldError usernameError = bindingResult.getFieldError("username");
            if (usernameError != null) {
                model.addAttribute("usernameErrorMessage", usernameError.getDefaultMessage());
            }

            FieldError passwordError = bindingResult.getFieldError("password");
            if (passwordError != null) {
                model.addAttribute("passwordErrorMessage", passwordError.getDefaultMessage());
            }

            FieldError nicknameError = bindingResult.getFieldError("nickname");
            if (nicknameError != null) {
                model.addAttribute("nicknameErrorMessage", nicknameError.getDefaultMessage());
            }

            FieldError phoneError = bindingResult.getFieldError("phone");
            if (phoneError != null) {
                model.addAttribute("phoneErrorMessage", phoneError.getDefaultMessage());
            }

            FieldError emailError = bindingResult.getFieldError("email");
            if (emailError != null) {
                model.addAttribute("emailErrorMessage", emailError.getDefaultMessage());
            }

            FieldError role_stringError = bindingResult.getFieldError("role_string");
            if (role_stringError != null) {
                model.addAttribute("role_stringErrorMessage", role_stringError.getDefaultMessage());
            }

            return "members/joinForm";
        }

        try {
            //회원강비 로직
            memberService.createMember(dto);
        }
        /* '중복 검사' 에러처리 */
        catch (DataAlreadyExistsException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "members/joinForm";
        }

        return "redirect:/";
    }

    /**
     * 회원 정보 조회
     */
    @GetMapping("/info")
    public String memberInformation(Model model) {
        //1. 현재 로그인된 회원 가져오기
        Member currentMember = memberService.getCurrentMember();

        //2. Entity -> Dto
        MemberDto.Response response = new MemberDto.Response(currentMember);
        model.addAttribute("responseDto", response);

        return "members/infoForm";
    }
}
