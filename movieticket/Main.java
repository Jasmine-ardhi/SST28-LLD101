import java.time.LocalDateTime;
import java.util.*;

public class Main {

    public static void main(String[] args) {

        TheaterService theaterService = new TheaterService();
        MovieService movieService = new MovieService();
        ShowService showService = new ShowService();
        BookingService bookingService = new BookingService();

        // Users
        User admin = new User();
        admin.userId = "U1";
        admin.role = UserRole.ADMIN;

        User customer = new User();
        customer.userId = "U2";
        customer.role = UserRole.CUSTOMER;

        // Theater
        Theater theater = new Theater();
        theater.theaterId = "T1";
        theater.name = "PVR";
        theater.city = "Bangalore";

        theaterService.addTheater(theater, admin);

        // Screen
        Screen screen = new Screen();
        screen.screenId = "S1";

        for (int i = 1; i <= 10; i++) {
            Seat seat = new Seat();
            seat.seatId = "Seat-" + i;
            seat.basePrice = 200;

            if (i <= 3) seat.type = SeatType.SILVER;
            else if (i <= 7) seat.type = SeatType.GOLD;
            else seat.type = SeatType.DIAMOND;

            screen.seats.add(seat);
        }

        theaterService.addScreen("T1", screen, admin);

        // Movie
        Movie movie = new Movie();
        movie.movieId = "M1";
        movie.name = "Inception";
        movie.duration = 120;

        movieService.addMovie(movie, admin);

        // Show
        Show show = new Show();
        show.showId = "SH1";
        show.movie = movie;
        show.screen = screen;
        show.startTime = LocalDateTime.now();
        show.endTime = show.startTime.plusMinutes(movie.duration);

        for (Seat seat : screen.seats) {
            ShowSeat ss = new ShowSeat();
            ss.seat = seat;
            ss.seatId = seat.seatId;
            ss.status = SeatStatus.AVAILABLE;
            show.seats.add(ss);
        }

        showService.addShow(show);

        // Booking 1
        List<ShowSeat> seats1 = new ArrayList<>();
        seats1.add(show.seats.get(0));
        seats1.add(show.seats.get(5));
        seats1.add(show.seats.get(9));

        Booking booking1 = new Booking();
        booking1.bookingId = "B1";
        booking1.user = customer;
        booking1.show = show;

        boolean locked = bookingService.lockSeats(customer, seats1);

        if (locked) {
            bookingService.confirmBooking(booking1, seats1, new UPIPayment());
            System.out.println("Total Paid: " + booking1.payment.amount);
        }

        // Booking 2 (after occupancy)
        System.out.println("\n--- Second Booking ---");

        List<ShowSeat> seats2 = new ArrayList<>();
        seats2.add(show.seats.get(1));
        seats2.add(show.seats.get(2));

        Booking booking2 = new Booking();
        booking2.bookingId = "B2";
        booking2.user = customer;
        booking2.show = show;

        bookingService.lockSeats(customer, seats2);
        bookingService.confirmBooking(booking2, seats2, new CreditCardPayment());

        System.out.println("Total Paid: " + booking2.payment.amount);
    }
}