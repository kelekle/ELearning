package com.star.e_learning.ui.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.star.e_learning.ui.fragment.BaseFragment;

import java.util.List;

public class CourseDetailTabAdapter extends FragmentPagerAdapter {

    List<BaseFragment> fragmentList;

    public CourseDetailTabAdapter(FragmentManager fm, List<BaseFragment> fragmentList) {
        super(fm);
        this.fragmentList=fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

}
