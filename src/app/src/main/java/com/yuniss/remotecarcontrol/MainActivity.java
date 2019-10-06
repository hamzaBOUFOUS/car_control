package com.yuniss.remotecarcontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.yuniss.remotecarcontrol.activities.StartActivity;
import com.yuniss.remotecarcontrol.local.SessionsManager;

public class MainActivity extends AppCompatActivity {


    private SessionsManager sessionsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        startActivity(new Intent(MainActivity.this, StartActivity.class));


    }
}
