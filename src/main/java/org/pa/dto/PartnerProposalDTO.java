/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.dto;

import java.util.Date;

/**
 *
 * @author mwave
 */
public class PartnerProposalDTO extends ProposalDTO {
    
    private String ownerName;
    private String ownerEmail;

    public PartnerProposalDTO() {
    }

    public PartnerProposalDTO(Long id, String ownerName, String ownerEmail,  String title, String message, Date createdAt) {
        super(id, title, message, createdAt);
        this.ownerName = ownerName;
        this.ownerEmail = ownerEmail;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }
    
    
    
    
}
