package com.star.e_learning.ui.fragment;

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
import com.star.e_learning.api.AppConfig;
import com.star.e_learning.repository.AppRepository;
import com.star.e_learning.ui.activity.EditUserInfoActivity;
import com.star.e_learning.ui.activity.LoginActivity;
import com.star.e_learning.ui.activity.WebViewActivity;
import com.star.e_learning.api.Utils;

import de.hdodenhof.circleimageview.CircleImageView;

public class MeFragment extends BaseFragment implements View.OnClickListener{

    private LinearLayout flContainer;
    private Button exit;
    private CircleImageView head;
    private TextView about, msg, usersafe, username;

    private AppRepository repository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = initView(inflater, container, savedInstanceState);
        repository = new AppRepository(getContext());
        return root;
    }

    @Override
    protected void setListener() {
        exit.setOnClickListener(this);
        about.setOnClickListener(this);
        msg.setOnClickListener(this);
        usersafe.setOnClickListener(this);
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
                Utils.start_Activity(getActivity(), EditUserInfoActivity.class);
                break;
            case R.id.txt_usersafe:

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
        msg = root.findViewById(R.id.txt_msgtip);
        usersafe = root.findViewById(R.id.txt_usersafe);
        about = root.findViewById(R.id.txt_about);
        head = root.findViewById(R.id.head);
        username = root.findViewById(R.id.username);
        return root;
    }

//    public void getUserByNetwork(){
//        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//        Call<User> call;
//        call = apiInterface.getUser(Utils.getCurrentEmail(getContext()));
//        call.enqueue(new Callback<User>() {
//            @Override
//            public void onResponse(Call<User> call, Response<User> response) {
//                if (response.isSuccessful() && response.body() != null) {
//                    User user = response.body();
//                    username.setText(user.getUsername());
////                    repository.deleteUser();
//                    repository.insertUser(user);
//                }else{
//
//                }
//            }
//
//            @Override
//            public void onFailure(Call<User> call, Throwable t) {
//
//            }
//        });
//    }

//    public void getUserPhoto(User user) {
//        final String path = Environment.getDownloadCacheDirectory() + "/media_cache/" + user.getPhoto();
//        File photo = new File(path);
//        if (!photo.exists()) {
//            File file = new File(Environment.getDownloadCacheDirectory() + "/media_cache/user/" + user.getUserid());
//            if (!file.exists()) {
//                file.mkdir();
//            }
//            ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
//            apiInterface.getUserPhoto(Utils.getCurrentEmail(getContext()))
//                    .flatMap(new Function<ResponseBody, Publisher<Boolean>>() {
//                        @Override
//                        public Publisher<Boolean> apply(final ResponseBody responseBody) throws Exception {
//                            return Flowable.create(new FlowableOnSubscribe<Boolean>() {
//                                @Override
//                                public void subscribe(FlowableEmitter<Boolean> e) throws Exception {
//                                    File file1 = new File(path);
//                                    InputStream inputStream = null;
//                                    OutputStream outputStream = null;
//                                    try {
//                                        try {
//                                            int readLen;
//                                            byte[] buffer =new byte[1024];
//                                            inputStream = responseBody.byteStream();
//                                            outputStream = new FileOutputStream(file1);
//                                            while ((readLen = inputStream.read(buffer)) != -1 && !e.isCancelled()){
//                                                outputStream.write(buffer, 0, readLen);
//                                                outputStream.flush();
//                                                e.onComplete();
//                                            }
//                                        }finally {
//                                            if(outputStream != null){
//                                                outputStream.close();
//                                            }
//                                            if(inputStream != null){
//                                                inputStream.close();
//                                            }
//                                            if(responseBody != null){
//                                                responseBody.close();
//                                            }
//                                        }
//                                    }catch (Exception e1){
//                                        e1.printStackTrace();
//                                    }
//                                }
//                            }, BackpressureStrategy.LATEST);
//                        }
//                    });
//        }
//            Glide.with(getContext()).load(Uri.fromFile(photo.getAbsoluteFile())).into(head);

//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                if (response.isSuccessful() && response.body() != null) {
////                    User user = response.body();
////                    username.setText(user.getUsername());
////                    AppDatabase.getDatabase(getContext()).userDao().insertUser(user);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });
//        }
//    }

    @Override
    protected void initData() {

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

