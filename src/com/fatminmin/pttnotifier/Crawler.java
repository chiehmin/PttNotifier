package com.fatminmin.pttnotifier;

import com.fatminmin.pttnotifier.model.Post;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by fatminmin on 6/28/16.
 */
public class Crawler {

    private String mBoard;
    private List<String> keywordsList = new ArrayList<>();

    private long mTimeOut = 10 * 1000;
    private long mRestartTimeOut = 1 * 1000;
    private boolean cont = false;
    private boolean firstRun = false;

    private UIController mUIContronller;

    private Set<String> shown = new HashSet<>();


    private class LoopTask extends Task<Void> {

        @Override
        protected Void call() throws Exception {
            loop();
            return null;
        }
    }

    public Crawler(UIController controller) {
        mBoard = "BuyTogether";
        mUIContronller = controller;
    }

    public Crawler(String board, String keyword) {
        mBoard = board;
        keywordsList.add(keyword);
    }

    public void addKeyword(String keyword) {
        keywordsList.add(keyword);
    }
    public void setBoard(String board) {
        mBoard = board;
    }

    public void loop() {
        try {
            while(cont) {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                log("[Parsing start] " + sdf.format(new Date()));
                log("Starting parsing board...");

                try {
                    checkBoardArticles();
                    log("[Parsing end] " + sdf.format(new Date()));
                    Thread.sleep(mTimeOut);
                } catch(Exception e) {
                    e.printStackTrace();
                    log("Error occurs...restarting...");
                    Thread.sleep(mRestartTimeOut);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public boolean start() {
        if(mBoard == null) return false;
        try {
//            Common.enableSSLSocket();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

        cont = true;
        firstRun = true;
        Thread worker = new Thread(new LoopTask());
        worker.setDaemon(true);
        worker.start();
        return true;
    }
    public void stop() {
        cont = false;
    }
    public boolean isRunning() { return cont; }


    private void log(String msg) {
        Platform.runLater(() -> {
            mUIContronller.log(msg);
        });
    }

    private void notifyPostAppeared(Post post) {
        shown.add(post.getUrl());
        System.out.println(post.getTitle() + " " + post.getUrl());
        Platform.runLater(() -> {
            mUIContronller.notifyPostAppeared(post.getTitle(), post.getUrl());
        });
    }

    private void checkBoardArticles() throws Exception {

        List<Post> posts = new ArrayList<>();

        String boardIndexUrl = Common.getBoardUrl(mBoard);
        Document doc = Common.getDocument(boardIndexUrl);
        posts.addAll(getBoardArticles(doc));

        if(firstRun) {
            firstRun = false;
            Elements btns = doc.select(".btn.wide");
            for(Element btn : btns) {
                if(btn.text().contains("上頁")) {
                    String lastPageUrl = Common.getCompleteUrl(btn.attr("href"));
                    doc = Common.getDocument(lastPageUrl);
                    posts.addAll(getBoardArticles(doc));
                }
            }
        }

        log("Find " + posts.size() + " posts.");

        for(Post post : posts) {
            log(post.getTitle());

            if(shown.contains(post.getUrl())) continue;

            for(String keyword : keywordsList) {
                if(post.titleContains(keyword)) {
                    notifyPostAppeared(post);
                    break;
                }
            }
        }
    }
    private List<Post> getBoardArticles(Document doc) throws Exception {
        List<Post> res = new ArrayList<>();
        Elements posts = doc.select(".title");
        for(Element post : posts) {
            try {
                Element a = post.child(0);
                String link = a.attr("href");
                String title = a.html();
                res.add(new Post(link, title));
            } catch (Exception e) {
                // do nothing
            }
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
