package ch.fhnw.movie4me;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ch.fhnw.movie4me.db.DbAccess;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MovieListDetailDbTest {

    @Test
    public void testCRUD() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DbAccess.init(appContext);

        //create a new movielist
        MovieList movieList = new MovieList();
        movieList.setName("Detail Test");

        MovieListDb movieListDb = new MovieListDb();
        movieListDb.save(movieList);


        //test details
        long fightClubId = 550;
        long sonicId = 454626;

        MovieListDetail movieListDetail = new MovieListDetail();
        movieListDetail.setMovieListId(movieList.getId());
        movieListDetail.setMovieId(fightClubId); //Fight Club

        //insert
        MovieListDetailDb movieListDetailDb = new MovieListDetailDb();
        boolean success = movieListDetailDb.save(movieListDetail);
        assertTrue(success);

        //read all
        List<MovieListDetail> movieListDetails = movieListDetailDb.getByMovieListId(movieList.getId());
        assertTrue(movieListDetails.size() > 0);

        //read by id
        movieListDetail = movieListDetailDb.get(movieListDetail.getId());
        assertNotNull(movieListDetail);

        //update
        movieListDetail.setMovieId(sonicId);
        success = movieListDetailDb.save(movieListDetail);
        assertTrue(success);

        //read by id
        movieListDetail = movieListDetailDb.get(movieListDetail.getId());
        assertEquals(movieListDetail.getMovieId(), sonicId);

        //delete
        success = movieListDetailDb.delete(movieListDetail.getId());
        assertTrue(success);
    }
}
