package net.engineeringdigest.hotelbooking.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "reservations")
public class Reservation {
    @Id
    private String id;
    private String customerName;
    private String roomNumber;
    private String checkOutDate;

}

