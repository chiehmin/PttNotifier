package com.fatminmin.pttnotifier.model;

import com.fatminmin.pttnotifier.Common;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;


/**
 * Created by fatminmin on 6/28/16.
 */
public class Post {
    String mUrl;
    String mTitle;

    Document mDoc;

    String mText;
    String mArticle;
    String mPush;

    public Post(String url, String title) {
        mUrl = Common.host + url;
        mTitle = title;
        prepare();
    }

    public String getUrl() {
        return mUrl;
    }
    public String getTitle() {
        return mTitle;
    }

    public boolean titleContains(String keyword) {
        return mTitle.toLowerCase().contains(keyword.toLowerCase());
    }
    public boolean articleContains(String keyword) {
        return mArticle.contains(keyword);
    }
    public boolean pushContains(String keyword) {
        return mPush.contains(keyword);
    }
    public boolean textContains(String keyword) {
        return mText.contains(keyword);
    }

    public boolean isPrepared() {
        return mText != null && mDoc != null;
    }

    private void prepare() {
        try {
            mDoc = Jsoup.connect(mUrl).get();
            Element content = mDoc.getElementById("main-content");
            mText = content.text();
            mPush = content.getElementsByClass("push").text();
            content.getElementsByClass("article-metaline").remove();
            content.getElementsByClass("article-metaline-right").remove();
            content.getElementsByClass("push").remove();
            mArticle = content.text();


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Post("/bbs/BuyTogether/M.1464712367.A.FF5.html", "[無主] Sigma刷具七折免運團-郵寄");
    }
}
