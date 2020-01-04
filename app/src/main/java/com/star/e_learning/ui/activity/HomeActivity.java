package com.star.e_learning.ui.activity;

import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationViewPager;
import com.star.e_learning.PollingReceiver;
import com.star.e_learning.R;
import com.star.e_learning.ui.fragment.BaseFragment;

import com.star.e_learning.ui.adapter.*;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private BaseFragment currentFragment;
    private MyPagerAdapter adapter;
    private AHBottomNavigationViewPager viewPager;
    private AHBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Making notification bar transparent
        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_home);
        initUI();
//        setListener();
//        LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);
//        PollingReceiver mBroadcastReceiver = new PollingReceiver();
//        IntentFilter intentFilter = new IntentFilter();
//        mLocalBroadcastManager.registerReceiver(mBroadcastReceiver, intentFilter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_titleBar_search:
                //search
                break;
        }
    }


    public void initUI(){
//        drawerLayout = findViewById(R.id.drawer_layout);
//        navigationView = findViewById(R.id.nav_view);
//        search = findViewById(R.id.tv_titleBar_search);
        viewPager = findViewById(R.id.view_pager);
        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

// Create items
        AHBottomNavigationItem item1 = new AHBottomNavigationItem(R.string.title_course, R.mipmap.my_course, R.color.color_tab_1);
//        AHBottomNavigationItem item2 = new AHBottomNavigationItem(R.string.title_notifications, R.mipmap.my_notification, R.color.color_tab_2);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(R.string.title_me, R.mipmap.ic_me, R.color.color_tab_3);


// Add items
        bottomNavigation.addItem(item1);
//        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item4);

        bottomNavigation.setTranslucentNavigationEnabled(true);

// Set background color
        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FEFEFE"));

// Set current item programmatically
//        bottomNavigation.setCurrentItem(1);

// Add or remove notification for each item
//        bottomNavigation.setNotification("1", 3);
// OR
//        AHNotification notification = new AHNotification.Builder()
//                .setText("1")
//                .setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.color_notification_back))
//                .setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.color_notification_text))
//                .build();
//        bottomNavigation.setNotification(notification, 1);

// Set listeners
        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                if (currentFragment == null) {
                    currentFragment = (BaseFragment) adapter.getCurrentFragment();
                }

                if (wasSelected) {
                    currentFragment.refresh();
                    return true;
                }

                if (currentFragment != null) {
                    currentFragment.willBeHidden();
                }

                viewPager.setCurrentItem(position, false);

                if (currentFragment == null) {
                    return true;
                }

                currentFragment = (BaseFragment) adapter.getCurrentFragment();
                currentFragment.willBeDisplayed();
                return true;
            }
        });

		/*
		bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
			@Override public void onPositionChange(int y) {
				Log.d("DemoActivity", "BottomNavigation Position: " + y);
			}
		});
		*/

        viewPager.setOffscreenPageLimit(4);
        adapter = new MyPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        currentFragment = (BaseFragment) adapter.getCurrentFragment();

//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                // Setting custom colors for notification
//                AHNotification notification = new AHNotification.Builder()
//                        .setText(":)")
//                        .setBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.color_notification_back))
//                        .setTextColor(ContextCompat.getColor(HomeActivity.this, R.color.color_notification_text))
//                        .build();
//                bottomNavigation.setNotification(notification, 1);
//                Snackbar.make(bottomNavigation, "Snackbar with bottom navigation",
//                        Snackbar.LENGTH_SHORT).show();
//            }
//        }, 3000);

    }

}
