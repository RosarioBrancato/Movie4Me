package ch.fhnw.movie4me.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.dto.Movie;

public class MovieArrayAdapter extends RecyclerView.Adapter<MovieViewHolder> {

    // This context we will use to inflate the Layout
    private Context context;

    // We are storing all the players in a List
    private List<Movie> movieList;


    // Getting the context and player List with constructor
    public MovieArrayAdapter(Context context, List<Movie> movieList) {
        this.context = context;
        this.movieList = movieList;
    }


    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_movie, null);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = this.movieList.get(position);

        holder.getTvTitle().setText(movie.getTitle());
        holder.getTvReleaseDate().setText(movie.getReleaseDateFormatted());

        Bitmap bitmap = this.getBitmapFromUrl(movie.getPosterUrl());
        if (bitmap != null) {
            holder.getIvPoster().setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }

    private Bitmap getBitmapFromUrl(String url) {
        final Bitmap[] bmp = new Bitmap[1];

        Thread thread = new Thread(() -> {
            try {
                InputStream in = new URL(url).openStream();
                bmp[0] = BitmapFactory.decodeStream(in);

            } catch (IOException e) {
                Log.e(this.getClass().getName(), e.getMessage(), e);
            }
        });

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            Log.e(this.getClass().getName(), e.getMessage(), e);
        }

        return bmp[0];
    }
}
