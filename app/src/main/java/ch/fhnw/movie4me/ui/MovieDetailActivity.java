package ch.fhnw.movie4me.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Review;
import ch.fhnw.movie4me.dto.Video;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.ui.search.AddMovieList2;
import ch.fhnw.movie4me.util.ImageUtils;
import androidx.fragment.app.Fragment;

public class MovieDetailActivity extends AppCompatActivity {

    private TheMovieDbClient client;
    private Movie movie;
    private ActionBar actionBar;
    ImageView imgMovie = null;
    private List<Cast> lsreview;
    private String strVideo;
    private List<Video> video;
    private String strMovieLink;
    private int movieId;
    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_movie_detail);

        this.client = TheMovieDbClient.getInstance();

        Intent intent = getIntent();

        // Check if Intent is not empty and has data
        if (intent != null && intent.hasExtra("MOVIE_ID")) {
            // Get data
            movieId = intent.getIntExtra("MOVIE_ID", -1);
            this.movie = this.client.getMovie(movieId);


            List<Cast> lsreview = this.client.getMovieCast(movieId);

            video = this.client.getMovieVideos(movieId);
            strVideo = video.get(0).getKey();



            for(int i = 0; i < lsreview.size(); i++){
            System.out.println("Review: " + lsreview.get(i).getName());
            }
            /**System.out.println("YT:" + strVideo);

            System.out.println(video.get(0).getSite().equals("YouTube"));
            System.out.println(video.get(0).getSite());
            String testing = "YouTube";
            String type = video.get(0).getType();
            System.out.println(testing);**/
            if(video.get(0).getSite().equals("YouTube")){
                strVideo = "https://www.youtube.com/watch?v=" + strVideo;
                System.out.println(strVideo);
            }else{
                strVideo = "no trailer available";
            }






            /**Button btnShare = (Button) findViewById(R.id.btnShare);
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override


                public void onClick(View v) {

                }


            });

            Button btnDetails = (Button) findViewById(R.id.btnDetails);
            btnDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            Button btnAddList = (Button) findViewById(R.id.btnAddList);
            btnAddList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }

            });**/




            final TextView txTitle = findViewById(R.id.txTitleList);
            txTitle.setText(this.movie.getTitle());


            imgMovie = findViewById(R.id.imgMovie);
            Bitmap bitmap = ImageUtils.getBitmapFromUrl(movie.getPosterUrl());
            if (bitmap != null) {

                imgMovie.setImageBitmap(bitmap);
            }


            final TextView txDescription = findViewById(R.id.txDescription);
            txDescription.setText(this.movie.getOverview());

            final TextView txRelease = findViewById(R.id.txRelease);
            txRelease.setText("Release: " + this.movie.getReleaseDateFormatted());

            final TextView txLink = findViewById(R.id.txLink);
            txLink.setText("Average Rating: " + this.movie.getVoteAvg());





        }
    }


    public void openAddList() {
        Intent intent = new Intent(MovieDetailActivity.this, AddMovieList2.class);
        startActivity(intent);
    }


    public void openDetails() {
        Intent intent = new Intent(MovieDetailActivity.this, MovieActors.class);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_LIST_ID, movieId);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //https://developer.android.com/guide/topics/ui/menus#java
        List<Movie> movies = null;

        switch (item.getItemId()) {
            case R.id.miAddToList:
                this.actionBar.setTitle("Add to List");
                //movies = this.client.getPopular();
                openAddList();
                break;
            case R.id.miShare:
                this.actionBar.setTitle("Share");
                //movies = this.client.getNowPlaying();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                String shareBody = "Look i found this movie on Movie4Me for you: ";
                String shareMessage = "Take a look at this awesome movie! " + this.movie.getTitle() + ": " + strVideo;
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareMessage);
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(shareIntent, "Medium to share with"));
                break;
            case R.id.miActors:
                this.actionBar.setTitle("Actors");
                //movies = this.client.getUpcoming();
                openDetails();
                break;
        }

        if (movies != null) {
            //this.resetAdapter(movies);
            return true;
        } else {
            return false;
        }


    }

    private void resetAdapter(List<Movie> movies) {
        /**MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(this.getContext(), movies);
        adapter.setOnMovieClickListener(this);
        adapter.setOnMovieLongClickListener(this);
        lvMovies.setAdapter(adapter);**/
    }
}
