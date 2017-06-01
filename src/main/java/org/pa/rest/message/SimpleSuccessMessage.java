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
public class SimpleSuccessMessage  implements Serializable {
    
    private String success;
    
    public SimpleSuccessMessage() {}

    public SimpleSuccessMessage(String success) {
        this.success = success;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }
    
    
    
}
