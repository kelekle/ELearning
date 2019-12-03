package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.star.e_learning.R;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView title;
    private ImageView back;

    private EditText phone;
    private EditText password;
    private EditText confirmPassword;
    private EditText verify;

    private Button submit;

    private AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        title = findViewById(R.id.txt_title);
        title.setText("注册");
        back = findViewById(R.id.img_back);
        back.setVisibility(View.VISIBLE);
        phone = findViewById(R.id.et_register_phone);
        password = findViewById(R.id.et_register_password);
        verify = findViewById(R.id.et_register_verify);
        submit = findViewById(R.id.bt_register_submit);
        confirmPassword = findViewById(R.id.et_register_confirm_password);

        submit.setOnClickListener(this);
        back.setOnClickListener(this);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        addValidation();
    }

    public void addValidation() {
        awesomeValidation.addValidation(phone, Patterns.PHONE, "请输入正确的手机号");
        awesomeValidation.addValidation(verify, "^\\d{4}", "验证码为4位数字");
        awesomeValidation.addValidation(password, "^[\\w]{6,12}$", "请输入6-12位密码");
        awesomeValidation.addValidation(confirmPassword, password, "两次密码不一致");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bt_register_submit:
                if (awesomeValidation.validate()) {
                    Toast.makeText(this, "注册成功!", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.img_back:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
        }
    }

}
