package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.star.e_learning.R;
import com.star.e_learning.bean.Course;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

public class CourseContentActivity extends AppCompatActivity implements View.OnClickListener {

    private Button follow;
    private Button syllabus;

    private JCVideoPlayerStandard jcVideoPlayerStandard;
    String url = "http://txycdn.miaopai.com/stream/ed5HCfnhovu3tyIQAiv60Q__.mp4?ssig=8c0c63d0e5f73ba30866ce78758f27ac&time_stamp=1570965192987";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_content);
        jcVideoPlayerStandard = findViewById(R.id.player_list_video);
        jcVideoPlayerStandard.setUp(url, jcVideoPlayerStandard.SCREEN_LAYOUT_NORMAL,"学英语");
        syllabus = findViewById(R.id.syllabus_btn);
        follow = findViewById(R.id.follow_btn);
        syllabus.setOnClickListener(this);
        follow.setOnClickListener(this);
    }


    @Override
    public void onBackPressed() {
//        "全屏竖屏切换的时候继续播放"
        if (JCVideoPlayerStandard.backPress()){
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        JCVideoPlayerStandard.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
//        "activity销毁的时候释放资源，播放器停止播放"
        super.onDestroy();
        try {
            jcVideoPlayerStandard.releaseAllVideos();
        } catch (Exception e) {
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.follow_btn:
                break;
            case R.id.syllabus_btn:
                Intent intent = new Intent(CourseContentActivity.this, SyllabusActivity.class);
                startActivity(intent);
                break;
        }
    }
}
