package project.RMS.dto;

import lombok.Data;
import project.RMS.entity.Room;

public class RoomDto {

    @Data
    public static class Request {

        // 객실 생성 : 'code', 'price', 'headcount'
        public interface Create {}

        private Long roomId;
        private String code;
        private Integer price; //4시간 기준
        private Integer headcount; //최대 인원
        private Boolean isReserved;

        public Request(String code, Integer price, Integer headcount) {
            this.code = code;
            this.price = price;
            this.headcount = headcount;
            this.isReserved = false;
        }

        public Room toEntity() {
            return new Room(
                    this.code,
                    this.price,
                    this.headcount
            );
        }
    }

    @Data
    public static class Response {
        private String code;
        private Integer price;
        private Integer headcount;

        //Entity -> Dto
        public Response(Room room) {
            this.code = room.getCode();
            this.price = room.getPrice();
            this.headcount = room.getHeadcount();
        }
    }
}
