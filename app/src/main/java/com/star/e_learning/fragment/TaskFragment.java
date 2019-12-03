package com.star.e_learning.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.star.e_learning.R;


public class TaskFragment extends BaseFragment {

    private FrameLayout flContainer;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = initView(inflater, container, savedInstanceState);
        return root;
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_task, container, false);
        flContainer = root.findViewById(R.id.container);
        return root;
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