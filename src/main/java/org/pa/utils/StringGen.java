/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.utils;

import java.util.Random;

/**
 *
 * @author mwave
 */
public class StringGen {
    private static StringGen instance = null;
    Random random;
    
    private StringGen() {
       random = new Random();
    }
    
    public static StringGen getInstance() {
        if (instance == null) {
            instance = new StringGen();
        }
        return instance;
    }
    
    /*
    reference: http://www.rationaljava.com/2015/06/java8-generate-random-string-in-one-line.html
    */
    public String generate(int length) {
          return random.ints(48,122)
                .filter(i-> (i<57 || i>65) && (i <90 || i>97))
                .mapToObj(i -> (char) i)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
                .toString();
    }
    
    
}
