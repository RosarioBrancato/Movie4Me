package ch.fhnw.movie4me.themoviedb;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.List;

import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.dto.CastSearch;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Page;
import ch.fhnw.movie4me.dto.Review;
import ch.fhnw.movie4me.dto.Video;
import ch.fhnw.movie4me.dto.VideoSearch;
import ch.fhnw.movie4me.util.ThreadUtils;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * //https://developer.android.com/training/volley/simple
 */
public class TheMovieDbClient {

    private static TheMovieDbClient instance = null;

    private final String TAG = this.getClass().getName();
    private String API_KEY;
    private ITheMovieDbService service;


    private TheMovieDbClient() {
    }

    public static TheMovieDbClient getInstance() {
        if(instance == null) {
            instance = new TheMovieDbClient();
        }
        return instance;
    }

    public void init(String apiKey) {
        API_KEY = apiKey;

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(ITheMovieDbService.class);

    }


    public Movie getMovie(int id) {
        Call<Movie> movieCall = this.service.getMovie(id, API_KEY);
        Movie movie = this.executeCall(movieCall);
        return movie;
    }

    public List<Video> getMovieVideos(int id) {
        List<Video> videos = null;

        Call<VideoSearch> movieVideosCall = this.service.getMovieVideos(id, API_KEY);
        VideoSearch videoSearch = this.executeCall(movieVideosCall);
        if (videoSearch != null) {
            videos = videoSearch.getResults();
        }

        return videos;
    }

    public List<Cast> getMovieCast(int id) {
        List<Cast> cast = null;

        Call<CastSearch> movieCastCall = this.service.getMovieCast(id, API_KEY);
        CastSearch castSearch = this.executeCall(movieCastCall);
        if (castSearch != null) {
            cast = castSearch.getCast();
        }

        return cast;
    }

    public List<Review> getMovieReviews(int id) {
        List<Review> reviews = null;

        Call<Page<Review>> movieReviewsCall = this.service.getMovieReviews(id, API_KEY);
        Page<Review> page = this.executeCall(movieReviewsCall);
        if (page != null) {
            reviews = page.getResults();
        }

        return reviews;
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

    public List<Movie> searchMovie(String searchText) {
        List<Movie> movies = null;

        Call<Page<Movie>> searchMovieCall = this.service.searchMovie(API_KEY, searchText);
        Page<Movie> page = this.executeCall(searchMovieCall);
        if (page != null) {
            movies = page.getResults();
        }

        return movies;
    }

    private <T> T executeCall(Call<T> call) {
        T retVal = null;
        final Response<T>[] response = new Response[1];

        ThreadUtils.runAsync(() -> {
            try {
                response[0] = call.execute();

            } catch (IOException e) {
                Log.e(TAG, e.getMessage(), e);
            }
        });

        if (response[0] != null) {
            if (response[0].isSuccessful()) {
                retVal = response[0].body();

            } else if (response[0].errorBody() != null) {
                try {
                    Log.e(TAG, response[0].errorBody().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e(TAG, call.request().url() + " was not successful.");
            }
        }

        return retVal;
    }

}
