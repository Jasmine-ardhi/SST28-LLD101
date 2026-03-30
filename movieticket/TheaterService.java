import java.util.*;

public class TheaterService {

    Map<String, Theater> theaters = new HashMap<>();

    public void addTheater(Theater theater, User admin) {
        if (admin.role != UserRole.ADMIN) return;
        theaters.put(theater.theaterId, theater);
        System.out.println("Theater added: " + theater.name);
    }

    public void addScreen(String theaterId, Screen screen, User admin) {
        if (admin.role != UserRole.ADMIN) return;
        Theater t = theaters.get(theaterId);
        t.screens.add(screen);
        System.out.println("Screen added to theater: " + t.name);
    }
}