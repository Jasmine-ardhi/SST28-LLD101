import java.util.*;

public class BookingService {

    // 🔒 Lock Seats
    public synchronized boolean lockSeats(User user, List<ShowSeat> seats) {

        for (ShowSeat s : seats) {
            if (s.status != SeatStatus.AVAILABLE) {
                return false;
            }
        }

        for (ShowSeat s : seats) {
            s.status = SeatStatus.LOCKED;
        }

        return true;
    }

    // 💰 Dynamic Pricing
    private double calculatePrice(Show show, List<ShowSeat> seats) {

        int totalSeats = show.seats.size();
        int bookedSeats = 0;

        for (ShowSeat s : show.seats) {
            if (s.status == SeatStatus.BOOKED) bookedSeats++;
        }

        double occupancy = (double) bookedSeats / totalSeats;

        double total = 0;

        for (ShowSeat s : seats) {

            double base = s.seat.basePrice;

            // Seat type multiplier
            if (s.seat.type == SeatType.GOLD) base *= 1.5;
            else if (s.seat.type == SeatType.DIAMOND) base *= 2;

            // Dynamic pricing multiplier
            if (occupancy > 0.5) base *= 1.5;

            total += base;
        }

        return total;
    }

    // ✅ Confirm Booking
    public synchronized boolean confirmBooking(Booking booking,
                                               List<ShowSeat> seats,
                                               PaymentStrategy strategy) {

        for (ShowSeat s : seats) {
            if (s.status != SeatStatus.LOCKED) {
                return false;
            }
        }

        double price = calculatePrice(booking.show, seats);

        strategy.pay(price);

        for (ShowSeat s : seats) {
            s.status = SeatStatus.BOOKED;
        }

        booking.seats = seats;

        Payment payment = new Payment();
        payment.amount = price;
        booking.payment = payment;

        System.out.println("Booking Confirmed!");
        return true;
    }
}