package ch.fhnw.movie4me.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ch.fhnw.movie4me.R;

public class MovieListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    private TextView tvName, tvDescription;
    private OnItemClickListener onItemClickListener;

    public MovieListViewHolder(@NonNull View itemView) {
        super(itemView);

        tvName = itemView.findViewById(R.id.tvName);
        tvDescription = itemView.findViewById(R.id.tvDescription);

        itemView.setOnClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (this.onItemClickListener != null) {
            this.onItemClickListener.OnItemClickListener(getAdapterPosition());
        }
    }
}
