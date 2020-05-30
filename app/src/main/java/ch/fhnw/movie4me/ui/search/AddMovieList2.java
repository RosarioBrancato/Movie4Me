package ch.fhnw.movie4me.ui.search;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.MovieArrayAdapter;
import ch.fhnw.movie4me.adapter.MovieListArrayAdapter;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.db.MovieListDetailDb;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class AddMovieList2<RecyclerAdapter> extends AppCompatActivity {

    private TheMovieDbClient client;
    private Movie movie;

    private TheMovieDbClient theMovieDbClient;
    private MovieListDb movieListDb;
    private MovieListDetailDb movieListDetailDb;

    private MovieList movieList;
    private List<MovieList> movieLists;
    private List<MovieListDetail> movieListDetails;
    private List<Movie> movies;
    public static final String EXTRA_MOVIE_LIST_ID = "ch.fhnw.movie4me.ui.MOVIE_LIST_ID";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter adapter;


    private View rootView;


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_add_movie_list2);

        /**this.client = TheMovieDbClient.getInstance();
        List<Movie> movies = this.client.getPopular();

        MovieArrayAdapter itemArrayAdapter = new MovieArrayAdapter(this.getContext(), movies);
        lvMovies.setAdapter(itemArrayAdapter);**/

        this.movieListDb = new MovieListDb();


        movieLists = movieListDb.getAll();

        recyclerView = findViewById(R.id.rvMovielists);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        MovieListArrayAdapter adapter = new MovieListArrayAdapter(this, movieLists);

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter((RecyclerView.Adapter) adapter);





        /**final RecyclerView rvMovieLists = findViewById(R.id.rvMovieLists);
        rvMovieLists.setHasFixedSize(true);
        rvMovieLists.setLayoutManager(new LinearLayoutManager(this));

        MovieListArrayAdapter itemArrayAdapter = new MovieListArrayAdapter(this, movieLists);
        rvMovieLists.setAdapter(itemArrayAdapter);**/

        System.out.println("Grösse der movielist " + movieLists.size());

        System.out.println(movieLists.get(1).getName());


    }

    /**protected View onCreate(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_movielists, container, false);
        this.movieListDb = new MovieListDb();

        final TextView txTitle = findViewById(R.id.txTitle);
        txTitle.setText("Add Movie to List");

        List<MovieList> movieLists = this.movieListDb.getAll();

        final RecyclerView lvMovieLists = this.rootView.findViewById(R.id.rvMovieLists);
        lvMovieLists.setHasFixedSize(true);
        lvMovieLists.setLayoutManager(new LinearLayoutManager(this));

        MovieListArrayAdapter itemArrayAdapter = new MovieListArrayAdapter(this,movieLists);
        lvMovieLists.setAdapter(itemArrayAdapter);


        this.refreshFields();

        return this.rootView;
    }**/



    private void refreshFields() {
        List<MovieList> movieLists = this.movieListDb.getAll();

        final RecyclerView lvMovieLists = this.rootView.findViewById(R.id.rvMovieLists);
        lvMovieLists.setHasFixedSize(true);
        lvMovieLists.setLayoutManager(new LinearLayoutManager(this));

        MovieListArrayAdapter itemArrayAdapter = new MovieListArrayAdapter(this,movieLists);
        lvMovieLists.setAdapter(itemArrayAdapter);
    }



}




