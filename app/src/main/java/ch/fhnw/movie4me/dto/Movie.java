package ch.fhnw.movie4me.dto;

import android.annotation.SuppressLint;

import com.google.gson.annotations.SerializedName;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie {

    private String title;

    @SerializedName("release_date")
    private Date releaseDate;

    public Date getReleaseDate() {
        return releaseDate;
    }

    @SuppressLint("SimpleDateFormat")
    public String getReleaseDateFormatted() {
        return new SimpleDateFormat("dd.MM.yyyy").format(this.getReleaseDate());
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
