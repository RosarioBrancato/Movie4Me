package ch.fhnw.movie4me.adapter.movielist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.fhnw.movie4me.R;
import ch.fhnw.movie4me.adapter.OnItemClickListener;
import ch.fhnw.movie4me.adapter.OnItemLongClickListener;

public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    private TextView tvName, tvDescription;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public MovieListViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvDescription = itemView.findViewById(R.id.tvDescription);

        itemView.setOnClickListener(this);
        itemView.setOnLongClickListener(this);
    }

    public TextView getTvName() {
        return tvName;
    }

    public TextView getTvDescription() {
        return tvDescription;
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
        if (this.onItemLongClickListener != null) {
            this.onItemLongClickListener.onItemLongClickListener(getAdapterPosition());
        }
        return true;
    }
}
