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

    String mHtml;
    String article;

    public Post(String url, String title) {
        mUrl = Common.host + url;
        mTitle = title;
        preparePost();
    }

    public boolean isPrepared() {
        return mHtml != null && mDoc != null;
    }

    private void preparePost() {
        try {
            mDoc = Jsoup.connect(mUrl).get();
            Element content = mDoc.getElementById("main-content");
            mHtml = content.html();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Post("/bbs/BuyTogether/M.1467098058.A.382.html", "[無主] Sigma刷具七折免運團-郵寄");
    }
}
