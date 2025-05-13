package com.example.gestionstockapp.view.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionstockapp.R;
import com.example.gestionstockapp.controller.ProductController;
import com.example.gestionstockapp.model.Product;
import com.example.gestionstockapp.view.EditProductActivity;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private final ProductController controller;

    public ProductAdapter(List<Product> productList, ProductController controller) {
        this.productList = productList;
        this.controller = controller;
    }

    public void updateData(List<Product> newList) {
        this.productList = newList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.textViewProduct.setText(product.name + " - " + product.quantity);

        // 🔴 Supprimer
        holder.buttonDelete.setOnClickListener(v -> {
            Log.d("DEBUG", "Suppression du produit : " + product.name);
            controller.deleteProduct(product);
            updateData(controller.getAllProducts());
            Log.d("DEBUG", "Produit supprimé et liste mise à jour");
        });

        // 🟢 Modifier (clic sur l'élément entier)
        holder.itemView.setOnClickListener(v -> {
            Log.d("DEBUG", "Clic sur produit pour modification : " + product.id);
            Intent intent = new Intent(v.getContext(), EditProductActivity.class);
            intent.putExtra("product_id", product.id);
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView textViewProduct;
        ImageButton buttonDelete;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewProduct = itemView.findViewById(R.id.textViewProduct);
            buttonDelete = itemView.findViewById(R.id.buttonDelete);
        }
    }
}
