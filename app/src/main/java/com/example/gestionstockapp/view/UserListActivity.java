// ✅ UserListActivity.java - avec affichage des utilisateurs et bouton retour
package com.example.gestionstockapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.User;

import java.util.List;
import java.util.concurrent.Executors;

public class UserListActivity extends AppCompatActivity {

    private TextView textUsers;
    private TextView textUserCount;
    private Button buttonBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        // Références UI
        textUsers = findViewById(R.id.textUsers);
        textUserCount = findViewById(R.id.textUserCount);
        buttonBack = findViewById(R.id.buttonBack);

        // Récupération et affichage des utilisateurs
        AppDatabase db = AppDatabase.getInstance(this);
        Executors.newSingleThreadExecutor().execute(() -> {
            List<User> users = db.userDao().getAllUsers();
            runOnUiThread(() -> {
                StringBuilder sb = new StringBuilder();
                for (User user : users) {
                    sb.append("\u2022 ")
                            .append(user.name)
                            .append(" | ")
                            .append(user.email)
                            .append("\n");
                }
                textUsers.setText(sb.toString());
                textUserCount.setText("Nombre total : " + users.size());
            });
        });

        // Bouton retour vers MainActivity
        buttonBack.setOnClickListener(v -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        });
    }
}
