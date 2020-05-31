package ch.fhnw.movie4me.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

    private ActionBar actionBar;
    private RecyclerView lvMovies;

    private TheMovieDbClient client;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(false);

        lvMovies = root.findViewById(R.id.rvMovies);
        lvMovies.setHasFixedSize(true);
        lvMovies.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        this.client = TheMovieDbClient.getInstance();
        List<Movie> movies = this.client.getPopular();
        this.resetAdapter(movies);

        return root;
    }

    private void resetAdapter(List<Movie> movies) {
        MovieRecyclerViewAdapter adapter = new MovieRecyclerViewAdapter(this.getContext(), movies);
        adapter.setOnMovieClickListener(this);
        adapter.setOnMovieLongClickListener(this);
        lvMovies.setAdapter(adapter);
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.pre_def_movie_lists, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        //https://developer.android.com/guide/topics/ui/menus#java
        List<Movie> movies = null;

        switch (item.getItemId()) {
            case R.id.miPopular:
                this.actionBar.setTitle("What's Popular");
                movies = this.client.getPopular();
                break;
            case R.id.miNowPlaying:
                this.actionBar.setTitle("Now Playing");
                movies = this.client.getNowPlaying();
                break;
            case R.id.miUpcoming:
                this.actionBar.setTitle("Upcoming");
                movies = this.client.getUpcoming();
                break;
            case R.id.miTopRated:
                this.actionBar.setTitle("Top Rated");
                movies = this.client.getTopRated();
                break;
        }

        if (movies != null) {
            this.resetAdapter(movies);
            return true;
        } else {
            return false;
        }
    }
}
