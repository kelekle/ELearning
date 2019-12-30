package com.star.e_learning.ui.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.star.e_learning.R;
import com.star.e_learning.bean.Course;
import com.star.e_learning.repository.AppRepository;
import com.star.e_learning.ui.activity.CourseDetailActivity;
import com.star.e_learning.ui.adapter.TeacherAdapter;
import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.bean.Teacher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherFragment extends BaseFragment {

    private String cid;
    private RecyclerView rv;
    private TeacherAdapter teacherAdapter;

    private FrameLayout flContainer;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage;
    private Button btnRetry;

    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;

    private List<Teacher> models;
    private AppRepository repository;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = initView(inflater, container, savedInstanceState);
        repository = new AppRepository(getContext());
        return root;
    }

    @Override
    protected void setListener() {
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJson();
            }
        });
    }

    @Override
    protected void initData() {
        teacherAdapter = new TeacherAdapter(getContext());
        rv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv.setAdapter(teacherAdapter);
        models = new ArrayList<Teacher>();
        cid = ((CourseDetailActivity) getActivity()).getCourseID();
        LoadJson();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_teacher, container, false);
        rv = root.findViewById(R.id.rv_teachers);
        flContainer = root.findViewById(R.id.container);
        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage = root.findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        return root;
    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            errorLayout.setVisibility(View.VISIBLE);
        }

        errorImage.setImageResource(imageView);
        errorTitle.setText(title);
        errorMessage.setText(message);

        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoadJson();
            }
        });

    }

    public void LoadJson(){
        if("".equals(cid)){
            showErrorMessage(
                    R.mipmap.oops,
                    "Oops..",
                    "Something wrong, Please Exit And Try Again\n");
            return;
        }
        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);

        Call<List<Teacher>> call;
        call = apiInterface.getCourseTeachers(cid);

        call.enqueue(new Callback<List<Teacher>>() {
            @Override
            public void onResponse(Call<List<Teacher>> call, final Response<List<Teacher>> response) {
                if (response.isSuccessful() && response.body() != null){
                    if (!models.isEmpty()){
                        models.clear();
                    }
                    models = response.body();
                    // prepare adapter and  attach to recyclerview
                    teacherAdapter.resetList(models);
                    swipeRefreshLayout.setRefreshing(false);
                    repository.deleteTeacherByCourseId(cid);
                    repository.insertTeachers(models);
                } else {
                    swipeRefreshLayout.setRefreshing(false);
                    repository.getTeacherByCourseId(cid)
                            .observe(getActivity(), new Observer<List<Teacher>>() {
                                @Override
                                public void onChanged(@Nullable List<Teacher> teachers) {
                                    if(teachers.size() > 0) {
                                        models = teachers;
                                        teacherAdapter.resetList(models);
                                    }else {
                                        String errorCode;
                                        switch (response.code()) {
                                            case 404:
                                                errorCode = "404 not found";
                                                break;
                                            case 500:
                                                errorCode = "500 server broken";
                                                break;
                                            default:
                                                errorCode = "unknown error";
                                                break;
                                        }
                                        showErrorMessage(
                                                R.mipmap.no_result,
                                                "No Result",
                                                "Please Try Again!\n"+
                                                        errorCode);
                                    }
                                }
                            });
                }
            }

            @Override
            public void onFailure(Call<List<Teacher>> call, final Throwable t) {
                swipeRefreshLayout.setRefreshing(false);
                repository.getTeacherByCourseId(cid)
                        .observe(getActivity(), new Observer<List<Teacher>>() {
                            @Override
                            public void onChanged(@Nullable List<Teacher> teachers) {
                                if(teachers.size() > 0) {
                                    models = teachers;
                                    teacherAdapter.resetList(models);
                                }else {
                                    showErrorMessage(
                                            R.mipmap.oops,
                                            "Oops..",
                                            "Network failure, Please Try Again\n"+
                                                    t.toString());
                                }
                            }
                        });

            }
        });

    }

    @Override
    public void refresh() {
        LoadJson();
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
