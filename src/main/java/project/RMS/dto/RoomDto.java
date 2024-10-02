package project.RMS.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import project.RMS.entity.Hotel;
import project.RMS.entity.Room;

public class RoomDto {

    @Data
    public static class Request {

        // 객실 생성 : 'code', 'price', 'headcount', 'hotelId'
        public interface Create {}

        private Long roomId;

        @NotBlank(groups = {Create.class}, message = "객실 코드는 필수 입력 값입니다.")
        private String code;

        @NotBlank(groups = {Create.class}, message = "객실 가격은 필수 입력 값입니다.")
        private String price; //4시간 기준

        @NotNull(groups = {Create.class}, message = "최대 수용인원은 필수 입력 값입니다.")
        private int headcount; //최대 인원

        private Hotel hotel;

        private Boolean isReserved;

        public Room toEntity() {
            return new Room(
                    this.code,
                    this.price,
                    this.headcount,
                    this.hotel
            );
        }
    }

    @Data
    public static class Response {
        private String code;
        private String price;
        private int headcount;

        //Entity -> Dto
        public Response(Room room) {
            this.code = room.getCode();
            this.price = room.getPrice();
            this.headcount = room.getHeadcount();
        }
    }
}
