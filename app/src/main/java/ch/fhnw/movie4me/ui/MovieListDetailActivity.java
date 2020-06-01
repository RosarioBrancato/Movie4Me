package ch.fhnw.movie4me.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movie.OnMovieClickListener;
import ch.fhnw.movie4me.adapter.movie.OnMovieLongClickListener;
import ch.fhnw.movie4me.adapter.movielist.OnMovieListClickListener;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieListDetailActivity extends AppCompatActivity implements OnMovieClickListener, OnMovieLongClickListener {

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private MovieList movieList;
    private List<MovieListDetail> movieListDetails;
    private List<Movie> movies;
    private ActionBar actionBar;


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
            actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(false);

            long movieListId = intent.getLongExtra(EXTRA_MOVIE_LIST_ID, -1);
            refreshData(movieListId);

            //final TextView textListDescription = findViewById(R.id.txMovieListDescription);
           // textListDescription.setText(this.movieList.getDescription());
        }

    }

    private void resetAdapter(List<Movie> movies) {
        RecyclerView lvMoviesList = findViewById(R.id.rvMovieListDetail);
        lvMoviesList.setHasFixedSize(true);
        lvMoviesList.setLayoutManager(new LinearLayoutManager(this));
        MovieRecyclerViewAdapter itemArrayAdapter = new MovieRecyclerViewAdapter(this, movies);
        itemArrayAdapter.setOnMovieClickListener(this);
        itemArrayAdapter.setOnMovieLongClickListener(this);
        lvMoviesList.setAdapter(itemArrayAdapter);
        TextView textListDescription = findViewById(R.id.txMovieListDescription);
        textListDescription.setText(this.movieList.getDescription());
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.refreshData(this.movieList.getId());
    }


    private void refreshData(long movieListId) {
        this.movieList = this.movieListDb.get(movieListId);
        actionBar.setTitle("My List: " + this.movieList.getName());
        this.movieListDetails = this.movieListDetailDb.getByMovieListId(movieListId);

        this.movies.clear();
        for (MovieListDetail detail : this.movieListDetails) {
            Movie movie = this.theMovieDbClient.getMovie((int) detail.getMovieId());
            this.movies.add(movie);
        }

        resetAdapter(this.movies);
    }

    public void click() {
        Intent intent = new Intent(this, MovieListEditActivity.class);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_list_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //https://developer.android.com/guide/topics/ui/menus#java

        switch (item.getItemId()) {
            case R.id.miDeleteList:
                Toast.makeText(this, "Delete List.", Toast.LENGTH_LONG).show();
                //this.movieListDb.delete();
                break;
            case R.id.miEditList:
                openListEditActivity();
                break;
            case R.id.miRemoveMovies:
                Toast.makeText(this, "To remove movies from the list LongPress on them.", Toast.LENGTH_LONG).show();
                // allow deletion with onMovieLongClickListener ?
                // resetAdapter(movies);
                break;
        }

        if (movies != null) {
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onMovieClickListener(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(this, MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            startActivity(intent);
        }
    }

    @Override
    public void onMovieLongClickListener(Movie movie) {
        // if RemoveMovies has been activated (set variable?)
        // delete this movie from the list
        Toast.makeText(this, "Long click.", Toast.LENGTH_LONG).show();
    }

    public void openListEditActivity() {
        Intent intent = new Intent(this, MovieListEditActivity.class);
        //intent.putExtra(EXTRA_MOVIE_LIST_ID, 4);
        intent.putExtra(EXTRA_MOVIE_LIST_ID, (int) movieList.getId());
        startActivity(intent);
    }
}
