/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss.data;

import org.pa.rss.NodeList;

/**
 *
 * @author mwave
 * We create a separate singleton class for each feed to avoid lock contention at startup.
 */
public class GoogleFeedData implements FeedDataInterface {
     private static GoogleFeedData instance;
     private NodeList nodeList;
     
     private GoogleFeedData() {   
       this.nodeList = new NodeList();     
    }
    
    public static GoogleFeedData getInstance() {
        if (instance == null) {
            instance = new GoogleFeedData();
        }
        return instance;
    }

    public NodeList getNodeList() {
        return nodeList;
    }

    public void setNodeList(NodeList nodeList) {
        this.nodeList = nodeList;
    }

}
