package ch.fhnw.movie4me.adapter.actor;

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
import ch.fhnw.movie4me.dto.Cast;
import ch.fhnw.movie4me.dto.Movie;
import ch.fhnw.movie4me.util.ImageUtils;

public class ActorRecyclerViewAdapter extends RecyclerView.Adapter<ActorViewHolder> implements OnItemClickListener, OnItemLongClickListener {

    private Context context;
    private List<Cast> castList;
    private OnActorClickListener onActorClickListener;
    private OnActorLongClickListener onActorLongClickListener;

    // Getting the context and player List with constructor
    public ActorRecyclerViewAdapter(Context context, List<Cast> castList) {
        this.context = context;
        this.castList = castList;
    }

    public void setOnActorClickListener(OnActorClickListener onActorClickListener) {
        this.onActorClickListener = onActorClickListener;
    }

    public void setOnActorLongClickListener(OnActorLongClickListener onActorLongClickListener) {
        this.onActorLongClickListener = onActorLongClickListener;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_item_cast, null);

        ActorViewHolder viewHolder = new ActorViewHolder(view);
        viewHolder.setOnItemClickListener(this);
        viewHolder.setOnItemLongClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Cast cast = this.castList.get(position);

        holder.getTvName().setText(cast.getName());
        holder.getTvCharacter().setText(cast.getCharacter());

        Bitmap bitmap = ImageUtils.getBitmapFromUrl(cast.getPosterUrl());
        if (bitmap != null) {
            holder.getIvPoster().setImageBitmap(bitmap);
        }
    }

    @Override
    public int getItemCount() {
        return this.castList.size();
    }

    @Override
    public void onItemClickListener(int position) {
        if(this.onActorClickListener != null) {
            Cast cast = castList.get(position);
            this.onActorClickListener.onActorClickListener(cast);
        }
    }

    @Override
    public void onItemLongClickListener(int position) {
        if(this.onActorLongClickListener != null) {
            Cast cast = castList.get(position);
            this.onActorLongClickListener.onActorLongClickListener(cast);
        }
    }
}
