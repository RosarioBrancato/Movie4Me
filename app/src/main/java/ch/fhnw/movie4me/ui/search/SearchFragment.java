package ch.fhnw.movie4me.ui.search;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movie.OnMovieClickListener;
import ch.fhnw.movie4me.adapter.movie.OnMovieLongClickListener;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.ui.MovieDetailActivity;

public class SearchFragment extends Fragment implements OnMovieClickListener, OnMovieLongClickListener {

    private TheMovieDbClient theMovieDbClient;
    private View root;
    private Button btnSearch;
    private List<Movie> movies;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_search, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);
        actionBar.setTitle("Search");

        this.theMovieDbClient = TheMovieDbClient.getInstance();

        btnSearch = root.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchMovie(root);
            }
        });

        return root;
    }

    @Override
    public void onMovieClickListener(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra(MovieDetailActivity.EXTRA_MOVIE_ID, movie.getId());
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onMovieLongClickListener(Movie movie) {

    }

    public void searchMovie(View view) {
        EditText editText1 = view.findViewById(R.id.movieNameInput);
        String movieName = editText1.getText().toString();

        if (movieName.length() > 0) {
            refreshFields(movieName);
        } else {
            Toast.makeText(this.getContext(), "Please enter a movie name.", Toast.LENGTH_LONG).show();
        }
    }

    private void refreshFields(String movieName) {
        List<Movie> searchResults = this.theMovieDbClient.searchMovie(movieName);

        if (searchResults != null && searchResults.size() > 0) {

            final RecyclerView lvSearchResults = this.root.findViewById(R.id.rvSearchResults);
            lvSearchResults.setHasFixedSize(true);
            lvSearchResults.setLayoutManager(new LinearLayoutManager(this.getActivity()));

            MovieRecyclerViewAdapter itemArrayAdapter = new MovieRecyclerViewAdapter(this.getContext(), searchResults);
            itemArrayAdapter.setOnMovieClickListener(this);
            itemArrayAdapter.setOnMovieLongClickListener(this);
            lvSearchResults.setAdapter(itemArrayAdapter);

        } else {
            Toast.makeText(this.getContext(), "No movies have been found.", Toast.LENGTH_LONG).show();
        }
    }

}
