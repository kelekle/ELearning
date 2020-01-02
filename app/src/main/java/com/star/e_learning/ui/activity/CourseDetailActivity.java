package com.star.e_learning.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.star.e_learning.R;
import com.star.e_learning.ui.adapter.*;
import com.star.e_learning.ui.fragment.BaseFragment;
import com.star.e_learning.ui.fragment.MaterialFragment;
import com.star.e_learning.ui.fragment.TeacherFragment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CourseDetailActivity extends AppCompatActivity {

    List<BaseFragment> fragmentList;
    private String cid;
    private Toolbar toolbar;
    private ImageView backcrop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_detail);
        toolbar = findViewById(R.id.htab_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getIntent().getStringExtra("title"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ViewPager viewPager = findViewById(R.id.view_pager);
        TabLayout tabs = findViewById(R.id.tabs);
        cid = getIntent().getStringExtra("cid");
        fragmentList = new ArrayList<BaseFragment>();
        fragmentList.add(new TeacherFragment());
        fragmentList.add(new MaterialFragment());
        tabs.setupWithViewPager(viewPager, false);
        CourseDetailTabAdapter pagerAdapter = new CourseDetailTabAdapter(getSupportFragmentManager(), fragmentList);
        viewPager.setAdapter(pagerAdapter);
        tabs.getTabAt(0).setText("Teacher");
        tabs.getTabAt(1).setText("Material");
//        tabs.setTabTextColors(R.color.colorTextTitle, R.color.title_bar_search_color);
        backcrop = findViewById(R.id.backdrop);
        backcrop.setImageResource(R.mipmap.math_course);
        if(getIntent().getStringExtra("ishot").contains("yes")){
            File file = new File(
                    getExternalFilesDir(null) + "/hotcourses/" + getIntent().getStringExtra("avatar").replace("\\", "/"));
            System.out.println(file.getAbsolutePath());
            Glide.with(CourseDetailActivity.this).load(Uri.fromFile(file)).into(backcrop);
        }else {
            File file = new File(
                    getExternalFilesDir(null) + "/courses/" + getIntent().getStringExtra("avatar").replace("\\", "/"));
            System.out.println(file.getAbsolutePath());
            Glide.with(CourseDetailActivity.this).load(Uri.fromFile(file)).into(backcrop);
        }
    }

    public String getCourseID(){
        return cid;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.share){
            try{
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plan");
                i.putExtra(Intent.EXTRA_SUBJECT, getIntent().getStringExtra("title"));
                String body = getIntent().getStringExtra("description") + "\n" + "Share from the E-Learning App" + "\n";
                i.putExtra(Intent.EXTRA_TEXT, body);
                startActivity(Intent.createChooser(i, "Share with :"));
            }catch (Exception e){
                Toast.makeText(this, "Hmm.. Sorry, \nCannot be share", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }
}