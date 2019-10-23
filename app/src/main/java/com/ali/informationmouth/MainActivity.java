package com.ali.informationmouth;

import android.content.Intent;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ali.informationmouth.base.BaseActivity;
import com.ali.informationmouth.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    private ViewPager viewPager;
    private TabLayout tabLayout;
    //fragment集合
    List<Fragment> fragmentList = new ArrayList<>();
    //title集合
    List<String> titleList = new ArrayList<>();

    @Override
    protected void initData() {
        //1、给fragment集合添加数据
        //首页
        HomeFragment homeFragment = new HomeFragment();
        fragmentList.add(homeFragment);

        //推荐
        OtherFragment otherFragment = new OtherFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", "推荐");
        otherFragment.setArguments(bundle);

        fragmentList.add(otherFragment);

        //我的
        OtherFragment otherFragment1 = new OtherFragment();
        Bundle bundle1 = new Bundle();
        bundle1.putString("key", "我的");
        otherFragment1.setArguments(bundle1);

        fragmentList.add(otherFragment1);


        //2、给title集合添加数据   首页HomeFragment    推荐、我的共用一个OhterFramgent
        titleList.add("首页");
        titleList.add("推荐");
        titleList.add("我的");

        //3、展示viewpager      FragmentPagerAdapter
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragmentList.get(i);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titleList.get(position);
            }
        });

        //4、tab 和 viewpager 关联
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initView() {
        viewPager = findViewById(R.id.vp);
        tabLayout = findViewById(R.id.tlayout);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_main;
    }


}
