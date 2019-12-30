package com.star.e_learning.ui.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;

import com.star.e_learning.R;
import com.star.e_learning.api.AppConfig;
import com.star.e_learning.api.Utils;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.User;
import com.star.e_learning.repository.AppRepository;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;
    private AppRepository appRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        appRepository = new AppRepository(SplashActivity.this);
        Boolean isLogin = Utils.getBooleanValue(SplashActivity.this,
                AppConfig.LOGIN_STATE);
        if (isLogin) {
            getLogin();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.setClass(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                    finish();
                }
            }, SPLASH_DISPLAY_LENGTH);
        }
    }

    public void getLogin(){
        Intent intent = new Intent();
        intent.setClass(SplashActivity.this, HomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
        appRepository.getUserByEmail(Utils.getValue(SplashActivity.this, AppConfig.CURRENT_EMAIL))
                .observe(SplashActivity.this, new Observer<User>() {
                    @Override
                    public void onChanged(@Nullable User user) {
                        if(user != null){
                            AppConfig.CURRENT_USER = user;
                        }
                    }
                });
        finish();
    }

}
