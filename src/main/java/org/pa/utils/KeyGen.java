/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.utils;



/**
 *
 * @author mwave
 */
public class KeyGen {
    
    private  static KeyGen instance = null;
    private Long key;

    private KeyGen() {
        key = new Long(0);
    }
    
    public static KeyGen getInstance() {
        if (instance == null) {
            instance = new KeyGen();
        }
        return instance;
    }
    
    public Long getKey() {
        return this.key++;
    }
    
}
