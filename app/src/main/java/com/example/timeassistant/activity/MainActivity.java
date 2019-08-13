package com.example.timeassistant.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.timeassistant.R;
import com.example.timeassistant.adapter.MainFragmentPagerAdapter;
import com.example.timeassistant.fragment.DashboardFragment;
import com.example.timeassistant.fragment.DeviceFragment;
import com.example.timeassistant.fragment.MoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindView(R.id.bv_main)
    BottomNavigationView bvMain;

    private Fragment[] mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initPagerAndBottom();
    }


    private void initPagerAndBottom() {
        //添加fragment
        mFragments = new Fragment[]{
                new DeviceFragment(),
                new DashboardFragment(),
                new MoreFragment()
        };

        //设置viewPager的适配器
        MainFragmentPagerAdapter adapter = new MainFragmentPagerAdapter(getSupportFragmentManager(), mFragments);
        vpMain.setAdapter(adapter);
        //设置初始的页面项
        bvMain.setSelectedItemId(R.id.navigation_devices);

        //连接viewpager和bottomnavigation
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //根据viePager的position选择navigaiton的选中项
                switch (position) {
                    case 0:
                        bvMain.setSelectedItemId(R.id.navigation_devices);
                        break;
                    case 1:
                        bvMain.setSelectedItemId(R.id.navigation_dashboard);
                        break;
                    case 2:
                        bvMain.setSelectedItemId(R.id.navigation_more);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        bvMain.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.navigation_devices:
                                vpMain.setCurrentItem(0);
                                return true;
                            case R.id.navigation_dashboard:
                                vpMain.setCurrentItem(1);
                                return true;
                            case R.id.navigation_more:
                                vpMain.setCurrentItem(2);
                                return true;
                        }
                        return false;
                    }
                });
    }

}
