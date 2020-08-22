package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movielist.MovieListRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movielist.OnMovieListClickListener;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class AddMovieToListActivity extends AppCompatActivity implements OnMovieListClickListener {

    public static final String EXTRA_MOVIE_ID = "ch.fhnw.movie4me.ui.AddMovieToListActivity.MOVIE_ID";

    private int movieId = -1;

    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_to_list);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_ID)) {
            this.movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
        }

        if (this.movieId <= 0) {
            finish();
        }

        this.movieListDb = new MovieListDb();
        this.movieListDetailDb = new MovieListDetailDb();

        this.setUpActionBar();

        final RecyclerView recyclerView = findViewById(R.id.rvMovieLists);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<MovieList> movieLists = movieListDb.getAll();
        MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(this, movieLists);
        adapter.setOnMovieListClickListener(this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onMovieListClickListener(MovieList movieList) {
        if (this.movieId > 0) {
            MovieListDetail detail = new MovieListDetail();
            detail.setMovieId(this.movieId);
            detail.setMovieListId(movieList.getId());
            this.movieListDetailDb.save(detail);

            Toast.makeText(this, "Added to list", Toast.LENGTH_LONG).show();

            finish();
        }
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Add to a List");
        }
    }
}




