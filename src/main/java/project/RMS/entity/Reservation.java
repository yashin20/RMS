package project.RMS.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "reservation_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    //yyyy-MM-dd_HH:mm (예약 시작 시간 ~ +4)
    private String resTime;

    // 예약 시간 추가하기
    public String addReservation(LocalDateTime date) {
        SimpleDateFormat customFormat = new SimpleDateFormat("yyyy-MM-dd_HH:mm");
        return customFormat.format(date);
    }
}
