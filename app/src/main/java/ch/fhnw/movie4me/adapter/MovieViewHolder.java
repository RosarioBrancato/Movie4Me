package ch.fhnw.movie4me.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.fhnw.movie4me.R;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvTitle, tvReleaseDate;
    private ImageView ivPoster;
    private OnItemClickListener onItemClickListener;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        ivPoster = itemView.findViewById(R.id.ivPoster);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);

        itemView.setOnClickListener(this);
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

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }
}
