package com.yuniss.remotecarcontrol.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yuniss.remotecarcontrol.interfaces.CarDAO;
import com.yuniss.remotecarcontrol.interfaces.UseDAO;
import com.yuniss.remotecarcontrol.model.Car;
import com.yuniss.remotecarcontrol.model.User;


@Database(entities = {User.class, Car.class},version = 1, exportSchema = false)
public abstract class DataBase extends RoomDatabase {

    public abstract UseDAO dataAccessObj();
    public abstract CarDAO getCarDAO();
}
