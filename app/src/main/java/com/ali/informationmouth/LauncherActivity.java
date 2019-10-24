package com.ali.informationmouth;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ali.informationmouth.base.BaseActivity;

public class LauncherActivity extends BaseActivity {


    @Override
    protected void initData() {
        Handler handler = new Handler();
        //postDelayed   延迟执行
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(LauncherActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }, 2000);
    }

    @Override
    protected void initView() {

    }

    @Override
    protected int layoutId() {
        return R.layout.activity_launcher;
    }
}
