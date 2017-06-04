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
public class RegistrationDTO implements Serializable {

    private String email;
    private String password;
    private String name;
    private boolean isNewAccount;

    public RegistrationDTO() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIsNewAccount() {
        return isNewAccount;
    }

    public void setIsNewAccount(boolean isNewAccount) {
        this.isNewAccount = isNewAccount;
    }
    
    

}
