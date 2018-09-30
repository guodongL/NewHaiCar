package com.example.administrator.myhaicar.main.mine.Account.OrderCash;

import java.util.List;

/**
 * Created by Administrator on 2017/6/21.
 */

public class CommonProblemBean {

    private List<HelplistBean> helplist;

    public List<HelplistBean> getHelplist() {
        return helplist;
    }

    public void setHelplist(List<HelplistBean> helplist) {
        this.helplist = helplist;
    }

    public static class HelplistBean {
        /**
         * news_id : 930
         * news_url : http://apptest.yingqin8.com/app/help/view.php?id=930
         * news_title : 恭喜您！活动报名成功！
         */

        private String news_id;
        private String news_url;
        private String news_title;

        public String getNews_id() {
            return news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
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
    }
}
