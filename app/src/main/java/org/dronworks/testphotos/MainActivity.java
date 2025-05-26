package org.dronworks.testphotos;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    PhotoAdapter adapter;
    DatabaseHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // מה זה LayoutManager
        dbHelper = new DatabaseHelper(this);

        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean isFirstLaunch = prefs.getBoolean("isFirstLaunch", true);

        if (isFirstLaunch) { // לא הבנתי את זה ובכללי את isFirstLaunch
            dbHelper.createDefaultUser();
            loadPhotosToDB();
            prefs.edit().putBoolean("isFirstLaunch", false).apply();
        }


        List<Photo> photos = dbHelper.loadPhotosByLocation();

        adapter = new PhotoAdapter(photos, dbHelper);
        recyclerView.setAdapter(adapter);
    }


    private void loadPhotosToDB() {
        Map<String, Integer> resourceMap = new HashMap<>(); // מה זה מפה?

        // Use reflection to get all drawable resources
        try { // לא מכירה TRY ו- CATCH
            Class<?> drawableClass = R.drawable.class; // מה זה הסימן שאלה?
            for (java.lang.reflect.Field field : drawableClass.getDeclaredFields()) { // מה זה FIELD?
                String resourceName = field.getName();
                // מה הבדיקה למטה עושה? הרי כל התמונות יהיו פרחים לא?
                if( !resourceName.startsWith("flower")) continue; // Filter by specific names
                int resourceId = field.getInt(null); // Get the resource ID
                resourceMap.put(resourceName, resourceId);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Group photos by location
        for (Map.Entry<String, Integer> entry : resourceMap.entrySet()) { //לא הבנתי את הפעולה פה ולא הבנתי את המבנה של הFOR
            String location = entry.getKey();
            String name = location.replace("flower_", ""); // Remove prefix for name
            int resId = entry.getValue();
            dbHelper.addPhoto(name, resId);
        }
    }


}
