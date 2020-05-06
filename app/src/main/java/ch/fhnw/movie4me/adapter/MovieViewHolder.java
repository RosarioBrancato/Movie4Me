package ch.fhnw.movie4me.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.fhnw.movie4me.R;

public class MovieViewHolder extends RecyclerView.ViewHolder  {

    private TextView tvTitle, tvReleaseDate;
    private ImageView ivPoster;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        ivPoster = itemView.findViewById(R.id.ivPoster);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);
    }

    public TextView getTvTitle() {
        return tvTitle;
    }

    public TextView getTvReleaseDate() {
        return tvReleaseDate;
    }

    public ImageView getIvPoster() {
        return ivPoster;
    }
}
