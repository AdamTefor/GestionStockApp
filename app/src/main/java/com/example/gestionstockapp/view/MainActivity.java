// ✅ MainActivity.java (optimisé avec Logcat)
package com.example.gestionstockapp.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.controller.ProductController;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import com.example.gestionstockapp.model.ProductDao;
import com.example.gestionstockapp.view.adapter.ProductAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private ProductDao productDao;
    private ProductAdapter adapter;
    private RecyclerView recyclerView;
    private ProductController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initialiser Room + DAO + Controller
        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "stock-db").build();
        productDao = db.productDao();
        controller = new ProductController(this);

        // Initialiser UI
        EditText editName = findViewById(R.id.editTextName);
        EditText editQty = findViewById(R.id.editTextQuantity);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonGoToAdd = findViewById(R.id.buttonGoToAdd);
        Button buttonGoToEdit = findViewById(R.id.buttonGoToEdit);
        Button buttonGoToSearch = findViewById(R.id.buttonGoToSearch);
        Button buttonGoToStats = findViewById(R.id.buttonGoToStats);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Charger les produits
        loadProducts();

        // Ajouter un produit
        buttonAdd.setOnClickListener(view -> {
            String name = editName.getText().toString().trim();
            String qtyText = editQty.getText().toString().trim();
            if (name.isEmpty() || qtyText.isEmpty()) return;
            int quantity = Integer.parseInt(qtyText);

            Log.d("DEBUG", "Produit à ajouter : " + name + " x " + quantity);

            Executors.newSingleThreadExecutor().execute(() -> {
                controller.addProduct(name, quantity);
                runOnUiThread(() -> {
                    Log.d("DEBUG", "Produit ajouté avec succès");
                    loadProducts();
                });
            });
        });

        // Navigation
        buttonGoToAdd.setOnClickListener(v -> {
            Log.d("DEBUG", "Navigation vers AddProductActivity");
            startActivity(new Intent(this, AddProductActivity.class));
        });
        buttonGoToEdit.setOnClickListener(v -> {
            Log.d("DEBUG", "Navigation vers EditProductActivity");
            startActivity(new Intent(this, EditProductActivity.class));
        });
        buttonGoToSearch.setOnClickListener(v -> {
            Log.d("DEBUG", "Navigation vers SearchActivity");
            startActivity(new Intent(this, SearchActivity.class));
        });
        buttonGoToStats.setOnClickListener(v -> {
            Log.d("DEBUG", "Navigation vers StatsActivity");
            startActivity(new Intent(this, StatsActivity.class));
        });
        Button buttonUserList = findViewById(R.id.buttonUserList);
        buttonUserList.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, UserListActivity.class));
        });

    }

    private void loadProducts() {
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Product> products = controller.getAllProducts();
            runOnUiThread(() -> {
                Log.d("DEBUG", "Produits chargés : " + products.size());
                if (adapter == null) {
                    adapter = new ProductAdapter(products, controller);
                    recyclerView.setAdapter(adapter);
                } else {
                    adapter.updateData(products);
                }
            });
        });
    }
}
