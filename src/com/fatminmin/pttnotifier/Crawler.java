package com.fatminmin.pttnotifier;

import com.fatminmin.pttnotifier.model.Post;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by fatminmin on 6/28/16.
 */
public class Crawler {

    private String mBoard;
    private String mKeywords;

    private long mTimeOut = 10 * 1000;
    private boolean cont = true;

    public Crawler(String board, String keyword) {
        mBoard = board;
        mKeywords = keyword;
    }

    public void loop() {
        try {
            while(cont) {
                checkBoardArticles();
                Thread.sleep(mTimeOut);
            }
        } catch (Exception e) {
            // do nothing
        }
    }
    public void start() {
        cont = true;
        new Thread(() -> {
           loop();
        }).start();
    }
    public void stop() {
        cont = false;
    }


    private void checkBoardArticles() {
        List<Post> posts = getBoardArticles(Common.getBoardUrl(mBoard));
        for(Post post : posts) {
            if(post.titleContains(mKeywords)) {
                System.out.println(post.getTitle() + " " + post.getUrl());
            }
        }
    }
    private List<Post> getBoardArticles(String url) {
        List<Post> res = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(url).get();
            Elements posts = doc.select(".title");
            for(Element post : posts) {
                Element a = post.child(0);
                String link = a.attr("href");
                String title = a.html();
                res.add(new Post(link, title));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void main(String[] args) {
        if(args.length < 1) {
            System.out.println("Usage: java -jar ./PttNotifier.jar <keyword>");
            System.exit(0);
        }
        Crawler crawler = new Crawler("BuyTogether", args[0]);
        crawler.start();
    }
}
