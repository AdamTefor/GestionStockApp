// ✅ SearchActivity.java (corrigé et complet)
package com.example.gestionstockapp.view;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import com.example.gestionstockapp.view.adapter.ProductAdapter;

import java.util.List;
import java.util.concurrent.Executors;

public class SearchActivity extends AppCompatActivity {

    private ProductAdapter adapter;
    private AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        EditText search = findViewById(R.id.editSearch);
        RecyclerView recycler = findViewById(R.id.recyclerSearchResults);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        db = AppDatabase.getInstance(getApplicationContext());

        adapter = new ProductAdapter(db.productDao().getAll(), null);
        recycler.setAdapter(adapter);

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                String query = s.toString().trim();
                Log.d("DEBUG", "Recherche : " + query);

                Executors.newSingleThreadExecutor().execute(() -> {
                    List<Product> results = db.productDao().searchProducts("%" + query + "%");
                    Log.d("DEBUG", "Produits trouvés : " + results.size());

                    runOnUiThread(() -> {
                        adapter.updateData(results);
                    });
                });
            }
        });
    }
}
