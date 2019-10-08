package com.yuniss.remotecarcontrol.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "cars")
public class Car {

    public Car() {
    }

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "title")
    private String title;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "user_id")
    private int user_id;

    @ColumnInfo(name = "password")
    private String password;

    @Ignore
    public Car(String title, String phone, int user_id, String password) {
        this.title = title;
        this.phone = phone;
        this.user_id = user_id;
        this.password = password;
    }

    @Ignore
    public Car(int uid, String title, String phone, int user_id, String password) {
        this.id = uid;
        this.title = title;
        this.phone = phone;
        this.user_id = user_id;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int uid) {
        this.id = uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
