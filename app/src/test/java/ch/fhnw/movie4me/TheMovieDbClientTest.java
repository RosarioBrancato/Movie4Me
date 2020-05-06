package ch.fhnw.movie4me;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Review;
import ch.fhnw.movie4me.dto.Video;
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
    public void testGetMovieVideos() {
        int id = 454626; //Sonic the Hedgehog

        List<Video> videos = this.client.getMovieVideos(id);

        assertNotNull(videos);
        assertTrue(videos.size() > 0);

        System.out.println("# of movie videos: " + videos.size());
    }

    @Test
    public void testGetMovieCast() {
        int id = 454626; //Sonic the Hedgehog

        List<Cast> cast = this.client.getMovieCast(id);

        assertNotNull(cast);
        assertTrue(cast.size() > 0);

        System.out.println("# of movie cast: " + cast.size());
    }

    @Test
    public void testGetMovieReviews() {
        int id = 454626; //Sonic the Hedgehog
        List<Review> reviews = this.client.getMovieReviews(id);

        assertNotNull(reviews);
        assertTrue(reviews.size() > 0);

        System.out.println("# of movie reviews: " + reviews.size());
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

    @Test
    public void testSearchMovie() {
        List<Movie> movies = this.client.searchMovie("Star Wars");

        assertNotNull(movies);
        assertTrue(movies.size() > 0);

        System.out.println("Movies found: " + movies.size());
    }

    @Before
    public void init() {
        Properties properties = new Properties();

        try {
            ClassLoader classLoader = this.getClass().getClassLoader();
            if (classLoader != null) {
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
