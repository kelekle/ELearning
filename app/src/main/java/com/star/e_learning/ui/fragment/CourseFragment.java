package com.star.e_learning.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.star.e_learning.R;
import com.star.e_learning.repository.AppRepository;
import com.star.e_learning.ui.activity.CourseDetailActivity;
import com.star.e_learning.ui.adapter.BannerAdapter;
import com.star.e_learning.ui.adapter.CourseItemAdapter;

import com.star.e_learning.api.ApiClient;
import com.star.e_learning.api.ApiInterface;
import com.star.e_learning.bean.Course;
import com.star.e_learning.bean.Model;
import com.star.e_learning.bean.MultiComment;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CourseFragment extends BaseFragment implements View.OnClickListener {

//    private Kanner kanner;
    private RecyclerView lv;
    private RecyclerView topLv;
    private ImageView errorImage;
    private TextView errorTitle, errorMessage, coursesTitle;
    private Button btnRetry;

    private CourseItemAdapter coursesAdapter;
    private BannerAdapter bannerAdapter;

    private RelativeLayout errorLayout;
    private SwipeRefreshLayout swipeRefreshLayout;
    private LinearLayout ll_dot, container;

    private int currentTopItem = 0;

    private AppRepository repository;

    private List<ImageView> iv_dots;
    private List<Course> topCourses;
    private List<Course> courseList;
    ArrayList<Model> models;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        topCourses = new ArrayList<Course>();
        repository = new AppRepository(getContext());
        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_titleBar_search:
                break;
        }
    }

    @Override
    public void setListener(){
        lv.setOnClickListener(this);
        topLv.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                LoadJson();
            }
        });
    }

    public void LoadJson(){
        errorLayout.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(true);
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Course>> call;
        call = apiInterface.getCourses();
        call.enqueue(new Callback<List<Course>>() {
            @Override
            public void onResponse(Call<List<Course>> call, final Response<List<Course>> response) {
                if (response.isSuccessful() && response.body() != null){
                    courseList = response.body();
                    generateData();
                    // prepare adapter and  attach to recyclerview
                    coursesTitle.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    repository.deleteAllCourses();
                    repository.insertCourses(courseList);
                } else {
                    coursesTitle.setVisibility(View.INVISIBLE);
                    swipeRefreshLayout.setRefreshing(false);
                    repository.fetchAllCourses()
                            .observe(getActivity(), new Observer<List<Course>>() {
                                @Override
                                public void onChanged(@Nullable List<Course> courses) {
                                    if(courses.size() > 0) {
                                        courseList = courses;
                                        generateData();
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
            public void onFailure(Call<List<Course>> call, final Throwable t) {
                System.out.println("failure");
                coursesTitle.setVisibility(View.INVISIBLE);
                swipeRefreshLayout.setRefreshing(false);
                repository.fetchAllCourses()
                        .observe(getActivity(), new Observer<List<Course>>() {
                            @Override
                            public void onChanged(@Nullable List<Course> courses) {
                                if(courses.size() > 0) {
                                    courseList = courses;
                                    generateData();
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

    public void generateData(){
        //clear
        if (!models.isEmpty()){
            models.clear();
        }
        if (!topCourses.isEmpty()){
            topCourses.clear();
        }
        //generate data
        for(int i = 0;i < 3;i++){
            topCourses.add(courseList.get(i));
        }
        bannerAdapter.resetList(topCourses);
        resetBanner();
        Model model;
        for(int i = 0;i < courseList.size();i++) {
            switch (i % 3){
                case 0:
                    model = new Model(Model.COURSE_IMAGE_TYPE, courseList.get(i));
                    models.add(model);
                    break;
                case 1:
                    model = new Model(Model.COURSE_TEXT_TYPE, courseList.get(i));
                    models.add(model);
                    break;
                case 2:
                    model = new Model(Model.LECTURE_TYPE, courseList.get(i));
                    models.add(model);
                    break;
            }
        }
        model = new Model(Model.ENROLLMENT_TYPE, String.valueOf(courseList.size()));
        models.add(model);
        model = new Model(Model.SUMMARY_TYPE, new MultiComment(99, "This courses just so rellay good!",
                80, "This course is general", 60, "This course can't be more bad!"));
        models.add(model);
        coursesAdapter.resetList(models);
    }

    private void showErrorMessage(int imageView, String title, String message){

        if (errorLayout.getVisibility() == View.GONE) {
            System.out.println("visible");
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

    @Override
    public void initData(){
        models = new ArrayList<Model>();
        coursesAdapter = new CourseItemAdapter(getContext());
        lv.setAdapter(coursesAdapter);
        bannerAdapter = new BannerAdapter(getContext());
//        bannerAdapter.resetList(courses);
        topLv.setAdapter(bannerAdapter);
        coursesAdapter.setOnItemClickListener(new CourseItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(!"".equals(coursesAdapter.getCourseID(position))){
                    System.out.println("aaaaaa");
                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                    intent.putExtra("cid", coursesAdapter.getCourseID(position));
                    intent.putExtra("title", coursesAdapter.getTitle(position));
                    intent.putExtra("description", coursesAdapter.getCourseDescription(position));
                    intent.putExtra("avatar", coursesAdapter.getCourseAvatar(position));
                    intent.putExtra("ishot", "no");
                    startActivity(intent);
                }
            }
        });
        bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                if(!"".equals(bannerAdapter.getCourseID(position))){
                    Intent intent = new Intent(getActivity(), CourseDetailActivity.class);
                    intent.putExtra("cid", bannerAdapter.getCourseID(position));
                    intent.putExtra("title", bannerAdapter.getTitle(position));
                    intent.putExtra("description", bannerAdapter.getCourseDescription(position));
                    intent.putExtra("avatar", bannerAdapter.getCourseAvatar(position));
                    intent.putExtra("ishot", "yes");
                    startActivity(intent);
                }
            }
        });
        LoadJson();
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        this.container = root.findViewById(R.id.container);
        topLv = root.findViewById(R.id.kanner);
        lv = root.findViewById(R.id.rv_courses);
        errorLayout = root.findViewById(R.id.errorLayout);
        errorImage = root.findViewById(R.id.errorImage);
        errorTitle = root.findViewById(R.id.errorTitle);
        errorMessage = root.findViewById(R.id.errorMessage);
        btnRetry = root.findViewById(R.id.btnRetry);
        swipeRefreshLayout = root.findViewById(R.id.swipe_refresh_layout);
        coursesTitle = root.findViewById(R.id.courses_title);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topLv.setLayoutManager(layoutManager);
        ll_dot = (LinearLayout) root.findViewById(R.id.ll_dot);
        iv_dots = new ArrayList<ImageView>();
        topLv.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastTopItem = currentTopItem;
                currentTopItem = layoutManager.findFirstVisibleItemPosition() % 3;
                updateIndicatorStatus(lastTopItem);

            }
        });

        lv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        new PagerSnapHelper().attachToRecyclerView(topLv);
        return root;
    }

    public void updateIndicatorStatus(int lastTopItem){
        if(lastTopItem != currentTopItem) {
            iv_dots.get(lastTopItem).setImageResource(R.mipmap.dot_blur);
            iv_dots.get(currentTopItem).setImageResource(R.mipmap.dot_focus);
        }
    }

    public void resetBanner(){
        int len = bannerAdapter.getSize();
        ll_dot.removeAllViews();
        iv_dots.clear();
        for (int i = 0; i < len; i++) {
            ImageView iv_dot = new ImageView(getContext());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 6;
            params.rightMargin = 6;
            ll_dot.addView(iv_dot, params);
            iv_dots.add(iv_dot);
        }
        if(len > 0){
            iv_dots.get(0).setImageResource(R.mipmap.dot_focus);
        }
        for(int i = 1;i < bannerAdapter.getSize();i++){
//            if(i != currentTopItem){
            iv_dots.get(i).setImageResource(R.mipmap.dot_blur);
        }
    }

    /**
     * Refresh
     */
    @Override
    public void refresh() {
        LoadJson();
    }

    /**
     * Called when a fragment will be displayed
     */
    @Override
    public void willBeDisplayed() {
        // Do what you want here, for example animate the content
        if (container != null) {
            Animation fadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            container.startAnimation(fadeIn);
        }
    }

    /**
     * Called when a fragment will be hidden
     */
    @Override
    public void willBeHidden() {
        if (container != null) {
            Animation fadeOut = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_out);
            container.startAnimation(fadeOut);
        }
    }

}