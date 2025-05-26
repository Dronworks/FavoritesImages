package org.dronworks.testphotos;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "photo_app.db";
    private static final int DB_VERSION = 1;
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_RES_LOCATION = "res_location";

    public static final String COLUMN_USER_ID = "user_id";
    public static final String COLUMN_PHOTO_ID = "photo_id";
    public static final String TABLE_NAME_PHOTO = "Photo";
    public static final String TABLE_NAME_USER = "User";
    public static final String TABLE_NAME_FAVORITE = "Favorite";

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create tables
        db.execSQL("CREATE TABLE User (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL)");

        db.execSQL("CREATE TABLE Photo (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT NOT NULL," +
                COLUMN_RES_LOCATION + " INTEGER NOT NULL)");

        db.execSQL("CREATE TABLE Favorite (" +
                COLUMN_USER_ID + " INTEGER," +
                COLUMN_PHOTO_ID + " INTEGER," +
                "PRIMARY KEY (" + COLUMN_USER_ID + ", photo_id)," +
                "FOREIGN KEY (" + COLUMN_USER_ID + ") REFERENCES User(id)," +
                "FOREIGN KEY (" + COLUMN_PHOTO_ID + ") REFERENCES Photo(id))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop old tables
        db.execSQL("DROP TABLE IF EXISTS Favorite");
        db.execSQL("DROP TABLE IF EXISTS Photo");
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    // הבנתי את המטרה של הפונקציה למטה, אבל לא הבנתי את הקוד
    public boolean isFavorite(int userId, int photoId) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                // לא הבנתי את השורה מלמטה, הכוונה שהיא בוחרת העדפה?
                "SELECT 1 FROM Favorite WHERE " + COLUMN_USER_ID + " = ? AND " + COLUMN_PHOTO_ID + " = ?",
                new String[]{String.valueOf(userId), String.valueOf(photoId)});
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public void addFavorite(int userId, int photoId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_ID, userId);
        values.put(COLUMN_PHOTO_ID, photoId);
        db.insert(TABLE_NAME_FAVORITE, null, values); // למה TABLE_NAME_FAVORITE? זה לא יכניס רק את השם של ההעדפה? גם מה המשמעות של NULL פה?
    }

    public void removeFavorite(int userId, int photoId) {
        SQLiteDatabase db = this.getWritableDatabase(); // מה בעצם השורות האלו אומרות? לא מאה אחוז מבינה
        db.delete(TABLE_NAME_FAVORITE, COLUMN_USER_ID + " = ? AND " + COLUMN_PHOTO_ID + " = ?", // למה יש סימני שאלה?
                new String[]{String.valueOf(userId), String.valueOf(photoId)});
    }

    public void addPhoto(String name, int resId) {
        SQLiteDatabase db = this.getWritableDatabase();

        // Insert photo into the Photo table
        ContentValues values2 = new ContentValues();
        values2.put(COLUMN_NAME, name);
        values2.put(COLUMN_RES_LOCATION, resId);  // Resource ID
        db.insert(TABLE_NAME_PHOTO, null, values2);

    }

    public List<Photo> loadPhotosByLocation() {
        SQLiteDatabase db = this.getWritableDatabase();

        List<Photo> photos = new ArrayList<>();
        // Query the database to get all photos
        String[] columns = {COLUMN_ID, COLUMN_NAME, COLUMN_RES_LOCATION};
        // מה זה QUERY?
        try (Cursor cursor = db.query(TABLE_NAME_PHOTO, columns, null, null, null, null, null)) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME));
                int resLocation = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_RES_LOCATION));

                // Create Photo object and add to the list
                Photo photo = new Photo(resLocation, name, id);
                photos.add(photo);
            }
        }

        return photos;
    }

    public void createDefaultUser() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, "Default User");
        db.insert(TABLE_NAME_USER, null, values);
    }
}
