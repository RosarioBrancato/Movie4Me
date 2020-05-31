package ch.fhnw.movie4me.adapter.movielist;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.OnItemClickListener;
import ch.fhnw.movie4me.adapter.OnItemLongClickListener;
import ch.fhnw.movie4me.dto.MovieList;
import ch.fhnw.movie4me.ui.MovieListDetailActivity;

public class MovieListRecyclerViewAdapter extends RecyclerView.Adapter<MovieListViewHolder> implements OnItemClickListener, OnItemLongClickListener {

    private Context context;
    private List<MovieList> movieLists;
    private OnMovieListClickListener onMovieListClickListener;
    private OnMovieListLongClickListener onMovieListLongClickListener;


    // Getting the context and player List with constructor
    public MovieListRecyclerViewAdapter(Context context, List<MovieList> movieLists) {
        this.context = context;
        this.movieLists = movieLists;
    }


    public void setOnMovieListClickListener(OnMovieListClickListener onMovieListClickListener) {
        this.onMovieListClickListener = onMovieListClickListener;
    }

    public void setOnMovieListLongClickListener(OnMovieListLongClickListener onMovieListLongClickListener) {
        this.onMovieListLongClickListener = onMovieListLongClickListener;
    }

    @NonNull
    @Override
    public MovieListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_movielist, null);

        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        viewHolder.setOnItemClickListener(this);
        viewHolder.setOnItemLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieListViewHolder holder, int position) {
        MovieList movieList = this.movieLists.get(position);

        holder.getTvName().setText(movieList.getName());
        holder.getTvDescription().setText(movieList.getDescription());
    }

    @Override
    public int getItemCount() {
        return this.movieLists.size();
    }

    @Override
    public void onItemClickListener(int position) {
        if (this.onMovieListClickListener != null) {
            MovieList movieList = this.movieLists.get(position);
            this.onMovieListClickListener.onMovieListClickListener(movieList);
        }
    }

    @Override
    public void onItemLongClickListener(int position) {
        if (this.onMovieListLongClickListener != null) {
            MovieList movieList = this.movieLists.get(position);
            this.onMovieListLongClickListener.onMovieListLongClickListener(movieList);
        }
    }
}
