import java.util.*;

public class MovieService {

    Map<String, Movie> movies = new HashMap<>();

    public void addMovie(Movie movie, User admin) {
        if (admin.role != UserRole.ADMIN) return;
        movies.put(movie.movieId, movie);
        System.out.println("Movie added: " + movie.name);
    }
}