package project.RMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.RMS.dto.HotelDto;
import project.RMS.dto.MemberDto;
import project.RMS.entity.Hotel;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.service.HotelService;
import project.RMS.service.MemberService;

@Controller
@RequestMapping("/hotels")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final MemberService memberService;

    /**
     * 호텔 등록 (Register Hotel) - ("/hotels/new")
     * 1. 호텔 등록 페이지 (GET)
     * 2. 호텔 등록 절차수행 (POST)
     */
    /*1. 호텔 등록 페이지 (GET)*/
    @GetMapping("/new")
    public String createHotelForm(Model model) {
        model.addAttribute("createHotelForm", new HotelDto.Request());
        return "hotels/createHotel";
    }
    /*2. 호텔 등록 절차수행 (POST)*/
    @PostMapping("/new")
    public String createHotel(@ModelAttribute("createHotelForm") @Validated(HotelDto.Request.Create.class)HotelDto.Request dto,
                              BindingResult bindingResult,
                              Model model) {
        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {

            FieldError nameError = bindingResult.getFieldError("name");
            if (nameError != null) {
                model.addAttribute("nameErrorMessage", nameError.getDefaultMessage());
            }

            FieldError addressError = bindingResult.getFieldError("address");
            if (addressError != null) {
                model.addAttribute("addressErrorMessage", addressError.getDefaultMessage());
            }

            FieldError phoneError = bindingResult.getFieldError("phone");
            if (phoneError != null) {
                model.addAttribute("phoneErrorMessage", phoneError.getDefaultMessage());
            }

            return "hotels/createHotel";
        }

        try {
            dto.setMember(memberService.getCurrentMember());
            hotelService.createHotel(dto);
        }
        /* '중복 검사' 에러처리 */
        catch (DataAlreadyExistsException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "hotels/createHotel";
        }

        return "redirect:/";
    }

    /**
     * 호텔 정보 페이지 - ("/hotels/{hotelId}")
     */
    @GetMapping("/{hotelId}")
    public String hotelInfo(@PathVariable Long hotelId, Model model) {
        Hotel hotel = hotelService.getHotelById(hotelId);
        HotelDto.Response response = new HotelDto.Response(hotel);
        model.addAttribute("hotelDto", response);

        return "hotels/hotel_infoForm";
    }
}
