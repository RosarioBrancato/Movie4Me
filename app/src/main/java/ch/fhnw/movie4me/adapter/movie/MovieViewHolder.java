package ch.fhnw.movie4me.adapter.movie;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.OnItemClickListener;
import ch.fhnw.movie4me.adapter.OnItemLongClickListener;

public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView tvTitle, tvReleaseDate;
    private ImageView ivPoster;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MovieViewHolder(@NonNull View itemView) {
        super(itemView);

        ivPoster = itemView.findViewById(R.id.ivPoster);
        tvTitle = itemView.findViewById(R.id.tvTitle);
        tvReleaseDate = itemView.findViewById(R.id.tvReleaseDate);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
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

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.onItemClickListener(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View v) {
        if(this.onItemLongClickListener != null) {
            this.onItemLongClickListener.onItemLongClickListener(getAdapterPosition());
        }
        return true;
    }
}
