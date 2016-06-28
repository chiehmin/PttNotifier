package com.fatminmin.pttnotifier;

/**
 * Created by fatminmin on 6/28/16.
 */
public class Common {
    static public final String host = "https://www.ptt.cc";
    static public final String prefix = host + "/bbs/";
    static public String getBoardUrl(String board) {
        return prefix + board + "/index.html";
    }
}
