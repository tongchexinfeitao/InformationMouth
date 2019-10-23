package com.ali.informationmouth.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ali.informationmouth.R;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //设置布局id
        View inflate = inflater.inflate(layoutId(), container, false);
        //find控件
        initView(inflate);
        return inflate;
    }

    protected abstract void initView(View inflate);

    protected abstract int layoutId();


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    //请求数据
    protected abstract void initData();
}
