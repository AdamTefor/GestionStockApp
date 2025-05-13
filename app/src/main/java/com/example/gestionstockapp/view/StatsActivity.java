package com.example.gestionstockapp.view;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

public class StatsActivity extends AppCompatActivity {

    private PieChart pieChart;
    private TextView textStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        pieChart = findViewById(R.id.pieChart);
        textStats = findViewById(R.id.textStats);

        // Charger les statistiques en background
        Executors.newSingleThreadExecutor().execute(() -> {
            List<Product> produits = AppDatabase.getInstance(getApplicationContext()).productDao().getAll();

            int total = 0;
            ArrayList<PieEntry> entries = new ArrayList<>();
            for (Product p : produits) {
                entries.add(new PieEntry(p.quantity, p.name));
                total += p.quantity;
            }

            PieDataSet dataSet = new PieDataSet(entries, "Produits en stock");
            dataSet.setColors(
                    Color.rgb(0, 150, 136),
                    Color.rgb(255, 87, 34),
                    Color.rgb(63, 81, 181),
                    Color.rgb(244, 67, 54),
                    Color.rgb(255, 193, 7),
                    Color.rgb(139, 195, 74),
                    Color.rgb(233, 30, 99)
            );
            dataSet.setValueTextColor(Color.WHITE);
            dataSet.setValueTextSize(12f);

            PieData pieData = new PieData(dataSet);

            int finalTotal = total;
            runOnUiThread(() -> {
                pieChart.setData(pieData);
                pieChart.setUsePercentValues(false);
                pieChart.getDescription().setEnabled(false);
                pieChart.setCenterText("Stock total");
                pieChart.setCenterTextSize(16f);
                pieChart.setHoleRadius(45f);
                pieChart.animateY(1000);
                pieChart.invalidate(); // Rafraichir le graphique

                textStats.setText("Total produits : " + finalTotal);
            });
        });
    }
}
