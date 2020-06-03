package com.example.taobaounion.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.taobaounion.R;
import com.example.taobaounion.base.BaseFragment;
import com.example.taobaounion.ui.fragment.HomeFragment;
import com.example.taobaounion.ui.fragment.RedPacketFragment;
import com.example.taobaounion.ui.fragment.SearchFragment;
import com.example.taobaounion.ui.fragment.SelectedFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private FrameLayout myframelayout;
    private BottomNavigationView navigation;
    private String TAG = "MainActivity";
    private RedPacketFragment redPacketFragment;
    private SearchFragment searchFragment;
    private SelectedFragment selectedFragment;
    private HomeFragment homeFragment;
    private FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragment();
        initView();
        initListener();
    }

    private void initFragment() {
        homeFragment = new HomeFragment();
        selectedFragment = new SelectedFragment();
        searchFragment = new SearchFragment();
        redPacketFragment = new RedPacketFragment();
        fm = getSupportFragmentManager();
        switchFragment(homeFragment);
    }

    private void initListener() {
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Log.d(TAG, "onNavigationItemSelected: " + item.getTitle() + "id--->" + item.getItemId());
                switch (item.getItemId()) {
                    case R.id.home:
                        switchFragment(homeFragment);
                        break;
                    case R.id.select:
                        switchFragment(selectedFragment);
                        break;
                    case R.id.search:
                        switchFragment(searchFragment);
                        break;
                    case R.id.red_packet:
                        switchFragment(redPacketFragment);
                        break;
                }
                return true;
            }

        });
    }

    private void switchFragment(BaseFragment baseFragment) {
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.myframelayout,baseFragment);
        transaction.commit();

    }

    private void initView() {
        myframelayout = (FrameLayout) findViewById(R.id.myframelayout);
        navigation = (BottomNavigationView) findViewById(R.id.navigation);
    }
}
