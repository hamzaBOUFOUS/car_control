package com.yuniss.remotecarcontrol.interfaces;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.yuniss.remotecarcontrol.model.Car;
import com.yuniss.remotecarcontrol.model.User;

import java.util.List;

@Dao
public interface CarDAO {



    @Insert
    void addCar(Car car);

    @Query("SELECT * FROM cars")
    List<Car> getAll();

    @Query("SELECT * FROM cars where user_id = :user_id ORDER BY id DESC ")
    List<Car> findByUser(int user_id);

    @Query("SELECT COUNT(*) from cars")
    int countCars();


    @Update
    void updateCar(Car car);

    @Delete
    void delete(Car car);


}
