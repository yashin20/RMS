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
public class Room {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String code; //room code
    private Boolean isReserved; //현재 예약 상태

    // 객실의 예약 시간 리스트
    @OneToMany(mappedBy = "room")
    private List<Reservation> resList = new ArrayList<>();
}
