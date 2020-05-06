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

        System.out.println("# of popular movies: " + movies.size());
    }

    @Test
    public void testNowPlaying() {
        List<Movie> movies = this.client.getNowPlaying();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        System.out.println("# of now playing movies: " + movies.size());
    }

    @Test
    public void testUpcoming() {
        List<Movie> movies = this.client.getUpcoming();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        System.out.println("# of upcoming movies: " + movies.size());
    }

    @Test
    public void testTopRated() {
        List<Movie> movies = this.client.getTopRated();

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        System.out.println("# of top rated movies: " + movies.size());
    }

    @Before
    public void init() {
        Properties properties = new Properties();

        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            if(classLoader != null) {
                InputStream stream = classLoader.getResourceAsStream("config-test.properties");
                if (stream != null) {
                    properties.load(stream);
                }
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
