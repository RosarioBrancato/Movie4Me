package ch.fhnw.movie4me.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Review;
import ch.fhnw.movie4me.dto.Video;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.util.ImageUtils;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";

    private TheMovieDbClient client;
    private Movie movie;
    private List<Review> reviews;
    private List<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        int movieId = -1;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("MOVIE_ID")) {
            movieId = intent.getIntExtra("MOVIE_ID", -1);
        }

        if (movieId <= 0) {
            finish();
        }

        this.client = TheMovieDbClient.getInstance();
        this.movie = this.client.getMovie(movieId);
        this.reviews = this.client.getMovieReviews(movieId);
        this.videos = this.client.getMovieVideos(movieId);

        this.setUpActionBar();

        final TextView txTitle = findViewById(R.id.tvTitle);
        txTitle.setText(this.movie.getTitle());

        final ImageView imgMovie = findViewById(R.id.ivMovie);
        Bitmap bitmap = ImageUtils.getBitmapFromUrl(movie.getPosterUrl());
        if (bitmap != null) {
            imgMovie.setImageBitmap(bitmap);
        }

        final TextView txDescription = findViewById(R.id.tvDescription);
        txDescription.setText(this.movie.getOverview());

        final TextView txRelease = findViewById(R.id.tvRelease);
        txRelease.setText("Release: " + this.movie.getReleaseDateFormatted());

        final TextView txLink = findViewById(R.id.tvLink);
        txLink.setText("Average Rating: " + this.movie.getVoteAvg());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movie_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //https://developer.android.com/guide/topics/ui/menus#java
        switch (item.getItemId()) {
            case R.id.miAddToList:
                addToList();
                break;
            case R.id.miShare:
                this.shareTrailer();
                break;
            case R.id.miActors:
                this.showActors();
                break;
        }

        return false;
    }

    private void setUpActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setTitle(this.movie.getTitle());
        }
    }

    private void addToList() {
        Intent intent = new Intent(MovieDetailActivity.this, AddMovieToListActivity.class);
        intent.putExtra(AddMovieToListActivity.MOVIE_ID, this.movie.getId());
        startActivity(intent);
    }

    private void showActors() {
        Intent intent = new Intent(MovieDetailActivity.this, MovieActorsActivity.class);
        intent.putExtra(MovieActorsActivity.EXTRA_MOVIE_ID, this.movie.getId());
        startActivity(intent);
    }

    private void shareTrailer() {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        String shareBody = "Look i found this movie on Movie4Me for you: ";
        String shareMessage = "Take a look at this awesome movie! " + this.movie.getTitle() + ": " + this.getTrailerUrl();
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareMessage);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(shareIntent, "Medium to share with"));
    }

    private String getTrailerUrl() {
        String strVideo = "no trailer available";

        if (videos.size() > 0) {
            strVideo = videos.get(0).getKey();

            /**System.out.println("YT:" + strVideo);

             System.out.println(video.get(0).getSite().equals("YouTube"));
             System.out.println(video.get(0).getSite());
             String testing = "YouTube";
             String type = video.get(0).getType();
             System.out.println(testing);**/
            if (videos.get(0).getSite().equals("YouTube")) {
                strVideo = "https://www.youtube.com/watch?v=" + strVideo;
                System.out.println(strVideo);
            } else {
                strVideo = "no trailer available";
            }
        }

        return strVideo;
    }

}
