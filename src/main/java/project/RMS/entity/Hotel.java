package project.RMS.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Hotel extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "accommodation_id")
    private Long id;

    private String name;
    private String address;
    private String phone;

    //호텔 소속 객실 리스트
    @OneToMany(mappedBy = "hotel")
    private List<Room> roomList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member; //숙박업소 관리자

    public Hotel(String name, String address, String phone, Member member) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.member = member;
    }
}
