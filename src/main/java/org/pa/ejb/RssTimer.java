/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.ejb;

import javax.ejb.Asynchronous;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.pa.rss.FeedData;
import org.pa.rss.FeedTypes;
import org.pa.rss.RemoteFeed;

/**
 *
 * @author mwave
 */
@Singleton
public class RssTimer {

    @Asynchronous
    @Schedule(hour="*/12", minute="0", second="0", persistent=false)
    public void democracyAtWorkRssTimer() {
        RemoteFeed rf = new RemoteFeed(FeedTypes.DEM_AT_WORK_RSS_URL);
        FeedData.getInstance().setDemocracyAtWorkRss(rf.getData());
    }
    
    @Asynchronous
    @Schedule(hour="*/12", minute="0", second="0", persistent=false)
    public void usFedWorkerCoopRssTimer() {
        RemoteFeed rf = new RemoteFeed(FeedTypes.US_FED_COOP_RSS_URL);
        FeedData.getInstance().setUsFedRss(rf.getData());
    }
    
    @Asynchronous
    @Schedule(hour="*/12", minute="0", second="0", persistent=false)
    public void demEnterpriseRssTimer() {
        RemoteFeed rf = new RemoteFeed(FeedTypes.DEMOC_ENTERPISE_RSS_URL);
        FeedData.getInstance().setDemTheEnterpriseRss(rf.getData());
    }
  
    @Asynchronous
    @Schedule(hour="*/12", minute="0", second="0", persistent=false)
    public void redditRssTimer() {
        RemoteFeed rf = new RemoteFeed(FeedTypes.REDDIT_RSS_URL, FeedTypes.ATOM);
        FeedData.getInstance().setRedditRss(rf.getData());
    }
    
    @Asynchronous
    @Schedule(hour="*/12", minute="0", second="0", persistent=false)
    public void googleRssTimer() {
        FeedData.getInstance().setCounter();
        RemoteFeed rf = new RemoteFeed(FeedTypes.GOOGLE_RSS_URL);
        FeedData.getInstance().setGoogleRss(rf.getData());
    }
}
