package project.RMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import project.RMS.entity.Member;
import project.RMS.entity.MemberRole;

public class MemberDto {

    @Data
    public static class Request {

        // 회원 생성 : 'username', 'password', 'phone', 'nickname', 'email', 'role'
        public interface Create {}


        private Long memberId;

        @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", groups = {Create.class},
                message = "아이디는 영문 대소문자, 숫자로 이루어진 4~20자리 입니다.")
        @NotBlank(groups = {Create.class}, message = "아이디는 필수 입력값 입니다.")
        private String username;

        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,16}",
                groups = {Create.class},
                message = "비밀번호는 8~16자 영문 대 소문자, 숫자, 특수문자를 사용하세요.")
        @NotBlank(groups = {Create.class}, message = "비밀번호는 필수 입력 값입니다.")
        private String password;

        @NotBlank(groups = {Create.class}, message = "닉네임은 필수 입력 값입니다.")
        private String nickname;

        @Pattern(regexp = "^010-(?:\\d{3}|\\d{4})-\\d{4}$",
                groups = {Create.class},
                message = "전화번호 형식이 올바르지 않습니다.")
        @NotBlank(groups = {Create.class}, message = "전화번호는 필수 입력 값입니다.")
        private String phone;

        @Pattern(regexp = "^(?:\\w+\\.?)*\\w+@(?:\\w+\\.)+\\w+$",
                groups = {Create.class},
                message = "전화번호 형식이 올바르지 않습니다.")
        @NotBlank(groups = {Create.class}, message = "이메일은 필수 입력 값입니다.")
        private String email;

        @NotBlank(groups = {Create.class}, message = "권한은 필수 선택 값입니다.")
        private String role_string;

        //MemberRole 부여
        public MemberRole grantRole(String role_string) {
            if (role_string.equals("CUSTOMER")) return MemberRole.CUSTOMER;
            else if (role_string.equals("SUPPLIER")) return MemberRole.SUPPLIER;
            else return null;
        }

        //create member 시 사용
        public Member toEntity() {
            return new Member(
                    this.memberId,
                    this.username,
                    this.password,
                    this.nickname,
                    this.phone,
                    this.email,
                    grantRole(this.role_string)
            );
        }

    }

    @Data
    public static class Response {
        private Long memberId;
        private String username;
        private String password;
        private String phone;
        private String hotelAddress;
    }
}
