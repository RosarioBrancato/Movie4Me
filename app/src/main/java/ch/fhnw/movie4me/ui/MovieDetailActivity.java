package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieDetailActivity extends AppCompatActivity {

    private TheMovieDbClient client;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_detail);

        this.client = TheMovieDbClient.getInstance();

        Intent intent = getIntent();

        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra("MOVIE_ID")) {
            // Get data
            int movieId = intent.getIntExtra("MOVIE_ID", -1);
            this.movie = this.client.getMovie(movieId);

            final TextView txTitle = findViewById(R.id.txTitle);
            txTitle.setText("TEMP - Movie title: " + this.movie.getTitle());
        }
    }
}
