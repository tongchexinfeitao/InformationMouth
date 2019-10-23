package com.ali.informationmouth.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置布局Id
        setContentView(layoutId());
        //find控件
        initView();
        //请求数据
        initData();
    }

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int layoutId();


}
