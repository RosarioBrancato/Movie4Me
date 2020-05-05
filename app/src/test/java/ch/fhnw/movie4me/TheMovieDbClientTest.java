package ch.fhnw.movie4me;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TheMovieDbClientTest {

    private TheMovieDbClient client;

    @Test
    public void testGetMovie() {
        int id = 550; //Fight Club

        Movie movie = this.client.getMovie(id);

        assertNotNull(movie);
        assertTrue(movie.getTitle().length() > 0);
        assertEquals(movie.getTitle(), "Fight Club");

        System.out.println(movie.getTitle() + " " + movie.getReleaseDateFormatted());
    }

    @Test
    public void testGetPopular() {
        List<Movie> movies = this.client.getPopular();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        for (Movie movie : movies) {
            System.out.println(movie.getTitle() + " " + movie.getReleaseDateFormatted());
        }
    }

    @Before
    public void init() {
        Properties properties = new Properties();
        try {
            InputStream stream = this.getClass().getClassLoader().getResourceAsStream("config-test.properties");
            if(stream != null) {
                properties.load(stream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        String apiKey = properties.getProperty("themoviedb_apy_key");
        this.client = new TheMovieDbClient(apiKey);
    }

    @After
    public void dispose() {
        this.client = null;
    }
}
