/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.data;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author mwave
 */
@Entity
@Table(name = "KEYWORD_VALUES")
public class KeywordValue implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "keyword_value", length = 128, nullable = false)
    private String keyword;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public KeywordValue(String keyword) {
        this.keyword = keyword;
    }

    public KeywordValue() {
    }
    
    
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeywordValue(String keyword) {
        this.keyword = keyword;
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
        if (!(object instanceof KeywordValue)) {
            return false;
        }
        KeywordValue other = (KeywordValue) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        
        if ((this.keyword == null && other.keyword != null) || (this.keyword != null && !this.keyword.equals(other.keyword))) {
            return false;
        }
        
        return true;
    }

    @Override
    public String toString() {
        return "org.pa.data.Keyword[ id=" + id + " keyword="+keyword+ "]";
    }
    
}
