package org.dronworks.testphotos;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        List<Photo> photos = loadPhotosByLocation(this);

        adapter = new PhotoAdapter(photos);
        recyclerView.setAdapter(adapter);
    }

    public List<Photo> loadPhotosByLocation(Context context) {
        List<Photo> photos = new ArrayList<>();
        Map<String, Integer> resourceMap = new HashMap<>();

        // Use reflection to get all drawable resources
        try {
            Class<?> drawableClass = R.drawable.class;
            for (java.lang.reflect.Field field : drawableClass.getDeclaredFields()) {
                String resourceName = field.getName();
                if( !resourceName.startsWith("flower")) continue; // Filter by specific names
                int resourceId = field.getInt(null); // Get the resource ID
                resourceMap.put(resourceName, resourceId);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Group photos by location
        for (Map.Entry<String, Integer> entry : resourceMap.entrySet()) {
            String location = entry.getKey();
            int resId = entry.getValue();
            photos.add(new Photo(resId, location, location));
        }

        return photos;
    }
}
