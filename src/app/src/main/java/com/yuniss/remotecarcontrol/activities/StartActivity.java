package com.yuniss.remotecarcontrol.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.yuniss.remotecarcontrol.R;
import com.yuniss.remotecarcontrol.helpers.Constants;
import com.yuniss.remotecarcontrol.helpers.Methods;

public class StartActivity extends AppCompatActivity {

    private TextView txtDeveloperCredit;
    private LinearLayout logo_container,credit_layout;
    private RelativeLayout sign_buttons;


    // Animations :
    private Animation slide_up, slide_up_sign_buttons;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);


        txtDeveloperCredit = findViewById(R.id.txtDeveloperCredit);
        logo_container = findViewById(R.id.logo_container);
        sign_buttons = findViewById(R.id.sign_buttons);
        credit_layout = findViewById(R.id.credit_layout);

        // Initial Animations
        slide_up = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up);
        slide_up_sign_buttons = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_up_sign_buttons);
        txtDeveloperCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Methods.openLink(Constants.DEVELOPER_CREDIT_URL,StartActivity.this);

            }
        });


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                slideUpLogo();
                slideUpSignButtons();


            }
        }, 1800);
    }

    private void slideUpLogo() {
        logo_container.startAnimation(slide_up);
    }

    private void slideUpSignButtons() {
        credit_layout.setVisibility(View.INVISIBLE);
        sign_buttons.startAnimation(slide_up_sign_buttons);
    }


}
