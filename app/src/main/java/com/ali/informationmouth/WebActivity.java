package com.ali.informationmouth;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.ali.informationmouth.base.BaseActivity;

public class WebActivity extends BaseActivity {


    private WebView webView;

    @Override
    protected void initData() {
        //获取url
        final String key = getIntent().getStringExtra("key");
        webView.loadUrl(key);
        //接受通知  页面开始加载 页面结束加载  页面应用内加载
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                //应用内加载页面
                view.loadUrl(key);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.e("TAG", "页面开始加载");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.e("TAG", "页面加载完成");
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                Log.e("TAG", "页面加载进度为" + newProgress + "%");
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.e("TAG", "当前页面的标题为:" + title);
            }
        });
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.web);
        //这是webview的一个辅助类
//        WebSettings settings = webView.getSettings();
//        settings.setBuiltInZoomControls(true);
//        settings.setDisplayZoomControls(false);
        //支持js和android交互
//        settings.setJavaScriptEnabled(true);
    }

    @Override
    protected int layoutId() {
        return R.layout.activity_web;
    }
}
