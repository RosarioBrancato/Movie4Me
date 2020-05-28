package ch.fhnw.movie4me.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.util.ImageUtils;
import retrofit2.http.Url;

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

            
            Button btnShare =(Button) findViewById(R.id.btnShare);
            btnShare.setOnClickListener(new View.OnClickListener(){
                @Override



                public void onClick(View v){
                    Intent shareIntent = new Intent (Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareBody = "Look i found this movie on Movie4Me for you: ";
                    String shareMessage = "Take a look at this awesome movie!";
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareMessage);
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                    startActivity(Intent.createChooser(shareIntent, "Medium to share with"));
                }


            });

            final TextView txTitle = findViewById(R.id.txTitle);
            txTitle.setText("Movie title: " + this.movie.getTitle());


            //Bitmap bitmap = ImageUtils.getBitmapFromUrl(movie.getPosterUrl());
            //if(bitmap != null){
            //    imgMovie.setImageBitmap(bitmap);
            //}


            final TextView txDescription = findViewById(R.id.txDescription);
            txDescription.setText("Description: " + this.movie.getOverview());

            final TextView txRelease = findViewById(R.id.txRelease);
            txRelease.setText("Release: " + this.movie.getReleaseDateFormatted());

            final TextView txLink = findViewById(R.id.txLink);
            txLink.setText("Movie Link: " + this.movie.getPosterUrl());


            final TextView txActors = findViewById(R.id.txActors);
            txActors.setText("Movie Actors: " + this.movie.getOverview());




            //ImageView imgMovie = (ImageView) findViewById(R.id.imgMovie);
            //imgMovie.setImageResource();

            //final TextView txLink = findViewById(R.id.txLink);
            //txLink.setText(this.movie.getPosterUrl());




        }
    }
}
