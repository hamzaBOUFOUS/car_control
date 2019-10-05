package com.yuniss.remotecarcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuniss.remotecarcontrol.activities.DashboardActivity;
import com.yuniss.remotecarcontrol.database.DataBase;
import com.yuniss.remotecarcontrol.activities.StartActivity;
import com.yuniss.remotecarcontrol.local.SessionsManager;
import com.yuniss.remotecarcontrol.model.User;

public class MainActivity extends AppCompatActivity {


    private SessionsManager sessionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (sessionsManager.isLoggedIn()) {
            startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            finish();
        } else {
            startActivity(new Intent(MainActivity.this, StartActivity.class));
            sessionsManager = new SessionsManager(this);
        }


    }
}
