package com.star.e_learning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.star.e_learning.R;
import com.star.e_learning.activity.CalendarActivity;
import com.star.e_learning.adapter.BannerAdapter;
import com.star.e_learning.adapter.CourseItemAdapter;
import com.star.e_learning.bean.Course;
import com.star.e_learning.activity.CourseContentActivity;
import com.star.e_learning.view.Kanner;

import java.util.ArrayList;
import java.util.List;


public class CourseFragment extends BaseFragment implements View.OnClickListener {

//    private Kanner kanner;
    private RecyclerView lv;
    private RecyclerView topLv;
    private CourseItemAdapter mAdapter;
    private BannerAdapter bannerAdapter;
    private LinearLayout container;
    private LinearLayout ll_dot;
    private int currentTopItem = 0;
    private List<ImageView> iv_dots;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = initView(inflater, container, savedInstanceState);
        setListener();
        return root;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titleBar_me_iv:
                break;
            case R.id.titleBar_calendar_iv:
                //message
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_titleBar_search:
                break;
        }
    }

    @Override
    public void setListener(){
//        msg.setOnClickListener(this);
//        me.setOnClickListener(this);
//        search.setOnClickListener(this);
    }

    @Override
    public void initData(){
        List<Course> courses = new ArrayList<Course>();
        Course course = new Course();
        course.setId(1);
        course.setType(4);
        course.setTitle("Physic");
        course.setDate("2019-07-05");
        course.setImage("https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E7%89%A9%E7%90%86&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=550565696,1159541160&os=3049789218,1376553891&simid=3120356360,2814829564&pn=2&rn=1&di=213870&ln=1287&fr=&fmq=1570873502982_R&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=11&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fi0.hdslb.com%2Fbfs%2Farticle%2F1327aaeb1926dcb11a9bab78701bddeba251cd22.png&rpstart=0&rpnum=0&adpicid=0&force=undefined");
        course.setTeacher("wang");
        courses.add(course);
        courses.add(course);
        courses.add(course);
        courses.add(course);
        courses.add(course);
        bannerAdapter = new BannerAdapter(getContext(), courses);
        topLv.setAdapter(bannerAdapter);
        resetBanner();
        bannerAdapter.setOnItemClickListener(new BannerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), CourseContentActivity.class);
                startActivity(intent);
            }
        });
//        kanner.setTopEntities(courses);
        mAdapter = new CourseItemAdapter(getContext());
        List<Course> mainCourses = new ArrayList<>();
        Course mainCourse = new Course();
        mainCourse.setType(3);
        mainCourse.setId(2);
        mainCourse.setTitle("Let us learn English, let's enjoy english class all!");
        mainCourse.setDate("2019-09-01");
        mainCourse.setImage("https://image.baidu.com/search/detail?ct=503316480&z=&tn=baiduimagedetail&ipn=d&word=%E8%8B%B1%E8%AF%AD%E8%AF%BE%E7%A8%8B&step_word=&ie=utf-8&in=&cl=2&lm=-1&st=-1&hd=&latest=&copyright=&cs=1124077391,3373820022&os=4193188098,1315254487&simid=3425353380,251997166&pn=1&rn=1&di=163900&ln=1200&fr=&fmq=1570873291981_R&ic=&s=undefined&se=&sme=&tab=0&width=&height=&face=undefined&is=0,0&istype=2&ist=&jit=&bdtype=0&spn=0&pi=0&gsm=0&objurl=http%3A%2F%2Fku.90sjimg.com%2Felement_origin_min_pic%2F17%2F01%2F14%2Fdafd64c0b6c0fcfea1b063a8d55abb6c.jpg%2521%2Ffwfh%2F804x976%2Fquality%2F90%2Funsharp%2Ftrue%2Fcompress%2Ftrue&rpstart=0&rpnum=0&adpicid=0&force=undefined");
        mainCourse.setTeacher("liu");
        mainCourses.add(mainCourse);
        mainCourses.add(mainCourse);
        mainCourses.add(mainCourse);
        mainCourses.add(mainCourse);
        mainCourses.add(mainCourse);
        mAdapter.addList(mainCourses);
        lv.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
//        lv.addItemDecoration(dividerItemDecoration);
        lv.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new CourseItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), CourseContentActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        topLv = root.findViewById(R.id.kanner);
        container = root.findViewById(R.id.container);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        topLv.setLayoutManager(layoutManager);
        ll_dot = (LinearLayout) root.findViewById(R.id.ll_dot);
        iv_dots = new ArrayList<ImageView>();
        topLv.addOnScrollListener(new RecyclerView.OnScrollListener(){
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                currentTopItem = layoutManager.findFirstVisibleItemPosition();
                updateIndicatorStatus();
            }
        });
//        kanner = root.findViewById(R.id.kanner);
        lv = root.findViewById(R.id.rv_courses);
        new PagerSnapHelper().attachToRecyclerView(topLv);
        return root;
    }

    public void updateIndicatorStatus(){
        for(int i = 0;i < bannerAdapter.getItemCount();i++){
            if(i == currentTopItem){
                iv_dots.get(i).setImageResource(R.mipmap.dot_focus);
            }else {
                iv_dots.get(i).setImageResource(R.mipmap.dot_blur);
            }
        }
    }

    public void resetBanner(){
        int len = bannerAdapter.getItemCount();
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
        updateIndicatorStatus();
    }


    /**
     * Refresh
     */
    @Override
    public void refresh() {
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