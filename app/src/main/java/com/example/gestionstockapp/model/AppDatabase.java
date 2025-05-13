package com.example.gestionstockapp.model;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

// ⬅️ Ajouté : Product.class, User.class
@Database(entities = {Product.class, User.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public abstract ProductDao productDao();
    public abstract UserDao userDao();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "stock_db"
                    )
                    .fallbackToDestructiveMigration() // Ajouté pour éviter les erreurs de migration
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }
}
