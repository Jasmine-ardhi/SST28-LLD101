import java.util.*;

public class ShowService {

    Map<String, Show> shows = new HashMap<>();

    public void addShow(Show show) {
        shows.put(show.showId, show);

        System.out.println("Show added: " + show.movie.name +
                " | Screen: " + show.screen.screenId +
                " | Time: " + show.startTime);
    }
}