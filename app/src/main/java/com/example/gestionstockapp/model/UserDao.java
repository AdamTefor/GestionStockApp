package com.example.gestionstockapp.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insert(User user);

    @Query("SELECT * FROM `User` WHERE email = :email AND password = :password")
    User login(String email, String password);
    @Query("SELECT * FROM User")
    List<User> getAllUsers();

}

