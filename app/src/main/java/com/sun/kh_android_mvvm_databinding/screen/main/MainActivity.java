package com.sun.kh_android_mvvm_databinding.screen.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.sun.kh_android_mvvm_databinding.R;
import com.sun.kh_android_mvvm_databinding.screen.favorite.FavoriteFragment;
import com.sun.kh_android_mvvm_databinding.screen.home.HomeFragment;
import com.sun.kh_android_mvvm_databinding.screen.setting.SettingFragment;

public class MainActivity extends AppCompatActivity implements
        ViewPager.OnPageChangeListener, BottomNavigationView.OnNavigationItemSelectedListener,
        FavoriteFragment.RefreshHomeData, HomeFragment.NeedToRefreshListener {
    private static final int HOME_INDEX = 0;
    private static final int FAVORITE_INDEX = 1;
    private static final int SETTING_INDEX = 2;
    private ViewPager mViewpager;
    private BottomNavigationView mBottomNavigation;

    public static Intent newInstance(Context context) {
        return new Intent(context, MainActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mViewpager = findViewById(R.id.viewpager);
        mBottomNavigation = findViewById(R.id.navigation);
        mViewpager.addOnPageChangeListener(this);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = HomeFragment.newInstance();
        homeFragment.setListener(this);
        adapter.addFragment(homeFragment);
        FavoriteFragment favoriteFragment = FavoriteFragment.newInstance();
        favoriteFragment.setListener(this);
        adapter.addFragment(favoriteFragment);
        SettingFragment settingFragment = SettingFragment.newInstance();
        adapter.addFragment(settingFragment);
        mViewpager.setAdapter(adapter);
        mBottomNavigation.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.tab_home:
                mViewpager.setCurrentItem(HOME_INDEX);
                break;
            case R.id.tab_favorite:
                mViewpager.setCurrentItem(FAVORITE_INDEX);
                break;
            case R.id.tab_setting:
                mViewpager.setCurrentItem(SETTING_INDEX);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {
        mBottomNavigation.getMenu().getItem(i).setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    private void refreshFavoriteData() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) mViewpager.getAdapter();
        FavoriteFragment favoriteFragment = (FavoriteFragment) adapter.getItem(FAVORITE_INDEX);
        favoriteFragment.refreshMovies();
    }

    @Override
    public void refreshFavoriteFragment() {
        refreshFavoriteData();
    }

    @Override
    public void onRefreshHomeFragment() {
        ViewPagerAdapter adapter = (ViewPagerAdapter) mViewpager.getAdapter();
        HomeFragment homeFragment = (HomeFragment) adapter.getItem(HOME_INDEX);
        homeFragment.updateFavoriteMovie();
    }
}
