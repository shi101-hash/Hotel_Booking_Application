package net.engineeringdigest.hotelbooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "hotel_rooms")
public class HotelRoom {
    @Id
    private String id;
    private String roomNumber;
    private String type;
    private boolean available;

}


