package org.dronworks.testphotos;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper dbHelper;
    BottomNavigationView bottomNavigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbHelper = new DatabaseHelper(this);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) {
            dbHelper.createDefaultUser();
            loadPhotosToDB();
            prefs.edit().putBoolean("isFirstLaunch", false).apply();
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new HomeFragment())
                .commit();
    }

    private void loadPhotosToDB() {
        Map<String, Integer> resourceMap = new HashMap<>();
        Map<String, String> descriptions = loadDescriptionsFromAssets();

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
            String name = location.replace("flower_", ""); // Remove prefix for name
            int resId = entry.getValue();
            String description = descriptions.getOrDefault(name, "No description available");
            dbHelper.addPhoto(name, description, resId);
        }
    }

    private Map<String, String> loadDescriptionsFromAssets() {
        Map<String, String> map = new HashMap<>();
        try {
            InputStream is = getAssets().open("flower_descriptions.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONObject jsonObject = new JSONObject(json);
            Iterator<String> keys = jsonObject.keys();

            while (keys.hasNext()) {
                String key = keys.next();
                map.put(key, jsonObject.getString(key));
            }

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
        return map;
    }



}
