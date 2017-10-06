/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.swing.plaf.basic.BasicInternalFrameTitlePane;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mwave
 */
public class RemoteFeed {
    
    private static final String SAX_NAMESPACE_PREFIXES = "http://xml.org/sax/features/namespace-prefixes";
    private static final String SAX_NAMESPACES = "http://xml.org/sax/features/namespaces";
    private final String USER_AGENT = "Mozilla/5.0";
    
    private String urlString;
    int feedType = FeedTypes.RSS;

    public RemoteFeed() {
    }

    public RemoteFeed(String urlString) {
        this.urlString = urlString;
    }
    
    public RemoteFeed(String urlString, int feedType) {
        this.urlString = urlString;
        this.feedType = feedType;
    }
    
    
    public NodeList getData() {
        URL urlObj; 
        HttpURLConnection con;
        StringBuffer response;
        NodeList nodeList;
        BufferedReader in = null;
        try {
                urlObj = new URL(this.urlString);
                con = (HttpURLConnection) urlObj.openConnection();  
                con.setRequestMethod("GET");
                con.setRequestProperty("User-Agent", USER_AGENT);
                con.setRequestProperty("Accept", "application/xml");
                int responseCode = con.getResponseCode();
                
		//System.out.println("\nSending 'GET' request to URL : " + urlString);
		//System.out.println("Response Code : " + responseCode);
                in = new BufferedReader(
		        new InputStreamReader(con.getInputStream(),"UTF-8"));
                
                InputSource is = new InputSource(in);
                is.setEncoding("UTF-8");
                nodeList = parseObject(is);
		//in.close();
                
               
        } catch (Exception e) {
             System.err.println(e);
             nodeList = new NodeList();
        } finally {
            try {
                in.close();
            } catch(IOException ioe) {
                
            } catch(Exception une) {
                
            }
        }
        return nodeList;
    }
    
  /**
    * Factory method with instantiates and returns a SAX xml parser instance.
    * 
    * @return An instance of an SAX xml parser.
    * @throws ParserConfigurationException  
    * @throws SAXException - Thrown if our RSS XML file is not a valid document.
    */    
    private XMLReader initializeReader() throws ParserConfigurationException, SAXException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        factory.setFeature(SAX_NAMESPACE_PREFIXES, true);
        factory.setFeature(SAX_NAMESPACES, false);
        SAXParser parser = factory.newSAXParser();
        XMLReader xmlreader = parser.getXMLReader();
        return xmlreader;
    }
    
    public NodeList parseObject(InputSource is) {
        NodeList list = null;
        try {
            XMLReader xmlreader = initializeReader();
            switch (this.feedType) {
                case FeedTypes.RSS:
                    NodeListHandler handler = new NodeListHandler();
                    xmlreader.setContentHandler(handler);
                    xmlreader.parse(is);
                    list = handler.get();
                break;
                case FeedTypes.ATOM:
                    AtomHandler atomHandler = new AtomHandler();
                    xmlreader.setContentHandler(atomHandler);
                    xmlreader.parse(is);
                    list = atomHandler.get();
            }
           
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
