package project.RMS.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.RMS.entity.Hotel;

import java.util.Optional;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

    Optional<Hotel> findByName(String name);
}
