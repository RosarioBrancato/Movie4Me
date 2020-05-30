package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.MovieArrayAdapter;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieListDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private MovieList movieList;
    private List<MovieListDetail> movieListDetails;
    private List<Movie> movies;

    private Button btnEditList;
    private Button btnDeleteMovies;

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
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_LIST_ID)) {
            // Get data
            int movieListId = intent.getIntExtra(EXTRA_MOVIE_LIST_ID, -1);
            this.movieList = this.movieListDb.get(movieListId);
            this.movieListDetails = this.movieListDetailDb.getByMovieListId(movieListId);

            for (MovieListDetail detail : this.movieListDetails) {
                Movie movie = this.theMovieDbClient.getMovie((int) detail.getMovieId());
                this.movies.add(movie);
            }

            final TextView textTitle = findViewById(R.id.txMovieListDescription);
            textTitle.setText("description placeholder" /* + this.movieList.getDescription()*/);

            final RecyclerView lvMoviesList = findViewById(R.id.rvMovieListDetail);
            lvMoviesList.setHasFixedSize(true);
            lvMoviesList.setLayoutManager(new LinearLayoutManager(this));

            ActionBar actionBar = getSupportActionBar();
            actionBar.setTitle("My List: Listname");
            actionBar.setDisplayHomeAsUpEnabled(false);

            this.theMovieDbClient = TheMovieDbClient.getInstance();
            List<Movie> movies = this.theMovieDbClient.searchMovie("Star Wars");

            MovieArrayAdapter itemArrayAdapter = new MovieArrayAdapter(this, movies);
            lvMoviesList.setAdapter(itemArrayAdapter);

        }

    }

    public void click() {
        Intent intent = new Intent(this, MovieListEditActivity.class);
        startActivity(intent);
    }
}
