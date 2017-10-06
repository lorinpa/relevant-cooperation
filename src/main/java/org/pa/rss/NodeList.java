/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rss;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author mwave
 */
public class NodeList extends ArrayList<Node> {
 
  @Override
  public boolean add(Node e) {
    return super.add(e);
  }

  /**
   * 
   * @return An iterator object that allows to select one Node at a time.
   */
  @Override
  public Iterator<Node> iterator() {
    return super.iterator();
  }
}
