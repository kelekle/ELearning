package com.star.e_learning.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.star.e_learning.R;
import com.star.e_learning.adapter.SyllabusAdapter;

import java.util.ArrayList;
import java.util.List;

public class SyllabusActivity extends AppCompatActivity {

    private ExpandableListView expandableListView;
    private TextView title;
    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllabus);
        title = findViewById(R.id.txt_title);
        title.setText("Course Syllabus");
        back = findViewById(R.id.img_back);
        back.setVisibility(View.VISIBLE);
        expandableListView = findViewById(R.id.chapter1);
        List<String> groupList = new ArrayList<>();
        groupList.add("Chapter 1");
        groupList.add("Chapter 2");
        groupList.add("Chapter 3");
        List<List<String>> childList = new ArrayList<>();
        List<String> childList1 = new ArrayList<>();
        childList1.add("About OS");
        childList1.add("Process");
        childList1.add("Thread");
        List<String> childList2 = new ArrayList<>();
        childList2.add("About OS");
        childList2.add("Process");
        childList2.add("Thread");
        List<String> childList3 = new ArrayList<>();
        childList3.add("About OS");
        childList3.add("Process");
        childList3.add("Thread");
        childList.add(childList1);
        childList.add(childList2);
        childList.add(childList3);
        SyllabusAdapter demoAdapter = new SyllabusAdapter(groupList, childList);
        expandableListView.setAdapter(demoAdapter);
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
           //一级点击监听
             @Override
             public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                 //如果你处理了并且消费了点击返回true,这是一个基本的防止onTouch事件向下或者向上传递的返回机制
                  return false;
             }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            //二级点击监听
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                //如果你处理了并且消费了点击返回true
                return false;
            }
        });
    }


}
