package ch.fhnw.movie4me.themoviedb;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Page;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class TheMovieDbClient {
    //https://developer.android.com/training/volley/simple

    private final String TAG = this.getClass().getName();
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
        Call<Movie> movieCall = this.service.getMovie(id, API_KEY);
        Movie movie = this.executeCall(movieCall);
        return movie;
    }

    public List<Movie> getPopular() {
        List<Movie> movies = null;

        Call<Page<Movie>> popularCall = this.service.getPopular(API_KEY);
        Page<Movie> page = this.executeCall(popularCall);
        if (page != null) {
            movies = page.getResults();
        }

        return movies;
    }

    public List<Movie> getNowPlaying() {
        List<Movie> movies = null;

        Call<Page<Movie>> nowPlayingCall = this.service.getNowPlaying(API_KEY);
        Page<Movie> page = this.executeCall(nowPlayingCall);
        if (page != null) {
            movies = page.getResults();
        }

        return movies;
    }

    public List<Movie> getUpcoming() {
        List<Movie> movies = null;

        Call<Page<Movie>> upcomingCall = this.service.getUpcoming(API_KEY);
        Page<Movie> page = this.executeCall(upcomingCall);
        if (page != null) {
            movies = page.getResults();
        }

        return movies;
    }

    public List<Movie> getTopRated() {
        List<Movie> movies = null;

        Call<Page<Movie>> popularCall = this.service.getTopRated(API_KEY);
        Page<Movie> page = this.executeCall(popularCall);
        if (page != null) {
            movies = page.getResults();
        }

        return movies;
    }

    private <T> T executeCall(Call<T> call) {
        T retVal = null;

        try {
            Response<T> response = call.execute();

            if (response.isSuccessful()) {
                retVal = response.body();
            } else {
                Log.e(TAG, response.errorBody().string());
            }

        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        }

        return retVal;
    }

}
