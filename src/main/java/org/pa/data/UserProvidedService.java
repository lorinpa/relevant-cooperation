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
import javax.persistence.UniqueConstraint;

/**
 *
 * @author mwave
 */
@Entity
@Table(name = "USER_PROVIDED_SERVICES", uniqueConstraints = {@UniqueConstraint(columnNames = {"USER_PROFILE_ID", "USER_PROVIDED_SERVICE_ID"})})
@NamedQueries({
    @NamedQuery(name = "UserProvidedService.findAll", query = "SELECT b FROM UserProvidedService b"),
    @NamedQuery(name = "UserProvidedService.findById", query = "SELECT b FROM UserProvidedService b WHERE b.id = :id"),
    @NamedQuery(name = "UserProvidedService.find",
            query = "SELECT b FROM UserProvidedService b WHERE b.userProfile.id = :userProfileId AND b.userProvidedService.id = :serviceId")

})
public class UserProvidedService implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @JoinColumn(name = "USER_PROFILE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private UserProfile userProfile;

    @JoinColumn(name = "USER_PROVIDED_SERVICE_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private KeywordValue userProvidedService;
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfileId) {
        this.userProfile = userProfileId;
    }

    public KeywordValue getUserProvidedService() {
        return userProvidedService;
    }

    public void setUserProvidedService(KeywordValue userProvidedService) {
        this.userProvidedService = userProvidedService;
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
        if (!(object instanceof UserProvidedService)) {
            return false;
        }
        UserProvidedService other = (UserProvidedService) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "org.pa.data.UserService[ id=" + id + " ]";
    }
    
}
