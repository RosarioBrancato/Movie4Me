package ch.fhnw.movie4me;

import android.content.Context;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import ch.fhnw.movie4me.db.DbAccess;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.MovieList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class MovieListDbTest {

    @Test
    public void testCRUD() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        DbAccess.init(appContext);

        String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());

        MovieList movieList = new MovieList();
        movieList.setName("My List " + now);
        movieList.setDescription("TEST LIST of " + now);

        //insert
        MovieListDb movieListDb = new MovieListDb();
        boolean success = movieListDb.save(movieList);
        assertTrue(success);

        //read all
        List<MovieList> movieLists = movieListDb.getAll();
        assertTrue(movieLists.size() > 0);

        //read by id
        movieList = movieListDb.get(movieList.getId());
        assertNotNull(movieList);

        //update
        String newName = movieList.getName() + " EDIT";
        movieList.setName(newName);
        success = movieListDb.save(movieList);
        assertTrue(success);

        //read by id
        movieList = movieListDb.get(movieList.getId());
        assertEquals(movieList.getName(), newName);

        //delete
        success = movieListDb.delete(movieList.getId());
        assertTrue(success);
    }
}
