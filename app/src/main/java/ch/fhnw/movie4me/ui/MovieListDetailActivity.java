package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieListDetailActivity extends AppCompatActivity {

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private MovieList movieList;
    private List<MovieListDetail> movieListDetails;
    private List<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movielist_detail);

        this.theMovieDbClient = TheMovieDbClient.getInstance();
        this.movieListDb = new MovieListDb();
        this.movieListDetailDb = new MovieListDetailDb();
        this.movies = new ArrayList<>();

        Intent intent = getIntent();
        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra("MOVIE_LIST_ID")) {
            // Get data
            int movieListId = intent.getIntExtra("MOVIE_LIST_ID", -1);
            this.movieList = this.movieListDb.get(movieListId);
            this.movieListDetails = this.movieListDetailDb.getByMovieListId(movieListId);

            for(MovieListDetail detail : this.movieListDetails) {
                Movie movie = this.theMovieDbClient.getMovie((int)detail.getMovieId());
                this.movies.add(movie);
            }
        }

    }
}
