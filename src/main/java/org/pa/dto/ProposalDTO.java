/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mwave
 */
public class ProposalDTO implements Serializable {
    
    private Long id;
    private String title;
    private String message;
    private String createdAt;
    private boolean is_private;
    private List<Long> partners;
    
   protected final SimpleDateFormat date_format = new SimpleDateFormat("MM-dd-yy");

    public ProposalDTO() {
    }

    public ProposalDTO(Long id, String message, String createdAt) {
        this.id = id;
        this.message = message;
        this.createdAt = createdAt;
    }
    
     public ProposalDTO(Long id, String title, String message, Date date) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = date_format.format(date);
        
    }
    
    public ProposalDTO(Long id, String title, String message, Date date, boolean is_private) {
        this.id = id;
        this.title = title;
        this.message = message;
        this.createdAt = date_format.format(date);
        this.is_private = is_private;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public List<Long> getPartners() {
        return partners;
    }

    public void setPartners(List<Long> partners) {
        this.partners = partners;
    }

    public boolean isIs_private() {
        return is_private;
    }

    public void setIs_private(boolean is_private) {
        this.is_private = is_private;
    }
    
    
    
}
