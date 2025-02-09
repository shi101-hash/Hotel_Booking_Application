package net.engineeringdigest.hotelbooking.controller;



import net.engineeringdigest.hotelbooking.model.HotelRoom;
import net.engineeringdigest.hotelbooking.model.Reservation;
import net.engineeringdigest.hotelbooking.service.HotelService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/hotel")
public class HotelController {
    private final HotelService hotelService;

    public HotelController(HotelService hotelService) {
        this.hotelService = hotelService;
    }

    @GetMapping("/rooms")
    public String getAllRooms(Model model) {
        List<HotelRoom> rooms = hotelService.getAllRooms();
        model.addAttribute("rooms", rooms);
        return "rooms";
    }

    @GetMapping("/book")
    public String showBookingForm(Model model) {
        model.addAttribute("reservation", new Reservation());
        return "book_room";
    }

    @PostMapping("/book")
    public String bookRoom(@ModelAttribute Reservation reservation, Model model) {
        Reservation booked = hotelService.bookRoom(reservation);
        if (booked != null) {
            model.addAttribute("message", "Room booked successfully!");
        } else {
            model.addAttribute("message", "Room is not available!");
        }
        return "booking_result";
    }

    @GetMapping("/reservations")
    public String getReservations(Model model) {
        List<Reservation> reservations = hotelService.getReservations();
        model.addAttribute("reservations", reservations);
        return "reservations";
    }
}
