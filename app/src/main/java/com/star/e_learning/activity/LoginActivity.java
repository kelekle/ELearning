package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.star.e_learning.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private TextView forget;
    private TextView register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.bt_login_submit);
        forget = findViewById(R.id.tv_login_forget_pwd);
        register = findViewById(R.id.tv_login_register);
        login.setOnClickListener(this);
        forget.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch(view.getId()){
            case R.id.bt_login_submit:
                intent.setClass(LoginActivity.this, HomeActivity.class);
                break;
            case R.id.tv_login_register:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                break;
            case R.id.tv_login_forget_pwd:
                intent.setClass(LoginActivity.this, ForgetPwdActivity.class);
                break;
        }
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

}
