package com.yuniss.remotecarcontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.yuniss.remotecarcontrol.database.DataBase;
import com.yuniss.remotecarcontrol.activities.StartActivity;
import com.yuniss.remotecarcontrol.model.User;

public class MainActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "car_control";
    Button btnAdd;
    EditText txtPhone, txtPassword;
    public static DataBase dataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(MainActivity.this, StartActivity.class));

        dataBase = Room.databaseBuilder(getApplicationContext(),DataBase.class,DATABASE_NAME).allowMainThreadQueries().build();

        btnAdd = findViewById(R.id.btnAdd);
        txtPhone = findViewById(R.id.txtPhone);
        txtPassword = findViewById(R.id.txtPassword);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = txtPhone.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                dataBase.dataAccessObj().addUser(new User(1,phone,password));
                Toast.makeText(MainActivity.this,"new user added! ",Toast.LENGTH_LONG).show();
                txtPhone.setText("");
                txtPassword.setText("");
            }
        });
    }
}
