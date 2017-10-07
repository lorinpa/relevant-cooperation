/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.ejb;

import javax.ejb.Asynchronous;
import javax.ejb.ConcurrentAccessTimeoutException;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import org.pa.rss.FeedTypes;
import org.pa.rss.RemoteFeed;
import org.pa.rss.data.DemAtWorkFeedData;
import org.pa.rss.data.DemEnterpriseFeedData;
import org.pa.rss.data.GoogleFeedData;
import org.pa.rss.data.RedditFeedData;
import org.pa.rss.data.UsFedCoopFeedData;

/**
 *
 * @author mwave
 */
@Singleton
public class RssTimer {

    @Asynchronous
    @Schedule(hour = "*/12", minute = "0", second = "0", persistent = false)
    public void democracyAtWorkRssTimer() {
        try {
            RemoteFeed rf = new RemoteFeed(FeedTypes.DEM_AT_WORK_RSS_URL);
            DemAtWorkFeedData.getInstance().setNodeList(rf.getData());
        } catch (ConcurrentAccessTimeoutException cae) {

        } catch (Exception une) {

        }
    }

    @Asynchronous
    @Schedule(hour = "*/12", minute = "0", second = "0", persistent = false)
    public void usFedWorkerCoopRssTimer() {
        try {
            RemoteFeed rf = new RemoteFeed(FeedTypes.US_FED_COOP_RSS_URL);
            UsFedCoopFeedData.getInstance().setNodeList(rf.getData());
        } catch (ConcurrentAccessTimeoutException cae) {

        } catch (Exception une) {

        }
    }

    @Asynchronous
    @Schedule(hour = "*/12", minute = "0", second = "0", persistent = false)
    public void demEnterpriseRssTimer() {
        try {
            RemoteFeed rf = new RemoteFeed(FeedTypes.DEMOC_ENTERPISE_RSS_URL);
            DemEnterpriseFeedData.getInstance().setNodeList(rf.getData());
        } catch (ConcurrentAccessTimeoutException cae) {

        } catch (Exception une) {

        }
    }

    @Asynchronous
    @Schedule(hour = "*/12", minute = "0", second = "0", persistent = false)
    public void redditRssTimer() {
        try {
            RemoteFeed rf = new RemoteFeed(FeedTypes.REDDIT_RSS_URL, FeedTypes.ATOM);
            RedditFeedData.getInstance().setNodeList(rf.getData());
        } catch (ConcurrentAccessTimeoutException cae) {

        } catch (Exception une) {

        }
    }

    @Asynchronous
    @Schedule(hour = "*/12", minute = "0", second = "0", persistent = false)
    public void googleRssTimer() {
        try {
            RemoteFeed rf = new RemoteFeed(FeedTypes.GOOGLE_RSS_URL);
            GoogleFeedData.getInstance().setNodeList(rf.getData());
        } catch (ConcurrentAccessTimeoutException cae) {

        } catch (Exception une) {

        }
    }
}
