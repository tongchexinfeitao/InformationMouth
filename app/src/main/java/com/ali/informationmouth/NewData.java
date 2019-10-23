package com.ali.informationmouth;

import com.stx.xhb.xbanner.entity.SimpleBannerInfo;

import java.util.List;

public class NewData {

    /**
     * reason : 成功的返回
     * result : {"banner":[{"image":"http://ku.90sjimg.com/back_pic/04/11/04/7958194fe717a1f.jpg"},{"image":"http://pic.58pic.com/58pic/17/32/64/42g58PICwf8_1024.jpg"},{"image":"http://pic93.nipic.com/file/20160330/5303433_135136175250_2.jpg"}],"news":[{"title":"正确归因，内方外圆","date":"2019-09-24","type":1,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/001.png"},{"title":"潜力无限，感恩奉献","date":"2019-09-24","type":2,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/002.jpg"},{"title":"八维校区，美丽校园","date":"2019-09-24","type":1,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/003.jpg"},{"title":"正确归因，内方外圆","date":"2019-09-24","type":1,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/001.png"},{"title":"潜力无限，感恩奉献","date":"2019-09-24","type":2,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/002.jpg"},{"title":"八维校区，美丽校园","date":"2019-09-24","type":1,"url":"http://www.baway.org.cn/news/20190719/148.html","thumbnail_pic_s":"http://blog.zhaoliang5156.cn/api/images/003.jpg"}]}
     * error_code : 0
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        private List<BannerBean> banner;
        private List<NewsBean> news;

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public List<NewsBean> getNews() {
            return news;
        }

        public void setNews(List<NewsBean> news) {
            this.news = news;
        }

        public static class BannerBean extends SimpleBannerInfo {
            /**
             * image : http://ku.90sjimg.com/back_pic/04/11/04/7958194fe717a1f.jpg
             */

            private String image;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            @Override
            public Object getXBannerUrl() {
                return null;
            }
        }

        public static class NewsBean {
            /**
             * title : 正确归因，内方外圆
             * date : 2019-09-24
             * type : 1
             * url : http://www.baway.org.cn/news/20190719/148.html
             * thumbnail_pic_s : http://blog.zhaoliang5156.cn/api/images/001.png
             */

            private String title;
            private String date;
            private int type;
            private String url;
            private String thumbnail_pic_s;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getType() {
                return type;
            }

            public void setType(int type) {
                this.type = type;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getThumbnail_pic_s() {
                return thumbnail_pic_s;
            }

            public void setThumbnail_pic_s(String thumbnail_pic_s) {
                this.thumbnail_pic_s = thumbnail_pic_s;
            }
        }
    }
}
