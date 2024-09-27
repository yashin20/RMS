package project.RMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.RMS.entity.Room;

import java.util.Optional;

public interface RoomRepository extends JpaRepository<Room, Long> {

    public Optional<Room> findByCode(String code);
}
