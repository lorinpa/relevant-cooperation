/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.apache.shiro.SecurityUtils;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public abstract  class CommonRepository {
    
    /*protected EntityManager entityManager;
    
    
    protected EntityManager getEntityManager() {
        if (!this.entityManager.isOpen()) this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        return this.entityManager;
    }*/
   
    
     @Transactional
    public Long getCurrentSubjectUserProfileId() throws javax.persistence.NoResultException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Long result = null;
        try {
            String subjectName = (String) SecurityUtils.getSubject().getPrincipal();
            Query q = em.createQuery("select a.id from UserProfile a where a.user.email = ?1");
            q.setParameter(1, subjectName);
            result = (Long)q.getSingleResult();
        } catch(Exception e) {
            
        } finally {
            em.close();
        }
        
        return result;
    }
    
}
