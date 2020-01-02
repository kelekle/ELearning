package com.star.e_learning.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.star.e_learning.R;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.util.AppConfig;
import com.star.e_learning.util.Utils;
import com.star.e_learning.bean.User;
import com.star.e_learning.repository.AppRepository;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.tencent.qq.QQ;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private Button login;
    private TextView forget;
    private TextView register;
    //actually it's the email
    private EditText username;
    private EditText password;
    private ImageView qqLogin;

    private AwesomeValidation awesomeValidation;
    private AppRepository appRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = findViewById(R.id.bt_login_submit);
        forget = findViewById(R.id.tv_login_forget_pwd);
        register = findViewById(R.id.tv_login_register);
        username = findViewById(R.id.et_login_username);
        password = findViewById(R.id.et_login_pwd);
        qqLogin = findViewById(R.id.iv_login_qq);
        login.setOnClickListener(this);
        forget.setOnClickListener(this);
        register.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        initValidation();
        verifyStoragePermissions();
        appRepository = new AppRepository(LoginActivity.this);
    }

    public void initValidation(){
        awesomeValidation.addValidation(username, "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", "请输入正确的邮箱");
        awesomeValidation.addValidation(password, "^[\\w]{6,12}$", "请输入6-12位密码");
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bt_login_submit:
                final Intent intent = new Intent();
                if(awesomeValidation.validate()){
                    final String email = username.getText().toString().trim();
                    final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
                    Call<JsonObject> call;
                    call = apiInterface.login(email, password.getText().toString().trim());
                    showProgressDialog(this, "正在登陆...");
                    call.enqueue(new Callback<JsonObject>() {
                        @Override
                        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                            JsonObject object = response.body();
                            dismissProgressDialog();
                            if (response.isSuccessful() && response.body() != null) {
                                System.out.println("bbb");
                                if (object.get("result").getAsString().equals("success")) {
                                    User user = new Gson().fromJson(object.get("user"), User.class);
                                    Utils.putBooleanValue(LoginActivity.this, AppConfig.LOGIN_STATE, true);
                                    Utils.putValue(LoginActivity.this, AppConfig.CURRENT_EMAIL, email);
                                    appRepository.insertUser(user);
                                    AppConfig.CURRENT_USER = user;
                                    intent.setClass(LoginActivity.this, HomeActivity.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                                    finish();
                                }else{
                                    Toast.makeText(LoginActivity.this,
                                            "登录失败, " + object.get("msg").getAsString() + "!",
                                            Toast.LENGTH_LONG).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonObject> call, Throwable t) {
                            dismissProgressDialog();
                            Toast.makeText(LoginActivity.this,
                                    "Network failure, Please Try Again" + t.toString(),
                                    Toast.LENGTH_LONG).show();
                        }
                    });
                }
                break;
            case R.id.tv_login_register:
                Intent intent1 = new Intent();
                intent1.setClass(LoginActivity.this, RegisterActivity.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.tv_login_forget_pwd:
                Intent intent2 = new Intent();
                intent2.setClass(LoginActivity.this, ForgetPwdActivity.class);
                startActivity(intent2);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                break;
            case R.id.iv_login_qq:
                final Intent intentqq = new Intent();
                Utils.showLongToast(LoginActivity.this, "You are using qq login!");
                final Platform plat = ShareSDK.getPlatform(QQ.NAME);
                ShareSDK.setActivity(this);//抖音登录适配安卓9.0
//回调信息，可以在这里获取基本的授权返回的信息，但是注意如果做提示和UI操作要传到主线程handler里去执行
                plat.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onError(Platform arg0, int arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
                        arg2.printStackTrace();
                    }

                    @Override
                    public void onComplete(Platform platform, int action, HashMap<String, Object> res) {
                        // TODO Auto-generated method stub
                        //输出所有授权信息
                        platform.getDb().exportData();
                        //遍历Map
                        Iterator ite =res.entrySet().iterator();
                        while (ite.hasNext()) {
                            Map.Entry entry = (Map.Entry)ite.next();
                            Object key = entry.getKey();
                            Object value = entry.getValue();
                            System.out.println(key+"： "+value);
                        }
                        if (action == Platform.ACTION_USER_INFOR) {
                            PlatformDb platDB = platform.getDb();//获取数平台数据DB
                            //通过DB获取各种数据
                            System.out.println("token: " + platDB.getToken());
                            System.out.println("userId: " + platDB.getUserId());
                            platDB.getUserGender();
                            platDB.getUserIcon();
                            platDB.getUserId();
                            platDB.getUserName();
                        }
                        Utils.putBooleanValue(LoginActivity.this, AppConfig.LOGIN_STATE, true);
                        AppConfig.CURRENT_USER = null;
                        intentqq.setClass(LoginActivity.this, HomeActivity.class);
                        startActivity(intentqq);
                        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
                        finish();
                    }

                    @Override
                    public void onCancel(Platform arg0, int arg1) {
                        // TODO Auto-generated method stub
                    }
                });
                plat.SSOSetting(false);
                plat.showUser(null);
                plat.removeAccount(true);
                break;
        }
    }

    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);	//设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();

        //设置超时自动消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //取消加载框
                if(dismissProgressDialog()){

                }
            }
        }, 30000);//超时时间30秒
    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE",
            "android.permission.WRITE_SETTINGS"};

    public void verifyStoragePermissions() {
        try {
            //检测是否有写的权限
//            int permission = ActivityCompat.checkSelfPermission(getApplicationContext(),
//                    "android.permission.WRITE_EXTERNAL_STORAGE");
//            if (permission != PackageManager.PERMISSION_GRANTED) {
//                // 没有写的权限，去申请写的权限，会弹出对话框
//                ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
//            }
            ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
