package com.ali.informationmouth;

import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends BaseAdapter {
    private List<NewData.ResultBean.NewsBean> list;


    //两种条目类型   向上 、靠左
    private int xiangshang = 0;
    private int kaozuo = 1;

    public MyAdapter(List<NewData.ResultBean.NewsBean> list) {

        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        NewData.ResultBean.NewsBean newsBean = list.get(position);
        int type = newsBean.getType();
        if (type == 1) {
            return xiangshang;
        } else {
            return kaozuo;
        }
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //条目类型
        int itemViewType = getItemViewType(position);

        ViewHolder viewHolder = null;
        if (convertView == null) {
            if (itemViewType == xiangshang) {
                convertView = View.inflate(parent.getContext(), R.layout.item_xiangshang, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv);
                viewHolder.title = convertView.findViewById(R.id.tv_title);
                viewHolder.data = convertView.findViewById(R.id.tv_data);
                convertView.setTag(viewHolder);
            } else if (itemViewType == kaozuo) {
                convertView = View.inflate(parent.getContext(), R.layout.item_kaozuo, null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = convertView.findViewById(R.id.iv);
                viewHolder.title = convertView.findViewById(R.id.tv_title);
                viewHolder.data = convertView.findViewById(R.id.tv_data);
                convertView.setTag(viewHolder);
            }
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        //获取当前条目的数据
        NewData.ResultBean.NewsBean newsBean = list.get(position);

        //给holder绑定数据
        viewHolder.title.setText(newsBean.getTitle());
        viewHolder.data.setText(newsBean.getDate());

        //图片的地址
        String thumbnail_pic_s = newsBean.getThumbnail_pic_s();

        final ViewHolder finalViewHolder = viewHolder;
        NetUtil.getInstance().doGetPhoto(thumbnail_pic_s, new NetUtil.MyCallback() {
            @Override
            public void onDoGetSuccess(String json) {

            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {
                finalViewHolder.imageView.setImageBitmap(bitmap);
            }
        });

        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView title;
        TextView data;
    }
}
