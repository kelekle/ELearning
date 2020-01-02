package com.star.e_learning.ui.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.star.e_learning.R;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.util.AppConfig;
import com.star.e_learning.ui.activity.EditUserInfoActivity;
import com.star.e_learning.ui.activity.LoginActivity;
import com.star.e_learning.ui.activity.WebViewActivity;
import com.star.e_learning.util.Utils;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MeFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout flContainer;
    private Button exit;
    private CircleImageView head;
    private TextView about, username, msg;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = initView(inflater, container, savedInstanceState);
        return root;
    }

    @Override
    protected void setListener() {
        exit.setOnClickListener(this);
        about.setOnClickListener(this);
        msg.setOnClickListener(this);
    }

    @Override
    public void onResume(){
        super.onResume();
        initData();
        setListener();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txt_about:
                Utils.start_Activity(getActivity(), WebViewActivity.class);
                break;
            case R.id.btnexit:
                Utils.RemoveValue(getContext(), AppConfig.LOGIN_STATE);
                Utils.start_Activity(getActivity(), LoginActivity.class);
                getActivity().finish();
                break;
            case R.id.txt_msgtip:
                if(AppConfig.CURRENT_USER == null){
                    return;
                }
                Utils.start_Activity(getActivity(), EditUserInfoActivity.class);
                break;
            default:
                break;
        }
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_me, container, false);
        flContainer = root.findViewById(R.id.container);
        exit = root.findViewById(R.id.btnexit);
        about = root.findViewById(R.id.txt_about);
        head = root.findViewById(R.id.head);
        username = root.findViewById(R.id.username);
        msg = root.findViewById(R.id.txt_msgtip);
        return root;
    }

    @Override
    protected void initData() {
        System.out.println("rrrrrrrrrrrrrrrrrrrrrrr");
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        if(AppConfig.CURRENT_USER != null) {
            username.setText(AppConfig.CURRENT_USER.getUsername());
            File file = new File(getContext().getExternalFilesDir(null).getAbsolutePath()
                    + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
            if (file.exists()) {
                head.setImageBitmap(Utils.getLocalBitmap(getContext().getExternalFilesDir(null).getAbsolutePath()
                        + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg"));
            } else {
                final File photo = new File(getContext().getExternalFilesDir(null).getAbsolutePath()
                        + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg");
                System.out.println("start download user head");
                Call<ResponseBody> call1 = apiInterface.getUserPhoto(AppConfig.CURRENT_USER.getPhoto().replace("\\","/"));
                call1.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            new AsyncTask<Void, Void, Void>() {
                                @Override
                                protected Void doInBackground(Void... voids) {
                                    Utils.writeResponseBodyToDisk(photo.getAbsolutePath(), response.body());
                                    System.out.println("already download user head");
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            head.setImageBitmap(Utils.getLocalBitmap(getContext().getExternalFilesDir(null).getAbsolutePath()
                                                    + "/user/" + AppConfig.CURRENT_USER.getUserid() + "/head.jpg"));
                                        }
                                    });
                                    return null;
                                }
                            }.execute();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });
            }
        }
    }

    @Override
    public void refresh() {

    }

    @Override
    public void willBeDisplayed() {
        if(flContainer != null){
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            flContainer.startAnimation(fadeIn);
        }
    }

    @Override
    public void willBeHidden() {
        if(flContainer != null){
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            flContainer.startAnimation(fadeOut);
        }
    }

}

