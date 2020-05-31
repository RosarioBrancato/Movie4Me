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
import ch.fhnw.movie4me.adapter.movielist.MovieListRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movielist.MovieListViewHolder;
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


        //final TextView txTitle = findViewById(R.id.txTitle);
        //txTitle.setText(this.movie.getTitle());

        final TextView txTitleList = findViewById(R.id.txTitleList);
        txTitleList.setText("Available Lists");

        this.movieListDb = new MovieListDb();


        movieLists = movieListDb.getAll();

        recyclerView = findViewById(R.id.rvMovielists);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


       MovieListRecyclerViewAdapter adapter = new MovieListRecyclerViewAdapter(this, movieLists);


        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter((RecyclerView.Adapter) adapter);


        //System.out.println("Grösse der movielist " + movieLists.size());

        //System.out.println(movieLists.get(1).getName());


    }





    private void refreshFields() {
        List<MovieList> movieLists = this.movieListDb.getAll();

        final RecyclerView lvMovieLists = this.rootView.findViewById(R.id.rvMovieLists);
        lvMovieLists.setHasFixedSize(true);
        lvMovieLists.setLayoutManager(new LinearLayoutManager(this));

        MovieListRecyclerViewAdapter itemArrayAdapter = new MovieListRecyclerViewAdapter(this,movieLists);
        lvMovieLists.setAdapter(itemArrayAdapter);
    }



}




