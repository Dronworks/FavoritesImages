package org.dronworks.testphotos;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

// FullScreenPhotoActivity.java
public class FullScreenPhotoActivity extends AppCompatActivity {
    public static final String EXTRA_IMAGE_RES_ID = "image_res_id";
    public static final String EXTRA_NAME = "name";
    public static final String EXTRA_DESCRIPTION = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_photo);

        ImageView imageView = findViewById(R.id.fullScreenImageView);
        TextView nameTextView = findViewById(R.id.fullScreenNameTextView);
        TextView descriptionTextView = findViewById(R.id.fullScreenDescriptionTextView);

        // Get data from intent
        Intent intent = getIntent();
        int imageResId = intent.getIntExtra(EXTRA_IMAGE_RES_ID, 0);
        String name = intent.getStringExtra(EXTRA_NAME);
        String description = intent.getStringExtra(EXTRA_DESCRIPTION);

        // Set data to views
        imageView.setImageResource(imageResId);
        nameTextView.setText(name);
        descriptionTextView.setText(description);
    }
}