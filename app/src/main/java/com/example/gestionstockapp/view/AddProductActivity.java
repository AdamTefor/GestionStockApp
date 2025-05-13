// ✅ AddProductActivity.java avec Log.d
package com.example.gestionstockapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import java.util.concurrent.Executors;

public class AddProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        EditText name = findViewById(R.id.editProductName);
        EditText qty = findViewById(R.id.editProductQty);
        EditText price = findViewById(R.id.editProductPrice);
        Button save = findViewById(R.id.buttonSaveProduct);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "stock-db").build();

        save.setOnClickListener(v -> {
            String nom = name.getText().toString();
            String quantiteTxt = qty.getText().toString();
            if (nom.isEmpty() || quantiteTxt.isEmpty()) return;
            int quantite = Integer.parseInt(quantiteTxt);
            Log.d("DEBUG", "Ajout du produit : " + nom + ", quantité: " + quantite);

            Executors.newSingleThreadExecutor().execute(() -> {
                db.productDao().insert(new Product(nom, quantite));
                runOnUiThread(() -> {
                    Toast.makeText(this, "Produit ajouté", Toast.LENGTH_SHORT).show();
                    finish();
                });
            });
        });
    }
}
