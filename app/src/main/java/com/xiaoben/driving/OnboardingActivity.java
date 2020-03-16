package com.xiaoben.driving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class OnboardingActivity extends AppCompatActivity {

    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_onboarding);

        viewPager = findViewById(R.id.viewpager);
        OnBoardingPageAdapter onBoardingPageAdapter = new OnBoardingPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(onBoardingPageAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    //switch viewpage to #page
    public void setCurrentPage(int page) {
        viewPager.setCurrentItem(page);
    }



}
