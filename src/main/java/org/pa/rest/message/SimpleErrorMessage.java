/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.rest.message;

import java.io.Serializable;

/**
 *
 * @author mwave
 */
public class SimpleErrorMessage implements Serializable {
    
   
    private String error;
    
    public SimpleErrorMessage() {
    }

    public SimpleErrorMessage(String message) {
        
        this.error = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    
    

   
    
    
    
    
}
