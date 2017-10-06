/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

/**
 *
 * @author mwave
 */
public class AtomHandler extends DefaultHandler {
    
  private StringBuilder buffer = new StringBuilder();
  private NodeList list;
  private Node node;
  boolean start = false;
  
  @Override
  public void startElement(String namespaceURI, String localName,
          String qName, Attributes atts) throws SAXException {
      
      if (qName.equals("link")) {
        if (node != null)
           node.setLink(atts.getValue("href"));
      }
      
      buffer.setLength(0);
      if (qName.equals("logo")) {
        list = new NodeList();
        start = true;
      } else if (qName.equals("entry") && start) {
        node = new Node();
      } 
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
   
      if (start) {
        if (qName.equals("title")) {
          if (node != null)
            node.setTitle(buffer.toString());
        /*} else if (qName.equals("link")) {
          if (node != null)
            node.setLink(buffer.toString()); */
       } else if (qName.equals("updated")) {
          if (node != null)
            node.setDate(buffer.toString());
        } else if (qName.equals("entry")) {
          list.add(node);
          node = new Node();
        }
    }
  }

  @Override
  public void characters(char[] ch, int start, int length) {
    buffer.append(ch, start, length);
  }

  public NodeList get() {
    return list;
  }
    
}
