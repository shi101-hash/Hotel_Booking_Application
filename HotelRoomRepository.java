package net.engineeringdigest.hotelbooking.repository;

import net.engineeringdigest.hotelbooking.model.HotelRoom;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HotelRoomRepository extends MongoRepository<HotelRoom, String> {
    Optional<HotelRoom> findByRoomNumber(String roomNumber);

}


