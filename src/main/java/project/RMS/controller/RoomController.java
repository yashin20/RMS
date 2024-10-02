package project.RMS.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.RMS.dto.HotelDto;
import project.RMS.dto.RoomDto;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.repository.RoomRepository;
import project.RMS.service.HotelService;
import project.RMS.service.RoomService;

@Controller
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final HotelService hotelService;

    /**
     * 객실 등록 (Register Room) - ("/rooms/{hotelId}/new")
     * 1. 객실 등록 페이지 (GET)
     * 2. 객실 등록 절차수행 (POST)
     */
    /*1. 객실 등록 페이지 (GET)*/
    @GetMapping("/{hotelId}/new")
    public String createRoomForm(@PathVariable Long hotelId, Model model) {
        model.addAttribute("createRoomForm", new RoomDto.Request());
        return "rooms/createRoom";
    }
    /*2. 객실 등록 절차수행 (POST)*/
    @PostMapping("/{hotelId}/new")
    public String createRoom(@PathVariable Long hotelId,
                             @ModelAttribute("createRoomForm") @Validated(RoomDto.Request.Create.class)RoomDto.Request dto,
                             BindingResult bindingResult,
                             Model model) {
        /* '유효성 검사' 에러처리 */
        if (bindingResult.hasErrors()) {

            FieldError codeError = bindingResult.getFieldError("code");
            if (codeError != null) {
                model.addAttribute("codeErrorMessage", codeError.getDefaultMessage());
            }

            FieldError priceError = bindingResult.getFieldError("price");
            if (priceError != null) {
                model.addAttribute("priceErrorMessage", priceError.getDefaultMessage());
            }

            FieldError headcountError = bindingResult.getFieldError("headcount");
            if (headcountError != null) {
                model.addAttribute("headcountErrorMessage", headcountError.getDefaultMessage());
            }

            return "rooms/createRoom";
        }

        try {
            //소속 hotel 설정
            dto.setHotel(hotelService.getHotelById(hotelId));

            roomService.createRoom(dto);
        }
        /* '중복 검사' 에러처리 */
        catch (DataAlreadyExistsException ex) {
            bindingResult.reject("errorMessage", ex.getMessage());
            model.addAttribute("errorMessage", ex.getMessage());

            return "rooms/createRoom";
        }

        return "redirect:/";
    }


    /**
     * 객실 정보 (Room Information) - ("/rooms/info")
     */
}
