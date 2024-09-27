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
public class Room extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "room_id")
    private Long id;

    private String code; //room code
    private Integer price; //4시간 기준
    private Integer headcount; //최대 인원
    private Boolean isReserved; //현재 예약 상태

    //소속호텔
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id")
    private Hotel hotel;

    // 객실의 예약 시간 리스트
    @OneToMany(mappedBy = "room")
    private List<Reservation> resList = new ArrayList<>();

    public void reservation(Boolean b) {
        if (!isReserved) isReserved = true;
        if (isReserved) isReserved = false;
    }

    public Room(String code, Integer price, Integer headcount) {
        this.code = code;
        this.price = price;
        this.headcount = headcount;
    }
}
