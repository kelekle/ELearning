package com.star.e_learning;

import android.annotation.SuppressLint;
import android.app.IntentService;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.bean.Course;
import com.star.e_learning.repository.AppRepository;
import com.star.e_learning.ui.activity.LoginActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class PollingIntentService extends IntentService {

    private boolean linkSign = false;

    private AppRepository appRepository;

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            //发送广播
            Intent intent=new Intent("com.star.e_learning.PollingReceiver");
            intent.putExtra("description", "latest push from server side!");
//            intent.setAction("com.star.e_learning.PollingReceiver");
            //第一个参数为包的路径，第二个参数为类名
            intent.setComponent(new ComponentName(getPackageName(), "com.star.e_learning.PollingReceiver"));
            sendBroadcast(intent);
            super.handleMessage(msg);
        }
    };

    public PollingIntentService() {
        super("PollingIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        final ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        appRepository = new AppRepository(PollingIntentService.this);
        while(!linkSign) {
            System.out.println("开始请求");
            Log.e("开始请求", "" + System.currentTimeMillis());
                Call<List<Course>> call;
                call = apiInterface.getCourses();
                call.enqueue(new Callback<List<Course>>() {
                    @Override
                    public void onResponse(Call<List<Course>> call, final Response<List<Course>> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            List<Course> courseList = response.body();
                            System.out.println("my intent service");
                            appRepository.insertCourses(courseList);
                            Message message = new Message();
                            message.what = 1;
                            handler.sendMessage(message);
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Course>> call, final Throwable t) {
                        System.out.println("failure");
                    }
                });
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        }

    }


}
