package com.yuniss.remotecarcontrol.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.yuniss.remotecarcontrol.interfaces.DataAccessObj;
import com.yuniss.remotecarcontrol.model.User;


@Database(entities = {User.class},version = 1)
public abstract class DataBase extends RoomDatabase {

    public abstract DataAccessObj dataAccessObj();
}
