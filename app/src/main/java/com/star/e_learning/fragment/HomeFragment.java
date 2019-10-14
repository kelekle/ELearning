package com.star.e_learning.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.star.e_learning.R;
import com.star.e_learning.activity.CalendarActivity;
import com.star.e_learning.adapter.CourseItemAdapter;
import com.star.e_learning.bean.Course;
import com.star.e_learning.activity.CourseContentActivity;
import com.star.e_learning.view.Kanner;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private Kanner kanner;
    private ListView lv;
    private ImageView me;
    private ImageView msg;
    private TextView search;
    private CourseItemAdapter mAdapter;
    private DrawerLayout drawerLayout;
    NavigationView navigationView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        drawerLayout = root.findViewById(R.id.drawer_layout);
        navigationView = root.findViewById(R.id.nav_view);
        kanner = root.findViewById(R.id.kanner);
        lv = root.findViewById(R.id.lv_courses);
        me = root.findViewById(R.id.titleBar_me_iv);
        msg = root.findViewById(R.id.titleBar_msg_iv);
        search = root.findViewById(R.id.tv_titleBar_search);
        initData();
        setListener();
        return root;
    }

    public void setListener(){
        msg.setOnClickListener(this);
        me.setOnClickListener(this);
        search.setOnClickListener(this);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.titleBar_me_iv:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.titleBar_msg_iv:
                //message
                Intent intent = new Intent(getContext(), CalendarActivity.class);
                startActivity(intent);
                break;
        }
    }

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
        kanner.setTopEntities(courses);
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
        lv.setAdapter(mAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), CourseContentActivity.class);
                startActivity(intent);
            }
        });
    }

}