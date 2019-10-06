package com.yuniss.remotecarcontrol.interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.yuniss.remotecarcontrol.model.User;

import java.util.List;

@Dao
public interface UseDAO {


    @Insert
    void addUser(User user);

    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users where phone = :phone")
    User findByPhone(String phone);

    @Query("SELECT COUNT(*) from users")
    int countUsers();


    @Delete
    void delete(User user);


}
