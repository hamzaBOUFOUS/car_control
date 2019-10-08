package com.yuniss.remotecarcontrol.activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.yuniss.remotecarcontrol.R;
import com.yuniss.remotecarcontrol.helpers.Constants;
import com.yuniss.remotecarcontrol.local.Current;
import com.yuniss.remotecarcontrol.model.CarDS;

import java.util.ArrayList;
import java.util.List;

public class CarDetails extends AppCompatActivity {


    private SmsManager smsManager;
    private CardView begin_start, change_password, manage_admins,car_tracking,manage_speed,system_position,shut_down_car,start_car,emergency;
    private static final int PERMISSION_REQUEST_CODE = 1;
    private RelativeLayout alert_layout;
    private TextView message_alert, close_btn,delete_car,nameOfCar;
    private CarDS carDS;

    private Dialog changePasswordDialog, manageAdminsDialog, carTrackignDialog, manageSpeed,systemPositionDialog,emergencyDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_details);

        carDS = new CarDS(this);

        alert_layout = findViewById(R.id.alert_layout);
        message_alert = findViewById(R.id.message_alert);
        close_btn = findViewById(R.id.close_btn);
        nameOfCar = findViewById(R.id.nameOfCar);

        nameOfCar.setText(Current.currentCar.getTitle());
        close_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                hideAlert();
            }
        });

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkPermission()) {
                Log.e("permission", "Permission already granted.");
            } else {
                requestPermission();
            }
        }

        // Initial Pop Ups
        changePasswordDialog = new Dialog(this);
        changePasswordDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        manageAdminsDialog = new Dialog(this);
        manageAdminsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        carTrackignDialog = new Dialog(this);
        carTrackignDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        manageSpeed = new Dialog(this);
        manageSpeed.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        systemPositionDialog = new Dialog(this);
        systemPositionDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        emergencyDialog = new Dialog(this);
        emergencyDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);


        // Initial Cards
        begin_start = findViewById(R.id.begin_start);
        change_password = findViewById(R.id.change_password);
        manage_admins = findViewById(R.id.manage_admins);
        car_tracking = findViewById(R.id.car_tracking);
        manage_speed = findViewById(R.id.manage_speed);
        system_position = findViewById(R.id.system_position);
        shut_down_car = findViewById(R.id.shut_down_car);
        start_car = findViewById(R.id.start_car);
        emergency = findViewById(R.id.emergency);

        // OnClickListeners
        begin_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                beginStart();
            }
        });
        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowChangePassword();
            }
        });
        manage_admins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageAdmin();
            }
        });
        car_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCarTracking();
            }
        });
        manage_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showManageSpeed();
            }
        });
        system_position.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSystemPosition();
            }
        });
        start_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startCar();
            }
        });
        shut_down_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shutDownCar();
            }
        });
        emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEmergency();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void showEmergency() {
        Button btn_start_emergency,btn_cancel_emergency;
        emergencyDialog.setContentView(R.layout.pop_emergency);
        emergencyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_start_emergency = emergencyDialog.findViewById(R.id.btn_start_emergency);
        btn_cancel_emergency = emergencyDialog.findViewById(R.id.btn_cancel_emergency);
        btn_start_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startEmergency();
            }
        });
        btn_cancel_emergency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopEmergency();
            }
        });

        emergencyDialog.show();
    }

    private void stopEmergency() {
        String sms = Constants.STOP_EMERGENCY+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم  الغاء حالة التاهب ");
            emergencyDialog.dismiss();
        }
    }

    private void startEmergency() {
        String sms = Constants.START_EMERGENCY+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم تفعيل حالة التاهب ");
            emergencyDialog.dismiss();
        }
    }

    private void shutDownCar() {
        String sms = Constants.STOP_CAR_CODE+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم إيقاف السيارة");
        }
    }

    private void startCar() {
        String sms = Constants.START_CAR_CODE+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم تشغيل السيارة");
        }
    }

    private void showSystemPosition() {
        Button btn_save;
        final RadioGroup radio_group;
        final TextView txt_error_alert;
        systemPositionDialog.setContentView(R.layout.pop_system_position);
        systemPositionDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        radio_group = systemPositionDialog.findViewById(R.id.radio_group);
        txt_error_alert = systemPositionDialog.findViewById(R.id.txt_error_alert);
        btn_save = systemPositionDialog.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (radio_group.getCheckedRadioButtonId()){
                    case R.id.rb_track: {
                        setPosition("tracker");
                        break;
                    }
                    case R.id.rb_listen: {
                        setPosition("monitor");
                        break;
                    }
                    case -1: {
                        txt_error_alert.setVisibility(View.VISIBLE);
                        txt_error_alert.setText("اختر وضعية");
                        break;
                    }
                }
            }
        });

        systemPositionDialog.show();



    }

    private void setPosition(String monitor) {
        String sms = monitor+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم الحفظ");
            systemPositionDialog.dismiss();
        }
    }

    private void showManageSpeed() {
        final TextView txt_error_alert;
        final Spinner speed_sp;
        Button btn_save,btn_cancel_speed;
        manageSpeed.setContentView(R.layout.pop_manage_speed);
        manageSpeed.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        speed_sp = manageSpeed.findViewById(R.id.speed_sp);
        txt_error_alert = manageSpeed.findViewById(R.id.txt_error_alert);
        btn_save = manageSpeed.findViewById(R.id.btn_save);
        btn_cancel_speed = manageSpeed.findViewById(R.id.btn_cancel_speed);

        List<String> list_speed = new ArrayList<>();
        list_speed.add("اختر السرعة");
        list_speed.add("040");
        list_speed.add("080");
        list_speed.add("120");
        list_speed.add("140");
        list_speed.add("180");

        ArrayAdapter<String> speedAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_speed);
        speedAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        speed_sp.setAdapter(speedAdapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(speed_sp.getSelectedItemId() == 0){
                    txt_error_alert.setVisibility(View.VISIBLE);
                    txt_error_alert.setText("اختر السرعة");
                }else {
                    saveSpeed(speed_sp.getSelectedItem().toString());
                }
            }
        });

        btn_cancel_speed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelSpeed();
            }
        });


        manageSpeed.show();

    }

    private void cancelSpeed() {
        String sms = "nospeed"+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم الغاء تحديد السرعة بنجاح");
            manageSpeed.dismiss();
        }
    }

    private void saveSpeed(String speed) {
        String sms = "speed"+Current.currentCar.getPassword()+" "+speed;
        if(sendSMS(sms)){
            showAlert("تم الحفظ");
            manageSpeed.dismiss();
        }
    }

    private void showCarTracking() {
        final Spinner nmbr_msg_sp,time_sp;
        Button btn_save,btn_cancel_tracking;
        final TextView txt_error_alert;
        carTrackignDialog.setContentView(R.layout.pop_car_tracking);
        carTrackignDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        nmbr_msg_sp = carTrackignDialog.findViewById(R.id.nmbr_msg_sp);
        time_sp = carTrackignDialog.findViewById(R.id.time_sp);
        btn_save = carTrackignDialog.findViewById(R.id.btn_save);
        btn_cancel_tracking = carTrackignDialog.findViewById(R.id.btn_cancel_tracking);
        txt_error_alert = carTrackignDialog.findViewById(R.id.txt_error_alert);

        // Fill Drop Down number_msg
        List<String> list_number = new ArrayList<>();
        list_number.add("عدد الرسائل");
        list_number.add("001");
        list_number.add("004");
        list_number.add("008");
        list_number.add("012");
        list_number.add("016");
        ArrayAdapter<String> numberAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_number);
        numberAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nmbr_msg_sp.setAdapter(numberAdapter);

        // Fill Drop Down time
        List<String> list_time = new ArrayList<>();
        list_time.add("المدة الزمنية");
        list_time.add("1 دقيقه");
        list_time.add("5 دقائق");
        list_time.add("15 دقيقه");
        list_time.add("30 دقيقه");
        list_time.add("ساعه");
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list_time);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        time_sp.setAdapter(timeAdapter);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nmbr_msg_sp.getSelectedItemId() == 0){
                    txt_error_alert.setVisibility(View.VISIBLE);
                    txt_error_alert.setText("اختر عدد الرسائل");
                }else if (time_sp.getSelectedItemId() == 0){
                    txt_error_alert.setVisibility(View.VISIBLE);
                    txt_error_alert.setText("اختر المدة الزمنية");
                } else {
                    switch ((int) time_sp.getSelectedItemId()){
                        case 1: carTracking(nmbr_msg_sp.getSelectedItem().toString(),"001");
                        case 2: carTracking(nmbr_msg_sp.getSelectedItem().toString(),"005");
                        case 3: carTracking(nmbr_msg_sp.getSelectedItem().toString(),"015");
                        case 4: carTracking(nmbr_msg_sp.getSelectedItem().toString(),"030");
                        case 5: carTracking(nmbr_msg_sp.getSelectedItem().toString(),"060");
                    }
                    carTrackignDialog.dismiss();
                }
            }
        });

        btn_cancel_tracking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                carTrackingDisable();
                carTrackignDialog.dismiss();
            }
        });



        carTrackignDialog.show();

    }


    private void carTrackingDisable() {
        String sms = "nofix"+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم الغاء التعقب بنجاح");
        }
    }


    private void carTracking(String numbr_msg, String time) {
        String sms = "fix"+time+"m"+numbr_msg+"n"+Current.currentCar.getPassword();
        if(sendSMS(sms)){
            showAlert("تم الحفظ بنجاح");
        }
    }

    private void showManageAdmin() {
        final String ADD_NUMBER_TXT = "اضافة الرقم";
        final String DELETE_NUMBER_TXT = "حذف الرقم";
        final String ADMIN_NUMBER_TXT = "اجلعه كمدير اساسي";
        final EditText phone_txt;
        final TextView txt_error_alert;
        Button btn_save;
        final Spinner action;
        manageAdminsDialog.setContentView(R.layout.pop_manage_admins);
        manageAdminsDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        phone_txt = manageAdminsDialog.findViewById(R.id.phone_txt);
        btn_save = manageAdminsDialog.findViewById(R.id.btn_save);
        action = manageAdminsDialog.findViewById(R.id.action);
        txt_error_alert = manageAdminsDialog.findViewById(R.id.txt_error_alert);

        List<String> list = new ArrayList<>();
        list.add(ADD_NUMBER_TXT);
        list.add(DELETE_NUMBER_TXT);
        list.add(ADMIN_NUMBER_TXT);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        action.setAdapter(dataAdapter);



        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String phone = phone_txt.getText().toString().trim();
                if (phone.isEmpty()){
                    txt_error_alert.setText("ادخل رقم الهاتف");
                    txt_error_alert.setVisibility(View.VISIBLE);
                } else if(phone.length() != Constants.PHONE_NUMBER_LENGTH){
                    txt_error_alert.setText("ادخل رقم الهاتف صحيح");
                    txt_error_alert.setVisibility(View.VISIBLE);
                } else {
                    switch (String.valueOf(action.getSelectedItem())) {
                        case ADD_NUMBER_TXT: {
                            changeAdmins(Constants.ADD_NUMBER_CODE,phone);
                            break;
                        }
                        case DELETE_NUMBER_TXT: {
                            changeAdmins(Constants.DELETE_NUMBER_CODE,phone);
                            break;
                        }
                        case ADMIN_NUMBER_TXT: {
                            changeAdmins(Constants.MK_ADMIN_NUMBER_CODE,phone);
                            break;
                        }
                    }
                    manageAdminsDialog.dismiss();
                }

            }
        });


        manageAdminsDialog.show();
    }

    private void changeAdmins(String action,String phone) {
        String sms = action + Current.currentCar.getPassword() + " " + phone;
        if(sendSMS(sms)){
            switch (action){
                case Constants.ADD_NUMBER_CODE: showAlert("تمت إضافة " + phone); break;
                case Constants.DELETE_NUMBER_CODE: showAlert( " تم حذف "+phone); break;
                case Constants.MK_ADMIN_NUMBER_CODE: showAlert( " تم إضافة " + phone +  " كمدير "); break;
            }

        }
    }

    private void ShowChangePassword() {
        final EditText password_txt, confirm_password_txt;
        final TextView txt_error_alert;
        Button btn_change_pwd;
        changePasswordDialog.setContentView(R.layout.pop_change_pwd);
        changePasswordDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        password_txt = changePasswordDialog.findViewById(R.id.password_txt);
        confirm_password_txt = changePasswordDialog.findViewById(R.id.confirm_password_txt);
        btn_change_pwd = changePasswordDialog.findViewById(R.id.btn_change_pwd);
        txt_error_alert = changePasswordDialog.findViewById(R.id.txt_error_alert);


        btn_change_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String password = password_txt.getText().toString().trim();
                String confirm_password = confirm_password_txt.getText().toString().trim();
                if (password.length() != Constants.PASSWORD_LENGTH) {
                    txt_error_alert.setText("يجب أن تكون كلمة المرور = " + Constants.PASSWORD_LENGTH + " ارقام");
                    txt_error_alert.setVisibility(View.VISIBLE);
                } else if (!password.equals(confirm_password)) {
                    txt_error_alert.setText("كلمات المرور غير متطابقة");
                    txt_error_alert.setVisibility(View.VISIBLE);
                } else {
                    changePassword(password);
                    changePasswordDialog.dismiss();
                }
            }
        });


        changePasswordDialog.show();
    }




    private void changePassword(String password) {
        String sms = Constants.CHANGE_PASSWORD_CODE + Current.currentCar.getPassword() + " " + password;
        if(sendSMS(sms)){
            Current.currentCar.setPassword(password);
            carDS.updateCar(Current.currentCar);
            showAlert("تم تغير كلمة المرور بنجاح");
        }
    }


    private void beginStart() {
        String sms = Constants.BEGIN_CODE + Current.currentCar.getPassword();
        sendSMS(sms);

    }


    private void hideAlert() {
        alert_layout.setVisibility(View.INVISIBLE);
    }

    public void showAlert(String message) {
        alert_layout.setVisibility(View.VISIBLE);
        message_alert.setText(message);
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_CODE);

    }

    private boolean sendSMS(String messageSMS) {
        if (checkPermission()) {
            smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Current.currentCar.getPhone(), null, messageSMS, null, null);
            showAlert(" تم تهيئة الجهاز لأول استخدام بنجاح");
            return true;
        } else {
            Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(CarDetails.this, Manifest.permission.SEND_SMS);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(CarDetails.this,
                            "Permission accepted", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CarDetails.this,
                            "Permission denied", Toast.LENGTH_LONG).show();
                }
                break;
        }
    }
}
