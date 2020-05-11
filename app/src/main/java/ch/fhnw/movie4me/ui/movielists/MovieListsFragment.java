package ch.fhnw.movie4me.ui.movielists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.MovieList;

public class MovieListsFragment extends Fragment {

    private MovieListDb movieListDb;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movielists, container, false);

        this.movieListDb = new MovieListDb();
        List<MovieList> movieLists = this.movieListDb.getAll();

        //TEMP
        final TextView textView = root.findViewById(R.id.text_movielists);
        textView.setText("TEMP: I have found " + movieLists.size() + " lists.");

        return root;
    }
}
