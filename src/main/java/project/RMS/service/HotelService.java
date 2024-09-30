package project.RMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.RMS.dto.HotelDto;
import project.RMS.dto.MemberDto;
import project.RMS.entity.Hotel;
import project.RMS.exception.DataAlreadyExistsException;
import project.RMS.exception.DataNotFoundException;
import project.RMS.repository.HotelRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;

    /**
     * Hotel 생성
     */
    @Transactional
    public Hotel createHotel (HotelDto.Request dto) {

        /*1. name 중복 검사*/
        duplicateValidation(dto);

        Hotel entity = dto.toEntity();
        return hotelRepository.save(entity);
    }

    /*중복 검사 (name : 호텔명)*/
    public void duplicateValidation(HotelDto.Request dto) {
        if (hotelRepository.findByName(dto.getName()).isPresent()){
            throw new DataAlreadyExistsException("이미 존재하는 호텔명(Name) 입니다.");
        }
    }

    /**
     * Hotel 조회
     * 1. ID 기반 조회
     * 2. Name 기반 조회
     */
    public Hotel getHotelById(Long hotelId) {
        return hotelRepository.findById(hotelId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 호텔 입니다."));
    }
    public Hotel getHotelByName(String hotelName) {
        return hotelRepository.findByName(hotelName)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 호텔 입니다."));
    }

    /**
     * 호텔 수정 (Hotel Update)
     */

    /**
     * 호텔 삭제 (Hotel Delete)
     */


}
