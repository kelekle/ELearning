package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.star.e_learning.R;

public class ForgetPwdActivity extends AppCompatActivity {

    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        title = findViewById(R.id.txt_title);
        title.setText("忘记密码");
        back = findViewById(R.id.img_back);
        back.setVisibility(View.VISIBLE);
    }
}
