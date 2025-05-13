// ✅ EditProductActivity.java avec Log.d
package com.example.gestionstockapp.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import java.util.concurrent.Executors;

public class EditProductActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        EditText name = findViewById(R.id.editProductName);
        EditText qty = findViewById(R.id.editProductQty);
        Button update = findViewById(R.id.buttonUpdate);

        AppDatabase db = AppDatabase.getInstance(getApplicationContext());

        int productId = getIntent().getIntExtra("product_id", -1);
        Log.d("DEBUG", "Product ID reçu : " + productId);

        if (productId != -1) {
            Executors.newSingleThreadExecutor().execute(() -> {
                Product p = db.productDao().getById(productId);
                runOnUiThread(() -> {
                    if (p != null) {
                        name.setText(p.name);
                        qty.setText(String.valueOf(p.quantity));
                    }
                });

                update.setOnClickListener(v -> {
                    p.name = name.getText().toString();
                    p.quantity = Integer.parseInt(qty.getText().toString());
                    Log.d("DEBUG", "Mise à jour du produit : " + p.name + " x" + p.quantity);
                    Executors.newSingleThreadExecutor().execute(() -> {
                        db.productDao().update(p);
                        runOnUiThread(() -> {
                            Log.d("DEBUG", "Produit mis à jour et activité terminée");
                            finish();
                        });
                    });
                });
            });
        } else {
            Log.e("DEBUG", "Aucun ID de produit fourni à EditProductActivity");
        }
    }
}
