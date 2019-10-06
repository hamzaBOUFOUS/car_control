package com.yuniss.remotecarcontrol.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int uid;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "password")
    private String password;


    public User() {
    }

    @Ignore
    public User(String phone, String password) {
        this.phone = phone;
        this.password = password;
    }

    @Ignore
    public User(int uid, String phone, String password) {
        this.uid = uid;
        this.phone = phone;
        this.password = password;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}