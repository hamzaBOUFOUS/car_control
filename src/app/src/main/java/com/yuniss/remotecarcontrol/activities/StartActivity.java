package com.yuniss.remotecarcontrol.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.yuniss.remotecarcontrol.R;
import com.yuniss.remotecarcontrol.helpers.Constants;
import com.yuniss.remotecarcontrol.helpers.Methods;
import com.yuniss.remotecarcontrol.local.SessionsManager;
import com.yuniss.remotecarcontrol.model.User;
import com.yuniss.remotecarcontrol.model.UserDS;

public class StartActivity extends AppCompatActivity {

    private TextView txtDeveloperCredit, txt_alert;
    private LinearLayout logo_container, credit_layout, txt_boxes_login, txt_boxes_register, alertLayout;
    private RelativeLayout sign_buttons;

    // Database
    UserDS userDS;

    // EditTExts
    private EditText phone_number_txt, password_txt, phone_number_txt_reg, password_txt_reg, confirm_password_txt;

    // CONSTANTS
    private int METHOD_LOGGIN = Constants.START_LOGGING;
    private int METHOD_REGISTER = Constants.START_REGISTER;

    // Buttons
    private Button btn_login, btn_register;


    // Animations :
    private Animation slide_up, slide_up_sign_buttons,slide_down;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        txtDeveloperCredit = findViewById(R.id.txtDeveloperCredit);
        logo_container = findViewById(R.id.logo_container);
        sign_buttons = findViewById(R.id.sign_buttons);
        credit_layout = findViewById(R.id.credit_layout);
        txt_boxes_login = findViewById(R.id.txt_boxes_login);
        txt_boxes_register = findViewById(R.id.txt_boxes_register);
        alertLayout = findViewById(R.id.alertLayout);
        txt_alert = findViewById(R.id.txt_alert);

        // Initial EditTexts
        phone_number_txt = findViewById(R.id.phone_number_txt);
        password_txt = findViewById(R.id.password_txt);
        phone_number_txt_reg = findViewById(R.id.phone_number_txt_reg);
        password_txt_reg = findViewById(R.id.password_txt_reg);
        confirm_password_txt = findViewById(R.id.confirm_password_txt);

        // Initial Databse
        userDS = new UserDS(this);

        // Initial Buttons
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);


        // Setup OnClickListener
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (METHOD_LOGGIN == Constants.START_LOGGING) {
                    startLogin();
                } else if (METHOD_LOGGIN == Constants.ACTUAL_LOGIN) {
                    performLogin();
                }

            }
        });
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (METHOD_REGISTER == Constants.START_REGISTER) {
                    startRegister();
                } else if (METHOD_REGISTER == Constants.ACTUAL_REGISTER) {
                    performRegister();
                }
            }
        });

        // Initial Animations
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up);
        slide_down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_down);
        slide_up_sign_buttons = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_up_sign_buttons);
        txtDeveloperCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.openLink(Constants.DEVELOPER_CREDIT_URL, StartActivity.this);

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SessionsManager sessionsManager = new SessionsManager(StartActivity.this);
                if (sessionsManager.isLoggedIn() && sessionsManager.userexist()){
                    startActivity(new Intent(StartActivity.this, DashboardActivity.class));
                    finish();
                }else {
                    slideUpLogo();
                    slideUpSignButtons();
                }


            }
        }, 2000);
    }

    private void performRegister() {
        String phoneNumber = phone_number_txt_reg.getText().toString().trim();
        String password = password_txt_reg.getText().toString().trim();
        String confirm_password = confirm_password_txt.getText().toString().trim();

        if (phoneNumber.isEmpty() || password.isEmpty() || confirm_password.isEmpty()) {
            // Error Empty
            showAlert(Constants.DANGER,"رجاءا اكمل جميع الحقول!");
        } else if (phoneNumber.length() != Constants.PHONE_NUMBER_LENGTH) {
            // Error Phone Number Length
            showAlert(Constants.DANGER,"رقم الهاتف غير صحيح");
        } else if (password.length() < 6) {
            // Error Password must be at least 6 chars
            showAlert(Constants.DANGER,"كلمة السر يجب أن تكون على الأقل 6 أحرف");
        } else if (!password.equals(confirm_password)) {
            // Error Passwords don't match
            showAlert(Constants.DANGER,"كلمات المرور غير متطابقة");
        } else {
            // Every things okay
            userDS.addUser(new User(phoneNumber, password));
            Methods.myToast("success", this);
            startLogin();
            showAlert(Constants.SUCCESS,"تم إنشاء حسابك بنجاح");

        }
    }

    private void startRegister() {
        btn_login.setVisibility(View.GONE);
        txt_boxes_register.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sign_buttons.getLayoutParams();
        params.height = Methods.dipToPixels(370, this);
        sign_buttons.setLayoutParams(params);
        METHOD_REGISTER = Constants.ACTUAL_REGISTER;
    }

    private void performLogin() {
        String phoneNumber = phone_number_txt.getText().toString().trim();
        String password = password_txt.getText().toString().trim();


        if (phoneNumber.isEmpty() || password.isEmpty()) {
            // display Empty Error Message
            showAlert(Constants.DANGER,"رجاءا اكمل جميع الحقول!");
        } else if (phoneNumber.length() != Constants.PHONE_NUMBER_LENGTH) {
            // Display Error Length Phone number
            showAlert(Constants.DANGER,"يرجى إدخال رقم هاتف صالح");
        } else if (password.length() < 6) {
            // Display Error Length Password
            showAlert(Constants.DANGER,"كلمة المرور غير صحيحة");
        } else {
            // Start Verify Login
            User user = new User(phoneNumber, password);
            if (userDS.findByPhone(user.getPhone()) == null) {
                showAlert(Constants.DANGER,"لم يتم العثور على اي المستخدم");
            } else {
                if (!userDS.findByPhone(user.getPhone()).getPassword().equals(user.getPassword())) {
                    showAlert(Constants.DANGER,"كلمة المرور غير صحيحة");
                } else {
                    SessionsManager sessionsManager = new SessionsManager(this);
                    sessionsManager.setLogin(true);
                    sessionsManager.setUserId(userDS.findByPhone(user.getPhone()).getUid());
                    startActivity(new Intent(StartActivity.this,DashboardActivity.class));
                    finish();
                }
            }

        }
    }

    private void startLogin() {
        // show login Layout
        btn_register.setVisibility(View.GONE);
        txt_boxes_login.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.VISIBLE);
        txt_boxes_register.setVisibility(View.GONE);
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) sign_buttons.getLayoutParams();
        params.height = Methods.dipToPixels(310, this);
        sign_buttons.setLayoutParams(params);
        METHOD_LOGGIN = Constants.ACTUAL_LOGIN;

    }

    private void slideUpLogo() {
        logo_container.startAnimation(slide_up);
    }

    private void slideUpSignButtons() {
        credit_layout.setVisibility(View.INVISIBLE);
        sign_buttons.startAnimation(slide_up_sign_buttons);
    }


    public void showAlert(int type, String message) {
        switch (type) {
            case Constants.SUCCESS: {
                alertLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.success));
                break;
            }
            case Constants.DANGER: {
                alertLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.danger));
                break;
            }
        }

        txt_alert.setText(message);
        alertLayout.startAnimation(slide_down);

    }


}
