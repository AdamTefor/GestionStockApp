package com.example.gestionstockapp.controller;

import android.content.Context;

import com.example.gestionstockapp.model.AppDatabase;
import com.example.gestionstockapp.model.Product;
import com.example.gestionstockapp.model.ProductDao;

import java.util.List;

public class ProductController {

    private final ProductDao productDao;

    public ProductController(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        productDao = db.productDao();
    }

    // Ajouter un produit
    public void addProduct(String name, int quantity) {
        Product product = new Product(name, quantity);
        productDao.insert(product);
    }

    // Retourner tous les produits
    public List<Product> getAllProducts() {
        return productDao.getAll();
    }

    // Supprimer un produit
    public void deleteProduct(Product product) {
        productDao.delete(product);
    }

    // Rechercher des produits
    public List<Product> searchProducts(String keyword) {
        return productDao.searchProducts("%" + keyword + "%");
    }

    // Obtenir un produit par ID
    public Product getProductById(int id) {
        return productDao.getById(id);
    }

    // Mettre à jour un produit
    public void updateProduct(Product product) {
        productDao.update(product);
    }
}
