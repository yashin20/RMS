package project.RMS.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Member extends BaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String username;
    private String password;

    private String nickname;
    private String phone;
    private String email;

    @Enumerated(EnumType.STRING)
    private MemberRole role;

    @OneToMany(mappedBy = "member")
    private List<Hotel> hotels = new ArrayList<>();

    public Member(Long id, String username, String password, String nickname, String phone, String email, MemberRole role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.email = email;
        this.role = role;
    }
}
