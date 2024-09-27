package project.RMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import project.RMS.dto.HotelDto;
import project.RMS.entity.Hotel;
import project.RMS.service.HotelService;

@Controller
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;

    /**
     * 호텔 정보 페이지
     */
    @GetMapping("/{hotelId}")
    public String hotelInfo(@PathVariable Long hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        HotelDto.Response response = new HotelDto.Response(hotel);
        model.addAttribute("hotelDto", response);

        return "hotels/hotel_infoForm";
    }
}
