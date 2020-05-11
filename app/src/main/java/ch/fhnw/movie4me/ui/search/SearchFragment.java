package ch.fhnw.movie4me.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.themoviedb.TheMovieDbClient;

public class SearchFragment extends Fragment {

    private TheMovieDbClient theMovieDbClient;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        this.theMovieDbClient = TheMovieDbClient.getInstance();
        List<Movie> movies = this.theMovieDbClient.searchMovie("Star Wars");

        //TEMP
        final TextView textView = root.findViewById(R.id.text_notifications);
        textView.setText("TEMP: I have found " + movies.size() + " Star Wars movies.");

        return root;
    }
}
