package net.engineeringdigest.hotelbooking.repository;

import net.engineeringdigest.hotelbooking.model.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    List<Reservation> findByRoomNumber(String roomNumber);
}

