package com.yuniss.remotecarcontrol.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuniss.remotecarcontrol.R;
import com.yuniss.remotecarcontrol.adapters.CarAdapter;
import com.yuniss.remotecarcontrol.helpers.Constants;
import com.yuniss.remotecarcontrol.local.SessionsManager;
import com.yuniss.remotecarcontrol.model.Car;
import com.yuniss.remotecarcontrol.model.CarDS;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {


    private Dialog addCarPopUp;
    private Button btn_add_car, btn_add;
    private EditText car_name, car_phone, car_password;
    private LinearLayout layout_error_alert;
    private TextView txt_error_alert;
    private SessionsManager sessionsManager;
    private CarDS carDS;
    private RecyclerView recycle_view;
    private List<Car> carList = new ArrayList<>();
    private CarAdapter carAdapter;
    private RelativeLayout alert_layout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        carDS = new CarDS(this);
        addCarPopUp = new Dialog(this);
        addCarPopUp.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        sessionsManager = new SessionsManager(this);
        recycle_view = findViewById(R.id.recycle_view);
        alert_layout = findViewById(R.id.alert_layout);



        initialCars();

        btn_add_car = findViewById(R.id.btn_add_car);
        btn_add_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCarPopUp.setContentView(R.layout.pop_add_car);
                addCarPopUp.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                btn_add = addCarPopUp.findViewById(R.id.btn_add);
                car_name = addCarPopUp.findViewById(R.id.car_name);
                car_phone = addCarPopUp.findViewById(R.id.car_phone);
                car_password = addCarPopUp.findViewById(R.id.car_password);
                layout_error_alert = addCarPopUp.findViewById(R.id.layout_error_alert);
                txt_error_alert = addCarPopUp.findViewById(R.id.txt_error_alert);

                btn_add.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String name = car_name.getText().toString().trim();
                        String phone = car_phone.getText().toString().trim();
                        String password = car_password.getText().toString().trim();

                        performAddCar(name, phone, password);
                    }
                });
                addCarPopUp.show();

            }
        });
    }

    private void initialCars() {
        carList = carDS.findByUser(sessionsManager.getUserId());
        carAdapter = new CarAdapter(carList,this);
        recycle_view.setHasFixedSize(true);
        recycle_view.setLayoutManager(new LinearLayoutManager(this));
        recycle_view.setItemAnimator(new DefaultItemAnimator());
        recycle_view.setAdapter(carAdapter);
    }

    private void performAddCar(String name, String phone, String password) {
        if (name.isEmpty()) {
            showError("رجاء ادخل اسما صحيحا");
        } else if (name.length() > 20) {
            showError("يجب أن يكون الاسم أقل من 20 أحرف");
        } else if (phone.isEmpty()) {
            showError("يرجى إدخال رقم هاتف صالح");
        } else if (phone.length() != Constants.PHONE_NUMBER_LENGTH){
            showError("رقم الهاتف يجب ان يكون "+Constants.PHONE_NUMBER_LENGTH + " ارقام");
        } else if (password.isEmpty()){
            showError("الرجاء إدخال كلمة المرور");
        } else if (password.length() != Constants.PASSWORD_LENGTH){
            showError("يجب أن تكون كلمة المرور = "+Constants.PASSWORD_LENGTH + " ارقام");
        } else {
            addCar(new Car(name,phone,sessionsManager.getUserId(),password));
            showAlert("");
            addCarPopUp.dismiss();
            carAdapter = new CarAdapter(carDS.findByUser(sessionsManager.getUserId()),this);
            recycle_view.setAdapter(carAdapter);
        }
    }


    @Override
    public void onBackPressed() {


    }

    private void showAlert(String s) {
        alert_layout.setVisibility(View.VISIBLE);

    }

    private void addCar(Car car) {
        carDS.addCar(car);
    }

    private void showError(String message) {
        layout_error_alert.setVisibility(View.VISIBLE);
        txt_error_alert.setText(message);
    }
}
