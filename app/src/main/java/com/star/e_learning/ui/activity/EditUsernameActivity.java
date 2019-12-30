package com.star.e_learning.ui.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.rengwuxian.materialedittext.MaterialEditText;
import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.api.AppConfig;
import com.star.e_learning.api.Utils;
import com.star.e_learning.repository.AppRepository;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


//注册后填写用户信息
public class EditUsernameActivity extends AppCompatActivity implements
        View.OnClickListener {

    private ImageView img_back;
    private MaterialEditText materialEditText;
    private TextView save;
    ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
    private AppRepository appRepository;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_edit_username);
        super.onCreate(savedInstanceState);
        img_back = (ImageView) findViewById(R.id.img_back);
        materialEditText = findViewById(R.id.edit_input_content);
        materialEditText.setText(AppConfig.CURRENT_USER.getUsername());
        img_back.setOnClickListener(this);
        save = findViewById(R.id.save);
        save.setOnClickListener(this);
        save.setEnabled(false);
        materialEditText.setText(AppConfig.CURRENT_USER.getUsername());
        materialEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                save.setBackgroundColor(getResources().getColor(R.color.login_input_active));
                save.setEnabled(true);
            }
        });
        appRepository = new AppRepository(EditUsernameActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.save:
                final String tx = materialEditText.getText().toString();
                Call<String> call = apiInterface.changeUsername(tx);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful() && response.body() != null){
                            //
                            AppConfig.CURRENT_USER.setUsername(tx);
                            appRepository.updateUser(AppConfig.CURRENT_USER);
                            finish();
                        }else {
                            Utils.showLongToast(EditUsernameActivity.this, "未知错误，更改失败！");
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Utils.showLongToast(EditUsernameActivity.this, "未知错误，更改失败！");
                    }
                });
                break;
            default:
                break;
        }
    }

}

