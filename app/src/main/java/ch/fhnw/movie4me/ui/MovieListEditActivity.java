package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;

public class MovieListEditActivity extends AppCompatActivity {

    private MovieListDb movieListDb;
    private MovieList movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movielist_edit);

        this.movieListDb = new MovieListDb();

        Intent intent = getIntent();
        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra("MOVIE_LIST_ID")) {
            // Get data
            int movieListId = intent.getIntExtra("MOVIE_LIST_ID", -1);
            this.movieList = this.movieListDb.get(movieListId);
        }
    }
}
