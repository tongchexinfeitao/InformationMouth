package com.ali.informationmouth;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.ali.informationmouth.base.BaseActivity;
import com.ali.informationmouth.base.BaseFragment;
import com.ali.informationmouth.dao.MyDao;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;
import com.stx.xhb.xbanner.XBanner;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    private XBanner xBanner;
    private PullToRefreshListView pullToRefreshListView;

    //旧数据
    List<NewData.ResultBean.NewsBean> list = new ArrayList<>();

    //分页加载
    private int page = 1;
    private boolean isLoadmore;
    private MyDao myDao;

    @Override
    protected void initView(View inflate) {
        xBanner = inflate.findViewById(R.id.xbanner);
        pullToRefreshListView = inflate.findViewById(R.id.pull);

        //设置listview的点击事件
        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                NewData.ResultBean.NewsBean newsBean = list.get(position);
                //html链接
                String url = newsBean.getUrl();
                //开启新的activity将url传过去
                Intent intent = new Intent(getActivity(), WebActivity.class);
                intent.putExtra("key", url);
                startActivity(intent);
            }
        });

        //支持上下拉
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        //监听用户手势
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新  请求第一页数据
                page = 1;
                isLoadmore = false;
                //请求接口
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多  请求下一页数据
                page++;
                isLoadmore = true;
                //请求接口
                getData();
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_home_layout;
    }

    @Override
    protected void initData() {
        myDao = new MyDao(getActivity());


        //联网前需要判断是否有网
        if (NetUtil.getInstance().hasNet(getActivity())) {
            //有网
            getData();
        } else {
            //无网Toast
            Toast.makeText(getActivity(), "无网", Toast.LENGTH_SHORT).show();

            List<NewData.ResultBean.NewsBean> newsBeanList = myDao.queryList();
            pullToRefreshListView.setAdapter(new MyAdapter(newsBeanList));
        }
    }

    //联网请求获取json
    private void getData() {
        String url = "";
        if (page == 1) {
            url = "http://blog.zhaoliang5156.cn/api/news/news_month_a.json";
        } else if (page == 2) {
            url = "http://blog.zhaoliang5156.cn/api/news/news_month_a1.json";
        } else if (page == 3) {
            url = "http://blog.zhaoliang5156.cn/api/news/news_month_a2.json";
        }
        NetUtil.getInstance().doGet(url, new NetUtil.MyCallback() {
            @Override
            public void onDoGetSuccess(String json) {
                //解析json
                NewData newData = new Gson().fromJson(json, NewData.class);
                NewData.ResultBean result = newData.getResult();

                //拿到了banner集合
                final List<NewData.ResultBean.BannerBean> bannerBeanList = result.getBanner();
                //给banner设置数据  必须继承 SimpleBannerInfo
                xBanner.setBannerData(bannerBeanList);
                xBanner.loadImage(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, Object model, final View view, int position) {
                        //根据position从集合中获取数据
                        NewData.ResultBean.BannerBean bannerBean = bannerBeanList.get(position);
                        //根据数据拿到图片url路径
                        String image = bannerBean.getImage();
                        //根据图片url去请求图片
                        NetUtil.getInstance().doGetPhoto(image, new NetUtil.MyCallback() {
                            @Override
                            public void onDoGetSuccess(String json) {

                            }

                            @Override
                            public void onDoGetPhotoSuccess(Bitmap bitmap) {
                                //把bitmap设置给ImageView
                                ((ImageView) view).setImageBitmap(bitmap);
                            }
                        });
                    }
                });


                //获取列表数据    新数据
                List<NewData.ResultBean.NewsBean> newList = result.getNews();

                //将news集合存到数据库
                myDao.insertList(newList);


                //当前是否是加载更多
                if (isLoadmore) {
                    //隐藏进度条
                    pullToRefreshListView.onRefreshComplete();
                    list.addAll(newList);
                    MyAdapter myAdapter = new MyAdapter(list);
                    pullToRefreshListView.setAdapter(myAdapter);
                } else {
                    //隐藏进度条
                    pullToRefreshListView.onRefreshComplete();
                    //清楚旧数据
                    list.clear();
                    list.addAll(newList);
                    MyAdapter myAdapter = new MyAdapter(list);
                    pullToRefreshListView.setAdapter(myAdapter);
                }

            }

            @Override
            public void onDoGetPhotoSuccess(Bitmap bitmap) {

            }
        });
    }
}
