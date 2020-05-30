package ch.fhnw.movie4me.adapter.movie;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.OnItemClickListener;
import ch.fhnw.movie4me.adapter.OnItemLongClickListener;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.util.ImageUtils;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieViewHolder> implements OnItemClickListener, OnItemLongClickListener {

    private Context context;
    private List<Movie> movieList;
    private OnMovieClickListener onMovieClickListener;
    private OnMovieLongClickListener onMovieLongClickListener;

    // Getting the context and player List with constructor
    public MovieRecyclerViewAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void setOnMovieLongClickListener(OnMovieLongClickListener onMovieLongClickListener) {
        this.onMovieLongClickListener = onMovieLongClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_movie, null);

        MovieViewHolder viewHolder = new MovieViewHolder(view);
        viewHolder.setOnItemClickListener(this);
        viewHolder.setOnItemLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);

        holder.getTvTitle().setText(movie.getTitle());
        holder.getTvReleaseDate().setText(movie.getReleaseDateFormatted());

        Bitmap bitmap = ImageUtils.getBitmapFromUrl(movie.getPosterUrl());
        if (bitmap != null) {
            holder.getIvPoster().setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    @Override
    public void onItemClickListener(int position) {
        if(this.onMovieClickListener != null) {
            Movie movie = movieList.get(position);
            this.onMovieClickListener.onMovieClickListener(movie);
        }
    }

    @Override
    public void onItemLongClickListener(int position) {
        if(this.onMovieLongClickListener != null) {
            Movie movie = movieList.get(position);
            this.onMovieLongClickListener.onMovieLongClickListener(movie);
        }
    }
}
