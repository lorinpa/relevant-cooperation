/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss;

import java.util.concurrent.Callable;
import org.pa.rss.data.FeedDataInterface;


/**
 *
 * @author mwave
 */
public class FeedTask implements Callable<FeedDataInterface>{
    
    private final String url;
    int feedType = FeedTypes.RSS;
    FeedDataInterface fd;

    public FeedTask(FeedDataInterface fd ,String url) {
        this.fd = fd;
        this.url = url;
    }
    
     public FeedTask(FeedDataInterface fd, String url, int feedType) {
        this.fd = fd;
        this.url = url;
        this.feedType = feedType;
    }
    

    @Override
    public FeedDataInterface call() throws Exception {
        RemoteFeed rf = new RemoteFeed(this.url, this.feedType);
        this.fd.setNodeList(rf.getData());
        return this.fd;
    }
     
    
}
