package cinema.controller;

import cinema.model.Cinema;
import cinema.model.Seat;
import cinema.service.CinemaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class CinemaController {

    private final CinemaService cinemaService;

    @Autowired
    public CinemaController(CinemaService cinemaService) {
        this.cinemaService = cinemaService;
    }

    @GetMapping("/seats")
    public Cinema getSeats() {
        return cinemaService.getSeats();
    }

    @PostMapping("/purchase")
    public ResponseEntity<?> purchaseTicket(@RequestBody Seat request) {
        int row = request.getRow();
        int column = request.getColumn();

        // Validate row and column bounds first (assuming max rows and columns are 9)
        if (row < 1 || row > 9 || column < 1 || column > 9) {
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "The number of a row or a column is out of bounds!"
            ));
        }

        // Check if the seat is already booked
        Seat seat = cinemaService.purchaseTicket(row, column);
        if (seat == null) {
            // Return 400 bad request if the ticket has already been purchased
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "The ticket has been already purchased!"
            ));
        }

        // Return the seat details if the booking is successful
        return ResponseEntity.ok(seat);
    }

}
