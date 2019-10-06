package com.yuniss.remotecarcontrol.model;

import android.content.Context;

import androidx.room.Room;

import com.yuniss.remotecarcontrol.database.DataBase;
import com.yuniss.remotecarcontrol.interfaces.UseDAO;

import java.util.List;

import static com.yuniss.remotecarcontrol.helpers.Constants.DATABASE_NAME;

public class UserDS implements UseDAO {


    public static DataBase dataBase;
    public Context context;

    public UserDS(Context context) {
        this.context = context;
        dataBase = Room.databaseBuilder(context,DataBase.class,DATABASE_NAME).allowMainThreadQueries().build();
    }

    @Override
    public void addUser(User user) {
        dataBase.dataAccessObj().addUser(new User(user.getPhone(),user.getPassword()));
    }

    @Override
    public List<User> getAll() {
        return dataBase.dataAccessObj().getAll();
    }

    @Override
    public User findByPhone(String phone) {
        return dataBase.dataAccessObj().findByPhone(phone);
    }

    @Override
    public int countUsers() {
        return dataBase.dataAccessObj().countUsers();
    }

    @Override
    public void delete(User user) {
        dataBase.dataAccessObj().delete(user);
    }
}
