package com.star.e_learning.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.star.e_learning.R;
import com.star.e_learning.fragment.HomeFragment;
import com.star.e_learning.fragment.NotificationsFragment;
import com.star.e_learning.fragment.TaskFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity {

    private Fragment mfragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mfragment = new HomeFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
               mfragment).commit();
//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_comment, R.id.navigation_task)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        if (navController == null){
//            Log.d("tag", "2");
//        }
//        if(appBarConfiguration == null){
//            Log.d("tag", "3");
//        }
//        NavigationUI.setupActionBarWithNavController(HomeActivity.this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);
        navView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fragment = null;
                        Log.d("tag","msg");
                        switch (item.getItemId()) {
                            case R.id.navigation_home:
                                fragment = new HomeFragment();
                                break;
                            case R.id.navigation_comment:
                                fragment = new NotificationsFragment();
                                break;
                            case R.id.navigation_task:
                                fragment = new TaskFragment();
                                break;
                        }
//                        if(!fragment.isAdded()){
//                            getSupportFragmentManager().beginTransaction().hide(mfragment).add(R.id.nav_host_fragment, fragment).show(fragment).commit();
//                        }else {
//                            getSupportFragmentManager().beginTransaction().hide(mfragment).show(fragment).commit();
//                        }
//                        mfragment = fragment;
                        getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment,
                                fragment).commit();
                        return true;
                    }
                });
    }

}
