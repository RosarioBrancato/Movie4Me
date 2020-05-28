package ch.fhnw.movie4me.ui.movielists;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.MovieListArrayAdapter;
import ch.fhnw.movie4me.db.MovieListDb;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.ui.MovieListEditActivity;

public class MovieListsFragment extends Fragment {

    private View rootView;

    private MovieListDb movieListDb;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.rootView = inflater.inflate(R.layout.fragment_movielists, container, false);
        this.movieListDb = new MovieListDb();

        this.refreshFields();

        floatingActionButton = this.rootView.findViewById(R.id.floatingActionButtonAdd);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openListEditActivity();
            }
        });

        return this.rootView;
    }

    @Override
    public void onResume() {
        super.onResume();

        this.refreshFields();
    }

    private void refreshFields() {
        List<MovieList> movieLists = this.movieListDb.getAll();

        final RecyclerView lvMovieLists = this.rootView.findViewById(R.id.rvMovieLists);
        lvMovieLists.setHasFixedSize(true);
        lvMovieLists.setLayoutManager(new LinearLayoutManager(this.getActivity()));

        MovieListArrayAdapter itemArrayAdapter = new MovieListArrayAdapter(this.getContext(), movieLists);
        lvMovieLists.setAdapter(itemArrayAdapter);
    }

    public void openListEditActivity() {
        Intent intent = new Intent(getView().getContext(), MovieListEditActivity.class);
        startActivity(intent);
    }


}
