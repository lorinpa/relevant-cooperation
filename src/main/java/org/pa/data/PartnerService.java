/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author mwave
 */
@Entity
@Table(name = "PARTNER_SERVICES")
@NamedQueries({
    @NamedQuery(name = "PartnerService.findAll", query = "SELECT b FROM PartnerService b"),
    @NamedQuery(name = "PartnerService.findById", query = "SELECT b FROM PartnerService b WHERE b.id = :id")
})
public class PartnerService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UserProfile userProfile;

    @JoinColumn(name = "PARTNER_SERVICE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private KeywordValue partnerService;

    public PartnerService() {
    }

    public PartnerService(UserProfile userProfile, KeywordValue partnerService) {
        this.userProfile = userProfile;
        this.partnerService = partnerService;
    }
    
   

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    public KeywordValue getPartnerService() {
        return partnerService;
    }

    public void setPartnerService(KeywordValue partnerService) {
        this.partnerService = partnerService;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PartnerService)) {
            return false;
        }
        PartnerService other = (PartnerService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.pa.data.PartnerService[ id=" + id + " ]";
    }
    
}
