package project.RMS.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.RMS.dto.RoomDto;
import project.RMS.entity.Room;
import project.RMS.exception.DataNotFoundException;
import project.RMS.repository.RoomRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoomService {

    private final RoomRepository roomRepository;

    /**
     * 객실 생성 (Create Room)
     */
    @Transactional
    public Room createRoom(RoomDto.Request dto) {
        Room entity = dto.toEntity();
        return roomRepository.save(entity);
    }

    /**
     * 객실 조회
     * 1. ID 기반 조회
     * 2. Room Code 기반 조회
     */
    /*1. ID 기반 조회*/
    public Room getRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 객실 입니다."));
    }
    /*2. Room Code 기반 조회*/
    public Room getRoomByCode(String roomCode) {
        return roomRepository.findByCode(roomCode)
                .orElseThrow(() -> new DataNotFoundException("존재하지 않는 객실 입니다."));
    }

    /**
     * 객실 수정
     */

    /**
     * 객실 삭제
     */
}
