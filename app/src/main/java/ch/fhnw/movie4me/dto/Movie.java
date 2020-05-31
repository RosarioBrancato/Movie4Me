package ch.fhnw.movie4me.dto;

import android.annotation.SuppressLint;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {

    private final String IMG_URL_ROOT = "https://image.tmdb.org/t/p/w500";


    private int id;

    private String title;

    private String tagline;

    private String overview;

    private int runtime;

    private boolean adult;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private double voteAvg;

    public String getPosterUrl() {
        String url = null;

        String posterPath = this.getPosterPath();
        if (posterPath != null) {
            url = IMG_URL_ROOT + posterPath;
        }

        return url;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    @SuppressLint("SimpleDateFormat")
    public String getReleaseDateFormatted() {
        Date date = null;
        String releaseDate = this.getReleaseDate();
        String dateFormatted = null;

        if (releaseDate != null && releaseDate.length() > 0) {
            try {
                date = new SimpleDateFormat("yyyy-MM-dd").parse(this.getReleaseDate());
            } catch (ParseException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
            }
        }

        if (date != null) {
            dateFormatted = new SimpleDateFormat("dd.MM.yyyy").format(date);
        }

        return dateFormatted;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public int getRuntime() {
        return runtime;
    }

    public void setRuntime(int runtime) {
        this.runtime = runtime;
    }

    public boolean isAdult() {
        return adult;
    }

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public double getVoteAvg() {
        return voteAvg;
    }

    public void setVoteAvg(double voteAvg) {
        this.voteAvg = voteAvg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }
}
