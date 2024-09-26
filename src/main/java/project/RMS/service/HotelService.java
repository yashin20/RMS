package project.RMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.RMS.dto.HotelDto;
import project.RMS.entity.Hotel;
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
        Hotel entity = dto.toEntity();
        return hotelRepository.save(entity);
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
