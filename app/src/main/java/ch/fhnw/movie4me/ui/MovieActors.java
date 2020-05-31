package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieActors extends AppCompatActivity {


    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";
    private TheMovieDbClient client;
    private MovieListDb movieListDb;
    private List<String> lsActors;
    private int movieId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_actors);

        this.client = TheMovieDbClient.getInstance();
        this.movieListDb = new MovieListDb();

        Intent intent = getIntent();

        movieId = intent.getIntExtra(EXTRA_MOVIE_LIST_ID, -1);

        List<Cast> lsreview = this.client.getMovieCast(movieId);

        for(int i = 0; i < lsreview.size(); i++){
            System.out.println("Review: " + lsreview.get(i).getName());
        }

        final TextView txTitle = findViewById(R.id.txTitleList);
        txTitle.setText("Actors in this movie");



    }

}
