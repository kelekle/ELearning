package com.star.e_learning.ui.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.star.e_learning.ui.fragment.CourseFragment;
import com.star.e_learning.ui.fragment.MeFragment;

import java.util.ArrayList;

public class MyPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> fragments = new ArrayList<>();
    private Fragment currentFragment;

    public MyPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments.clear();
        fragments.add(new CourseFragment());
//        fragments.add(new NotificationsFragment());
        fragments.add(new MeFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        if (getCurrentFragment() != object) {
            currentFragment = ((Fragment) object);
        }
        super.setPrimaryItem(container, position, object);
    }

    /**
     * Get the current fragment
     */
    public Fragment getCurrentFragment() {
        return currentFragment;
    }
}
