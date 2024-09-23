package project.RMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.RMS.dto.MemberDto;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.service.MemberService;

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
            model.addAttribute("bindingResult", bindingResult);
            return "members/joinForm";
        }

        try {
            //회원강비 로직
            memberService.createMember(dto);
        }
        /* '중복 검사' + 'PW 이중검사' 에러처리 */
        catch (DataAlreadyExistsException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "members/joinForm";
        }

        return "redirect:/";
    }
}
