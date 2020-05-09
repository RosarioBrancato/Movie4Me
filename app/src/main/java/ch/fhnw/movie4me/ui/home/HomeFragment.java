package ch.fhnw.movie4me.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.MovieArrayAdapter;
import ch.fhnw.movie4me.config.ConfigLoader;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class HomeFragment extends Fragment {

    private TheMovieDbClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        final RecyclerView lvMovies = root.findViewById(R.id.rvMovies);
        lvMovies.setHasFixedSize(true);
        lvMovies.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setTitle("What's Popular");
        actionBar.setDisplayHomeAsUpEnabled(false);

        this.client = new TheMovieDbClient(ConfigLoader.getInstance().getTheMovieDbApyKey());
        List<Movie> movies = this.client.getPopular();

        MovieArrayAdapter itemArrayAdapter = new MovieArrayAdapter(this.getContext(), movies);
        lvMovies.setAdapter(itemArrayAdapter);
        return root;
    }
}
