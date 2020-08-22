package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movie.OnMovieClickListener;
import ch.fhnw.movie4me.adapter.movie.OnMovieLongClickListener;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieListDetailActivity extends AppCompatActivity implements OnMovieClickListener, OnMovieLongClickListener {

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MovieListDetailActivity.MOVIE_LIST_ID";

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private MovieList movieList;
    private List<MovieListDetail> movieListDetails;
    private List<Movie> movies;
    private ActionBar actionBar;
    private RecyclerView lvMoviesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long movieListId = -1;
        Intent intent = getIntent();
        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_LIST_ID)) {
            movieListId = intent.getLongExtra(EXTRA_MOVIE_LIST_ID, -1);
        }

        if (movieListId <= 0) {
            finish();
        }

        setContentView(R.layout.activity_movielist_detail);

        this.theMovieDbClient = TheMovieDbClient.getInstance();
        this.movieListDb = new MovieListDb();
        this.movieListDetailDb = new MovieListDetailDb();
        this.movies = new ArrayList<>();
        this.movieList = this.movieListDb.get(movieListId);

        this.actionBar = getSupportActionBar();
        if (this.actionBar != null) {
            this.actionBar.setDisplayHomeAsUpEnabled(false);
        }

        final TextView textListDescription = findViewById(R.id.txMovieListDescription);
        if (this.movieList.getDescription() != null && this.movieList.getDescription().length() > 0) {
            textListDescription.setText(this.movieList.getDescription());
        } else {
            textListDescription.setVisibility(View.INVISIBLE);
        }

        this.lvMoviesList = findViewById(R.id.rvMovieListDetail);
        this.lvMoviesList.setHasFixedSize(true);
        this.lvMoviesList.setLayoutManager(new LinearLayoutManager(this));

        refreshData(movieListId);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.refreshData(this.movieList.getId());
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
                this.movieListDb.delete(this.movieList.getId());
                finish();
                break;
            case R.id.miEditList:
                openListEditActivity();
                break;
        }

        return false;
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Remove " + movie.getTitle() + " from list?");

        builder.setPositiveButton("Yes", (dialog, which) -> {
            ArrayList<Long> idToDelete = new ArrayList<>();
            for (MovieListDetail detail : movieListDetails) {
                if (detail.getMovieId() == movie.getId()) {
                    idToDelete.add(detail.getId());
                }
            }

            for (Long id : idToDelete) {
                movieListDetailDb.delete(id);
            }

            if (idToDelete.size() > 0) {
                Toast.makeText(MovieListDetailActivity.this, "Movie " + movie.getTitle() + " removed", Toast.LENGTH_LONG).show();
                refreshData(movieList.getId());
            }
        });

        builder.setNegativeButton("No", (dialog, which) -> {

        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void refreshData(long movieListId) {
        this.movieList = this.movieListDb.get(movieListId);
        this.actionBar.setTitle(this.movieList.getName());
        this.movieListDetails = this.movieListDetailDb.getByMovieListId(movieListId);

        this.movies.clear();
        for (MovieListDetail detail : this.movieListDetails) {
            Movie movie = this.theMovieDbClient.getMovie((int) detail.getMovieId());
            this.movies.add(movie);
        }

        resetAdapter(this.movies);
    }

    private void resetAdapter(List<Movie> movies) {
        MovieRecyclerViewAdapter itemArrayAdapter = new MovieRecyclerViewAdapter(this, movies);
        itemArrayAdapter.setOnMovieClickListener(this);
        itemArrayAdapter.setOnMovieLongClickListener(this);
        this.lvMoviesList.setAdapter(itemArrayAdapter);
    }

    private void openListEditActivity() {
        Intent intent = new Intent(this, MovieListEditActivity.class);
        intent.putExtra(MovieListEditActivity.EXTRA_MOVIE_LIST_ID, (int) movieList.getId());
        startActivity(intent);
    }

}
