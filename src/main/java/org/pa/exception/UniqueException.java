/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.exception;

import static org.pa.definitions.RequestDefinitions.NOT_UNIQUE_EXCEPTION;

/**
 *
 * @author mwave
 */
public class UniqueException  extends  Exception {

    public UniqueException() {  
        super(NOT_UNIQUE_EXCEPTION);
    }
    
    
    
}
