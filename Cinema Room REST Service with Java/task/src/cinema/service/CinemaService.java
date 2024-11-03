package cinema.service;

import cinema.model.Cinema;
import cinema.model.Seat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CinemaService {
    private static final int ROWS = 9;
    private static final int COLUMNS = 9;
    private static final int STANDARD_PRICE = 8;
    private static final int PREMIUM_PRICE = 10;
    List<Seat> seats = new ArrayList<>();

    Map<Integer, List<Integer>> request = new HashMap<>();
    List<Integer> column = new ArrayList<>();

    Cinema cinema;


    public Cinema getSeats() {
        seats.clear();  // Clear seats to avoid duplicates
        for (int row = 1; row <= ROWS; row++) {
            int price = row <= 4 ? PREMIUM_PRICE : STANDARD_PRICE;
            for (int column = 1; column <= COLUMNS; column++) {
                seats.add(new Seat(row, column, price));
            }
        }
        cinema = new Cinema(ROWS, COLUMNS, seats);
        return cinema;
    }

    public Seat purchaseTicket(int row, int column) {
        // Ensure the row exists in the map, if not, initialize a new list for that row
        List<Integer> bookedColumns = request.computeIfAbsent(row, k -> new ArrayList<>());

        // Check if the seat has already been booked
        if (bookedColumns.contains(column)) {
            return null; // Seat already booked
        }

        // Add the column to the booked list and update the map
        bookedColumns.add(column);

        // Determine the price based on the row
        int price = row <= 4 ? PREMIUM_PRICE : STANDARD_PRICE;
        return new Seat(row, column, price);
    }


}
