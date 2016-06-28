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
    public List<Post> parseBoard(String url) {
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
        new Crawler().parseBoard(Common.getBoardUrl("BuyTogether"));
    }
}
