package ch.fhnw.movie4me.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Review;
import ch.fhnw.movie4me.dto.Video;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.util.ImageUtils;

public class MovieDetailActivity extends AppCompatActivity {

    public static final String EXTRA_MOVIE_ID = "ch.fhnw.movie4me.ui.MovieDetailActivity.MOVIE_ID";

    private Movie movie;
    private List<Review> reviews;
    private List<Video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_movie_detail);

        int movieId = -1;
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(EXTRA_MOVIE_ID)) {
            movieId = intent.getIntExtra(EXTRA_MOVIE_ID, -1);
        }

        if (movieId <= 0) {
            finish();
        }

        TheMovieDbClient client = TheMovieDbClient.getInstance();
        this.movie = client.getMovie(movieId);
        this.reviews = client.getMovieReviews(movieId);
        this.videos = client.getMovieVideos(movieId);

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

        StringBuilder text = new StringBuilder();
        for (Video video : this.videos) {
            if (video.getSite().equals(Video.SITE_YOUTUBE)) {
                text.append(video.getName());
                text.append(System.getProperty("line.separator"));
                text.append("https://www.youtube.com/watch?v=");
                text.append(video.getKey());
                text.append(System.getProperty("line.separator"));
                text.append(System.getProperty("line.separator"));
            }
        }
        final TextView textView = findViewById(R.id.tvVideos);
        textView.setText(text.toString());
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
        String url = this.getTrailerUrl();
        if (url.length() > 0) {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            String shareBody = "Look i found this movie on Movie4Me for you: ";
            String shareMessage = "Take a look at this awesome movie! " + this.movie.getTitle() + ": " + url;
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareMessage);
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(shareIntent, "Medium to share with"));
        } else {
            Toast.makeText(this, "This movie has no trailer.", Toast.LENGTH_LONG).show();
        }
    }

    private String getTrailerUrl() {
        String url = "";
        if (videos.size() > 0) {
            for (int i = this.videos.size() - 1; i >= 0; i--) {
                Video video = this.videos.get(i);
                if (video.getSite().equals(Video.SITE_YOUTUBE) && video.getType().equals(Video.TYPE_TRAILER) && video.getKey() != null && video.getKey().length() > 0) {
                    url = "https://www.youtube.com/watch?v=" + video.getKey();
                    break;
                }
            }
        }
        return url;
    }

}
