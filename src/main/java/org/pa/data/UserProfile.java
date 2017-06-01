/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author mwave
 */
@Entity
@Table(name = "USER_PROFILE")
@JsonIgnoreProperties({"servicesProvided", "partnerServices", "businessConcepts"})
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "aliasName",  length = 128, nullable = false)
    private String name;
    
    @Column(name = "location",  length = 128, nullable = false)
    private String location;

   
    @OneToMany(mappedBy = "userProfile")
    private Collection<UserProvidedService> servicesProvided;
    
   
    @OneToMany(mappedBy = "userProfile")
    private Collection<PartnerService> partnerServices;
  

    @OneToMany(mappedBy = "userProfile")
    private Collection<BusinessConcept> businessConcepts;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "USER_ID")
    private User user;


    public UserProfile() {
    }

    public UserProfile(String name, String location) {
        this.name = name;
        this.location = location;
    }
    
    

    
    public UserProfile(Long id, Collection<UserProvidedService> servicesProvided, User user) {
        this.id = id;
        this.servicesProvided = servicesProvided;
        this.user = user;
    }
    
   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    
    public Collection<UserProvidedService> getServicesProvided() {
        return servicesProvided;
    }

    public void setServicesProvided(Collection<UserProvidedService> servicesProvided) {
        this.servicesProvided = servicesProvided;
    }

    public Collection<PartnerService> getPartnerServices() {
        return partnerServices;
    }

    public void setPartnerServices(Collection<PartnerService> partnerServices) {
        this.partnerServices = partnerServices;
    }

    public Collection<BusinessConcept> getBusinessConcepts() {
        return businessConcepts;
    }

    public void setBusinessConcepts(Collection<BusinessConcept> businessConcepts) {
        this.businessConcepts = businessConcepts;
    }
   
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
        if (!(object instanceof UserProfile)) {
            return false;
        }
        UserProfile other = (UserProfile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.pa.data.UserProfile[ id=" + id + " ]";
    }
    
}
