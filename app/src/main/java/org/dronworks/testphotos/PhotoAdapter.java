package org.dronworks.testphotos;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    private final List<Photo> photoList;
    private final DatabaseHelper databaseHelper;

    public PhotoAdapter(List<Photo> photoList, DatabaseHelper databaseHelper) {
        this.photoList = photoList;
        this.databaseHelper = databaseHelper;
    }

    public static class PhotoViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ImageButton favoriteButton;
        TextView nameTextView;

        public PhotoViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.photoImageView);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            nameTextView = itemView.findViewById(R.id.photoNameTextView);
        }
    }

    @NonNull
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
        holder.nameTextView.setText(photo.getName());

        boolean favorite = databaseHelper.isFavorite(1, photo.getImageDbId());// Replace with real user ID

        if (favorite) {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled_res);
        } else {
            holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline_res);
        }

        holder.favoriteButton.setOnClickListener(v -> {
            if(photo.isFavorite()) {
                databaseHelper.removeFavorite(1, photo.getImageDbId()); // Replace with real user ID
            } else {
                databaseHelper.addFavorite(1, photo.getImageDbId()); // Replace with real user ID
            }

            photo.setFavorite(!photo.isFavorite());
            notifyItemChanged(position);
        });

        holder.imageView.setOnClickListener(v -> {
            String description = photo.getDescription();
            new AlertDialog.Builder(holder.itemView.getContext())
                    .setTitle(photo.getName())
                    .setMessage(description)
                    .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                    .show();
        });
    }

    @Override
    public int getItemCount() {
        return photoList.size();
    }
}
