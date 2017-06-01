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
public class SearchResultDTO implements Serializable {
    
    private String name;
    private String keyword;
    private String location;
    private Long up_id;

    public SearchResultDTO() {
    }

    public SearchResultDTO(Long up_id, String name, String keyword, String location) {
        this.name = name;
        this.keyword = keyword;
        this.location = location;
        this.up_id = up_id;
    }
    
    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Long getUp_id() {
        return up_id;
    }

    public void setUp_id(Long up_id) {
        this.up_id = up_id;
    }

    
    
    
}
