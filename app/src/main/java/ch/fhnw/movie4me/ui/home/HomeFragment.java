package ch.fhnw.movie4me.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.movie.OnMovieLongClickListener;
import ch.fhnw.movie4me.adapter.movie.MovieRecyclerViewAdapter;
import ch.fhnw.movie4me.adapter.movie.OnMovieClickListener;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;
import ch.fhnw.movie4me.ui.MovieDetailActivity;

public class HomeFragment extends Fragment implements OnMovieClickListener, OnMovieLongClickListener {

    private TheMovieDbClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("What's Popular");
        actionBar.setDisplayHomeAsUpEnabled(false);

        final RecyclerView lvMovies = root.findViewById(R.id.rvMovies);
        lvMovies.setHasFixedSize(true);
        lvMovies.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        this.client = TheMovieDbClient.getInstance();
        List<Movie> movies = this.client.getPopular();

        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(this.getContext(), movies);
        adapter.setOnMovieClickListener(this);
        adapter.setOnMovieLongClickListener(this);
        lvMovies.setAdapter(adapter);

        return root;
    }

    @Override
    public void onMovieClickListener(Movie movie) {
        if (movie != null) {
            Intent intent = new Intent(getContext(), MovieDetailActivity.class);
            intent.putExtra("MOVIE_ID", movie.getId());
            getContext().startActivity(intent);
        }
    }

    @Override
    public void onMovieLongClickListener(Movie movie) {
        //TEMP EXAMPLE
        Toast.makeText(getContext(), "Long click.", Toast.LENGTH_LONG).show();
    }
}
