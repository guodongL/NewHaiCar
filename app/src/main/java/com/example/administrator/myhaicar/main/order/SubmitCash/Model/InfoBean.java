package com.example.administrator.myhaicar.main.order.SubmitCash.Model;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */

public class InfoBean {

    private List<NewslistinfoBean> newslistinfo;

    public List<NewslistinfoBean> getNewslistinfo() {
        return newslistinfo;
    }

    public void setNewslistinfo(List<NewslistinfoBean> newslistinfo) {
        this.newslistinfo = newslistinfo;
    }

    public static class NewslistinfoBean {
        /**
         * news_id : 921
         * usr_isorno : 0
         * news_url : http://yingqin8.com/appdata/news/view.php?id=921
         * news_title : 恭喜您！获得推荐人红包！
         * news_titles : 嗨！恭喜您获得推荐人红包！请至我的账户-红包里拆红包得现金！多多推荐~召唤红包雨！
         * news_created : 2017-04-12 05:14:00
         */

        private String news_id;
        private String usr_isorno;
        private String news_url;
        private String news_title;
        private String news_titles;
        private String news_created;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getUsr_isorno() {
            return usr_isorno;
        }

        public void setUsr_isorno(String usr_isorno) {
            this.usr_isorno = usr_isorno;
        }

        public String getNews_url() {
            return news_url;
        }

        public void setNews_url(String news_url) {
            this.news_url = news_url;
        }

        public String getNews_title() {
            return news_title;
        }

        public void setNews_title(String news_title) {
            this.news_title = news_title;
        }

        public String getNews_titles() {
            return news_titles;
        }

        public void setNews_titles(String news_titles) {
            this.news_titles = news_titles;
        }

        public String getNews_created() {
            return news_created;
        }

        public void setNews_created(String news_created) {
            this.news_created = news_created;
        }
    }
}
