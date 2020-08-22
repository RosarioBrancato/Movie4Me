package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.actor.ActorRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieActorsActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "ch.fhnw.movie4me.ui.MovieActorsActivity.MOVIE_ID";

    private RecyclerView rvActors;

    private Movie movie;
    private List<Cast> castList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_actors);

        int movieId = -1;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_ID)) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
        }

        TheMovieDbClient client = TheMovieDbClient.getInstance();
        this.movie = client.getMovie(movieId);
        this.castList = client.getMovieCast(movieId);

        this.setUpActionBar();

        this.rvActors = findViewById(R.id.rvActors);
        this.rvActors.setHasFixedSize(true);
        this.rvActors.setLayoutManager(new LinearLayoutManager(this));
        ActorRecyclerViewAdapter adapter = new ActorRecyclerViewAdapter(this, this.castList);
        rvActors.setAdapter(adapter);
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(this.movie.getTitle());
        }
    }

}
