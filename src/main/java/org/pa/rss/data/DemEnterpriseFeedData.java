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
public class DemEnterpriseFeedData implements FeedDataInterface {
     private static DemEnterpriseFeedData instance;
     private NodeList nodeList;
     
     private DemEnterpriseFeedData() {   
       this.nodeList = new NodeList();     
    }
    
    public static DemEnterpriseFeedData getInstance() {
        if (instance == null) {
            instance = new DemEnterpriseFeedData();
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
