package ch.fhnw.movie4me.ui.movielists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import ch.fhnw.movie4me.R;

public class MovieListsFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_movielists, container, false);

        //TEMP
        final TextView textView = root.findViewById(R.id.text_movielists);
        textView.setText("This is the my lists fragment");

        return root;
    }
}
