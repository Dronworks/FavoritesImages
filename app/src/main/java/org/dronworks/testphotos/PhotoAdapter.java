package org.dronworks.testphotos;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private List<Photo> photoList;

    public PhotoAdapter(List<Photo> photoList) {
        this.photoList = photoList;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton favoriteButton;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoImageView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
        }
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.photo_item, parent, false);
        return new PhotoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photoList.get(position);
        holder.imageView.setImageResource(photo.getImageResId());

        // Set heart icon based on favorite status
        if (photo.isFavorite()) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled_res);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline_res);
        }

        holder.favoriteButton.setOnClickListener(v -> {
            photo.setFavorite(!photo.isFavorite());
            notifyItemChanged(position); // or just update icon
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
