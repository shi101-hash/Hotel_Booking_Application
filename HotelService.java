package net.engineeringdigest.hotelbooking.service;

import net.engineeringdigest.hotelbooking.model.HotelRoom;
import net.engineeringdigest.hotelbooking.model.Reservation;
import net.engineeringdigest.hotelbooking.repository.HotelRoomRepository;
import net.engineeringdigest.hotelbooking.repository.ReservationRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class HotelService {
    private final HotelRoomRepository hotelRoomRepository;
    private final ReservationRepository reservationRepository;

    public HotelService(HotelRoomRepository hotelRoomRepository, ReservationRepository reservationRepository) {
        this.hotelRoomRepository = hotelRoomRepository;
        this.reservationRepository = reservationRepository;

        initializeRooms();
    }

    private void initializeRooms() {
        if (hotelRoomRepository.count() == 0) {
            HotelRoom room1 = new HotelRoom();
            room1.setRoomNumber("101");
            room1.setType("Single");
            room1.setAvailable(true);
            hotelRoomRepository.save(room1);

            HotelRoom room2 = new HotelRoom();
            room2.setRoomNumber("102");
            room2.setType("Double");
            room2.setAvailable(true);
            hotelRoomRepository.save(room2);

            HotelRoom room3 = new HotelRoom();
            room3.setRoomNumber("103");
            room3.setType("Suite");
            room3.setAvailable(true);
            hotelRoomRepository.save(room3);
        }
    }


    public List<HotelRoom> getAllRooms() {
        return hotelRoomRepository.findAll();
    }

    public Optional<HotelRoom> getRoomByNumber(String roomNumber) {
        return hotelRoomRepository.findByRoomNumber(roomNumber);
    }

    public Reservation bookRoom(Reservation reservation) {
        Optional<HotelRoom> room = hotelRoomRepository.findByRoomNumber(reservation.getRoomNumber());
        if (room.isPresent() && room.get().isAvailable()) {
            room.get().setAvailable(false);
            hotelRoomRepository.save(room.get());
            return reservationRepository.save(reservation);
        }
        return null;
    }

    public List<Reservation> getReservations() {
        return reservationRepository.findAll();
    }

    // Scheduled task to mark rooms as available after check-out date
    @Scheduled(fixedRate = 86400000) // This runs once every 24 hours (86400000 ms)
    public void markRoomsAsAvailable() {
        List<Reservation> reservations = reservationRepository.findAll();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = new Date();

        for (Reservation reservation : reservations) {
            try {
                Date checkOutDate = sdf.parse(reservation.getCheckOutDate());
                if (checkOutDate.before(currentDate)) {
                    // Room is available after the check-out date
                    Optional<HotelRoom> room = hotelRoomRepository.findByRoomNumber(reservation.getRoomNumber());
                    if (room.isPresent()) {
                        room.get().setAvailable(true);
                        hotelRoomRepository.save(room.get());
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
