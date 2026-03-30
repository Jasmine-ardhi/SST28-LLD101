import java.time.LocalDateTime;
import java.util.*;

public class Show {
    public String showId;
    public Movie movie;
    public Screen screen;
    public LocalDateTime startTime;
    public LocalDateTime endTime;
    public List<ShowSeat> seats = new ArrayList<>();
}