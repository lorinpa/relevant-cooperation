/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.dto;

import java.io.Serializable;

/**
 *
 * @author mwave
 */
public class UserProfileDTO implements Serializable{
    
    String name;
    String location;
    String email;

    public UserProfileDTO() {
    }

    public UserProfileDTO(String name, String location) {
        this.name = name;
        this.location = location;
       
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
    
}
