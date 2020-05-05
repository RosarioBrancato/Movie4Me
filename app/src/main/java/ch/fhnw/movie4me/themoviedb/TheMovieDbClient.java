package ch.fhnw.movie4me.themoviedb;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Page;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbClient {
    //https://developer.android.com/training/volley/simple

    private final String BASE_URL = "https://api.themoviedb.org/3/";
    private static String API_KEY = "";

    private Retrofit retrofit;
    private ITheMovieDbService service;

    public TheMovieDbClient(String apiKey) {
        API_KEY = apiKey;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ITheMovieDbService.class);
    }


    public Movie getMovie(int id) {
        Movie movie = null;

        Call<Movie> movieCall = this.service.getMovie(id, API_KEY);

        try {
            Response<Movie> response = movieCall.execute();
            movie = response.body();

        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        }

        return movie;
    }

    public List<Movie> getPopular() {
        List<Movie> movies = null;

        Call<Page<Movie>> popularCall = this.service.getPopular(API_KEY);

        try {
            Response<Page<Movie>> response = popularCall.execute();
            movies = response.body().getResults();

        } catch (IOException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        }

        return movies;
    }

}
