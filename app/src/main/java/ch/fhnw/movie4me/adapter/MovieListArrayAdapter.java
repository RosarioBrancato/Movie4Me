package ch.fhnw.movie4me.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.dto.MovieListDetail;
import ch.fhnw.movie4me.ui.MovieDetailActivity;
import ch.fhnw.movie4me.ui.MovieListDetailActivity;
import ch.fhnw.movie4me.ui.MovieListEditActivity;
import ch.fhnw.movie4me.util.ImageUtils;

public class MovieListArrayAdapter extends RecyclerView.Adapter<MovieListViewHolder> implements OnItemClickListener {

    // This context we will use to inflate the Layout
    private Context context;

    // We are storing all the players in a List
    private List<MovieList> movieListList;


    // Getting the context and player List with constructor
    public MovieListArrayAdapter(Context context, List<MovieList> movieListList) {
        this.context = context;
        this.movieListList = movieListList;
    }


    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_movielist, null);

        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        viewHolder.setOnItemClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        MovieList movieList = this.movieListList.get(position);

        holder.getTvName().setText(movieList.getName());
        holder.getTvDescription().setText(movieList.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.movieListList.size();
    }

    @Override
    public void OnItemClickListener(int position) {
        Intent intent = new Intent(this.context, MovieListDetailActivity.class);

        MovieList movieList = movieListList.get(position);
        intent.putExtra(MovieListDetailActivity.EXTRA_MOVIE_LIST_ID, movieList.getId());

        this.context.startActivity(intent);
    }
}
