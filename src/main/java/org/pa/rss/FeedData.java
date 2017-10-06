/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss;

import java.util.Map;

/**
 *
 * @author mwave
 */
public class FeedData {
    private static FeedData instance;
    private int counter;
    
    private NodeList googleRss;
    private NodeList democracyAtWorkRss;
    private NodeList demTheEnterpriseRss;
    private NodeList redditRss;
    private NodeList usFedRss;
    
    private FeedData() {
       this.counter = 0;
       googleRss = new NodeList();
       democracyAtWorkRss = new NodeList();
    }
    
    public static FeedData getInstance() {
        if (instance == null) {
            instance = new FeedData();
        }
        return instance;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter() {
        this.counter++;
    }

    public NodeList getGoogleRss() {
        return googleRss;
    }

    public void setGoogleRss(NodeList googleRss) {
        this.googleRss = googleRss;
    }

    public NodeList getDemocracyAtWorkRss() {
        return democracyAtWorkRss;
    }

    public void setDemocracyAtWorkRss(NodeList democracyAtWorkRss) {
        this.democracyAtWorkRss = democracyAtWorkRss;
    }

    public NodeList getDemTheEnterpriseRss() {
        return demTheEnterpriseRss;
    }

    public void setDemTheEnterpriseRss(NodeList demTheEnterpriseRss) {
        this.demTheEnterpriseRss = demTheEnterpriseRss;
    }

    public NodeList getRedditRss() {
        return redditRss;
    }

    public void setRedditRss(NodeList redditRss) {
        this.redditRss = redditRss;
    }

    public NodeList getUsFedRss() {
        return usFedRss;
    }

    public void setUsFedRss(NodeList usFedRss) {
        this.usFedRss = usFedRss;
    }
    
    
    
    
}
