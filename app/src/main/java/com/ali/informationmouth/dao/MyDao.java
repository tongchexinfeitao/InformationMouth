package com.ali.informationmouth.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.ali.informationmouth.NewData;

import java.util.ArrayList;
import java.util.List;

public class MyDao {

    private final MyHelper myHelper;

    public MyDao(Context context) {
        myHelper = new MyHelper(context, "mouth.db", null, 1);
    }

    public void insertList(List<NewData.ResultBean.NewsBean> newList) {
        //拿到数据库
        SQLiteDatabase writableDatabase = myHelper.getWritableDatabase();
        for (int i = 0; i < newList.size(); i++) {

            NewData.ResultBean.NewsBean newsBean = newList.get(i);

            ContentValues contentValues = new ContentValues();

            contentValues.put("title", newsBean.getTitle());
            contentValues.put("data", newsBean.getDate());
            contentValues.put("url", newsBean.getUrl());

            writableDatabase.insert("news", null, contentValues);
        }

        writableDatabase.close();

    }


    public List<NewData.ResultBean.NewsBean> queryList() {
        SQLiteDatabase writableDatabase = myHelper.getWritableDatabase();
        Cursor cursor = writableDatabase.query("news", null, null, null, null, null, null);

        List<NewData.ResultBean.NewsBean> list = new ArrayList<>();

        while (cursor.moveToNext()) {

            String title = cursor.getString(cursor.getColumnIndex("title"));
            String data = cursor.getString(cursor.getColumnIndex("data"));
            String url = cursor.getString(cursor.getColumnIndex("url"));

            NewData.ResultBean.NewsBean newsBean = new NewData.ResultBean.NewsBean();

            newsBean.setTitle(title);
            newsBean.setDate(data);
            newsBean.setUrl(url);

            list.add(newsBean);
        }
        writableDatabase.close();
        return list;
    }
}
