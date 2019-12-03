package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.star.e_learning.R;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.google.common.collect.Range;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button login;
    private TextView forget;
    private TextView register;
    //actually it's the phone number
    private EditText username;
    private EditText password;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.bt_login_submit);
        forget = findViewById(R.id.tv_login_forget_pwd);
        register = findViewById(R.id.tv_login_register);
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_pwd);
        login.setOnClickListener(this);
        forget.setOnClickListener(this);
        register.setOnClickListener(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        initValidation();
    }

    public void initValidation(){
        awesomeValidation.addValidation(username, Patterns.PHONE, "请输入正确的手机号");
        awesomeValidation.addValidation(password, "^[\\w]{6,12}$", "请输入6-12位密码");
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch(view.getId()){
            case R.id.bt_login_submit:
                if(awesomeValidation.validate()){
                    intent.setClass(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                }
                break;
            case R.id.tv_login_register:
                intent.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_login_forget_pwd:
                intent.setClass(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
        }
    }

}
