package com.ali.informationmouth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.TextView;

import com.ali.informationmouth.base.BaseFragment;

public class OtherFragment extends BaseFragment {

    private TextView textView;

    @Override
    protected void initView(View inflate) {
        textView = inflate.findViewById(R.id.tv);
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_other_layout;
    }

    @Override
    protected void initData() {
        Bundle arguments = getArguments();
        String key = arguments.getString("key");
        textView.setText(key);
    }
}
