package com.example.administrator.newkj.bean.bean;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/2/15.
 */

public class Pic {


    Body showapi_res_body;

    public Body getShowapi_res_body() {
        return showapi_res_body;
    }

    public void setShowapi_res_body(Body showapi_res_body) {
        this.showapi_res_body = showapi_res_body;
    }

    public static class Body{
        ArrayList<MyPic> newslist;

        public ArrayList<MyPic> getNewslist() {
            return newslist;
        }

        public void setNewslist(ArrayList<MyPic> newslist) {
            this.newslist = newslist;
        }

        public static class MyPic{
            String title;
            String picUrl;

            public String getPicUrl() {
                return picUrl;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public void setPicUrl(String picUrl) {
                this.picUrl = picUrl;
            }
        }

    }

}
