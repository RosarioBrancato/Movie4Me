package ch.fhnw.movie4me.themoviedb;

import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.Page;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITheMovieDbService {

    @GET("movie/{id}")
    Call<Movie> getMovie(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("movie/popular")
    Call<Page<Movie>> getPopular(@Query("api_key") String apiKey);

    @GET("movie/now_playing")
    Call<Page<Movie>> getNowPlaying(@Query("api_key") String apiKey);

    @GET("movie/upcoming")
    Call<Page<Movie>> getUpcoming(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Page<Movie>> getTopRated(@Query("api_key") String apiKey);

}
