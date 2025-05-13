package com.example.gestionstockapp.view;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.User;
import com.example.gestionstockapp.model.UserDao;

import java.util.concurrent.Executors;

public class RegisterActivity extends AppCompatActivity {

    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        EditText editName = findViewById(R.id.editName);
        EditText editEmail = findViewById(R.id.editEmail);
        EditText editPassword = findViewById(R.id.editPassword);
        Button buttonRegister = findViewById(R.id.buttonRegister);

        userDao = AppDatabase.getInstance(this).userDao();

        buttonRegister.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            String email = editEmail.getText().toString().trim();
            String password = editPassword.getText().toString().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                return;
            }

            Executors.newSingleThreadExecutor().execute(() -> {
                userDao.insert(new User(name, email, password));
                runOnUiThread(() -> {
                    Toast.makeText(this, "Compte créé avec succès", Toast.LENGTH_SHORT).show();
                    finish(); // retourne à la page de login
                });
            });
        });
    }
}
