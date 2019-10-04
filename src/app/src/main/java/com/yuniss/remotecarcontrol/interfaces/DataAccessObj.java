package com.yuniss.remotecarcontrol.interfaces;


import androidx.room.Dao;
import androidx.room.Insert;

import com.yuniss.remotecarcontrol.model.User;

@Dao
public interface DataAccessObj {


    @Insert
    public void addUser(User user);


}
