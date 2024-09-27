package project.RMS.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import project.RMS.entity.Hotel;
import project.RMS.entity.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class HotelDto {

    @Data
    public static class Request {

        // 호텔 생성 : 'name', 'address', 'phone', 'member'
        public interface Create {}

        private Long hotelId;

        @NotBlank(groups = {Create.class}, message = "호텔명은 필수 입력 값입니다.")
        private String name;

        @NotBlank(groups = {Create.class}, message = "주소는 필수 입력 값입니다.")
        private String address;

        @NotBlank(groups = {Create.class}, message = "연락처 필수 입력 값입니다.")
        private String phone;

//        @NotBlank(groups = {Create.class}, message = "연락처 필수 입력 값입니다.")
        private Member member;

        //create member 시 사용
        public Hotel toEntity() {
            return new Hotel(
                    this.name,
                    this.address,
                    this.phone,
                    this.member
            );
        }
    }

    @Data
    public static class Response {
        private Long hotelId;
        private String name;
        private String address;
        private String phone;

        private List<RoomDto.Response> roomList = new ArrayList<>();

        public Response(Hotel hotel) {
            this.hotelId = hotel.getId();
            this.name = hotel.getName();
            this.address = hotel.getAddress();
            this.phone = hotel.getPhone();

            this.roomList = hotel.getRoomList().stream()
                    .map(RoomDto.Response::new)
                    .collect(Collectors.toList());
        }
    }
}
