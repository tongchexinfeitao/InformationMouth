package com.ali.informationmouth;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class NetUtil {
    //单例
    // 1、声明
    static NetUtil netUtil = new NetUtil();

    //2、提供get方法   geti
    public static NetUtil getInstance() {
        return netUtil;
    }

    //3、构造器私有
    private NetUtil() {

    }

    //doGet    根据 url路径 进行联网,请求 json     "http://blog.zhaoliang5156.cn/api/news/lawyer.json"
    @SuppressLint("StaticFieldLeak")
    public void doGet(final String httpUrl, final MyCallback myCallback) {
        //子线程联网 、主线程解析
        //使用Asyncktask类来实现
        new AsyncTask<String, Void, String>() {
            @Override
            protected void onPostExecute(String s) {
                //主线程   使用接口回调，将结果回调给activity
                myCallback.onDoGetSuccess(s);
            }

            @Override
            protected String doInBackground(String... strings) {
                //子线程
                //联网用 URL
                String json = "";
                HttpURLConnection httpURLConnection = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(httpUrl);
                    //获取到连接对象
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    //设置请求方式
                    httpURLConnection.setRequestMethod("GET");
                    //设置超时时间  连接超时、读取超时
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);
                    //开启连接
                    httpURLConnection.connect();
                    //判断是否连接成功   如果响应码是200，表示成功
                    if (httpURLConnection.getResponseCode() == 200) {
                        //成功
                        //成功之后获取流
                        inputStream = httpURLConnection.getInputStream();
                        //流 转换成 json    io2String
                        json = io2String(inputStream);

                    } else {
                        //失败
                        Log.e("TAG", "请求失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭连接
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    //关闭输入流
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //一定要返回
                return json;
            }
        }.execute();
    }

    //io2String  流转json
    public String io2String(InputStream inputStream) {
        //三件套
        byte[] bytes = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        String json = "";
        //边读边写    读是1个参数、写是3个参数    、   记错就末班
        try {
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, len);
            }
            //输出流转成byte数组
            byte[] bytes1 = outputStream.toByteArray();
            //byte数据转成json
            json = new String(bytes1);
        } catch (Exception e) {

        } finally {
            //关闭输出流
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return json;

    }

    //根据 url路径  请求图片，
    @SuppressLint("StaticFieldLeak")
    public void doGetPhoto(final String httpUrl, final MyCallback myCallback) {
        new AsyncTask<String, Void, Bitmap>() {
            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                //一定要回调，不然白写了
                myCallback.onDoGetPhotoSuccess(bitmap);
            }

            @Override
            protected Bitmap doInBackground(String... strings) {
                HttpURLConnection httpURLConnection = null;
                Bitmap bitmap = null;
                InputStream inputStream = null;
                try {
                    URL url = new URL(httpUrl);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setConnectTimeout(5000);
                    httpURLConnection.setReadTimeout(8000);
                    httpURLConnection.connect();
                    if (httpURLConnection.getResponseCode() == 200) {
                        //流转Btimap
                        inputStream = httpURLConnection.getInputStream();
                        bitmap = io2Bitmap(inputStream);
                    } else {
                        //失败打印log
                        Log.e("TAG", "请求图片失败");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //关闭连接
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                    //关闭流
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                return bitmap;
            }
        }.execute();
    }

    //流转Bitmap
    public Bitmap io2Bitmap(InputStream inputStream) {
        return BitmapFactory.decodeStream(inputStream);
    }


    //接口回调     回调json 、回调Bitmap
    public interface MyCallback {
        void onDoGetSuccess(String json);

        void onDoGetPhotoSuccess(Bitmap bitmap);
    }

    public boolean hasNet(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isWifi(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isAvailable() && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else {
            return false;
        }
    }

}
