package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

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

    public static final String MOVIE_ID = "ch.fhnw.movie4me.ui.AddMovieToListActivity.MOVIE_ID";

    private Movie movie;

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private List<MovieList> movieLists;
    private RecyclerView recyclerView;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_movie_to_list);


        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MOVIE_ID)) {

            this.theMovieDbClient = TheMovieDbClient.getInstance();
            this.movieListDb = new MovieListDb();
            this.movieListDetailDb = new MovieListDetailDb();

            movieLists = movieListDb.getAll();

            int movieId = intent.getIntExtra(MOVIE_ID, -1);
            this.movie = this.theMovieDbClient.getMovie(movieId);

            final TextView txTitleList = findViewById(R.id.tvTitle);
            txTitleList.setText("Available Lists");

            recyclerView = findViewById(R.id.rvMovielists);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);


            MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(this, movieLists);
            adapter.setOnMovieListClickListener(this);
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(adapter);

        } else {
            finish();
        }
    }

    @Override
    public void onMovieListClickListener(MovieList movieList) {
        if (movie != null) {
            MovieListDetail detail = new MovieListDetail();
            detail.setMovieId(this.movie.getId());
            detail.setMovieListId(movieList.getId());
            this.movieListDetailDb.save(detail);

            Toast.makeText(this, "Added to list", Toast.LENGTH_LONG).show();

            finish();
        }
    }
}




