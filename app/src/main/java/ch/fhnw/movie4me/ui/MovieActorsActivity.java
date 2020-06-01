package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class MovieActorsActivity extends AppCompatActivity {


    public static final String EXTRA_MOVIE_ID = "ch.fhnw.movie4me.ui.MovieActorsActivity.MOVIE_ID";
    private TheMovieDbClient client;
    private int movieId;
    private String strAllActors = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_actors);

        this.client = TheMovieDbClient.getInstance();

        Intent intent = getIntent();

        movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);

        List<Cast> lsreview = this.client.getMovieCast(movieId);



        if(lsreview == null) {
            System.out.println("No Cast - Array null");
            System.out.println(movieId);
            strAllActors = "No Actors found for this movie";
        }else{

            for (int i = 0; i < lsreview.size(); i++) {
                System.out.println("Review: " + lsreview.get(i).getName());
                strAllActors = strAllActors + lsreview.get(i).getName() + "\n";
            }
        }



        final TextView txTitle = findViewById(R.id.txTitleActors);
        txTitle.setText("Actors in this movie");

        final TextView txAllActors = findViewById(R.id.txAllActors);
        txAllActors.setText(strAllActors);




    }

}
