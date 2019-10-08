package com.yuniss.remotecarcontrol.model;

import android.content.Context;

import androidx.room.Room;

import com.yuniss.remotecarcontrol.database.DataBase;
import com.yuniss.remotecarcontrol.interfaces.CarDAO;

import java.util.List;

import static com.yuniss.remotecarcontrol.helpers.Constants.DATABASE_NAME;

public class CarDS implements CarDAO {

    public static DataBase dataBase;
    public Context context;

    public CarDS(Context context) {
        this.context = context;
        dataBase = Room.databaseBuilder(context, DataBase.class, DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Override
    public void addCar(Car car) {
        dataBase.getCarDAO().addCar(car);
    }

    @Override
    public List<Car> getAll() {
        return dataBase.getCarDAO().getAll();
    }


    @Override
    public List<Car> findByUser(int user_id) {
        return dataBase.getCarDAO().findByUser(user_id);
    }

    @Override
    public int countCars() {
        return dataBase.getCarDAO().countCars();
    }

    @Override
    public void updateCar(Car car) {
        dataBase.getCarDAO().updateCar(car);
    }

    @Override
    public void delete(Car car) {
        dataBase.getCarDAO().delete(car);
    }
}
