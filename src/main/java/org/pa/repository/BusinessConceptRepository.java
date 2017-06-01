/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import org.pa.data.BusinessConcept;
import org.pa.data.KeywordValue;
import org.pa.data.UserProfile;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class BusinessConceptRepository extends CommonRepository {
    
    private static BusinessConceptRepository instance;
    
    private BusinessConceptRepository() {
        //this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public static BusinessConceptRepository getInstance() {
        if (instance == null) {
            instance = new BusinessConceptRepository();
        }
        return instance;
    }
    

    private BusinessConcept addUerBusConcept(String subjectName,Serializable keyWordValueId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query qu = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        qu.setParameter(1, subjectName);
        UserProfile up = (UserProfile) qu.getSingleResult();
       
        Query qk = em.createQuery("select a from KeywordValue a where a.id = ?1");
        qk.setParameter(1, keyWordValueId);
        KeywordValue kw  = (KeywordValue) qk.getSingleResult();
        
        BusinessConcept s = new BusinessConcept();
        s.setUserProfile(up);
        s.setBusinessConcept(kw);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();
        return s;
    }
    
    // used when the frontend has a new string to add as a keyword and assign it as business concept relationship
    public KeywordValue addUserBusConcept(String subjectName, String keywordValue) {
       // 
        KeywordValue kw = null;
        try {
            kw = KeywordRepository.getInstance().findByValue(keywordValue);
        } catch(NoResultException nre){
            try {
                kw = (KeywordValue) KeywordRepository.getInstance().addNew(new KeywordValue(keywordValue));     
            } catch(Exception e) { }
        }
        if (kw != null) {
           //Long up_id = this.getCurrentSubjectUserProfileId();
          // if  (up_id != null) {
                BusinessConcept r = this.addUerBusConcept(subjectName, kw.getId());
                kw = r.getBusinessConcept();
           //}
        }
       // entityManager.close();
        return kw;
    }
    
    // used when the frontend has an existing Keyword (with id) and  wants to add a business concept relationship
    public KeywordValue addUserBusConcept(String subjectName, Long id) {
        //EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        KeywordValue kw = null;
        try {
            kw = (KeywordValue)KeywordRepository.getInstance().findOne(id);
            //Long up_id = this.getCurrentSubjectUserProfileId();
            //if  (up_id != null) {
                this.addUerBusConcept(subjectName, kw.getId()); 
           //}       
        } catch(NoResultException nre){
            
        } catch (Exception e) {      
        } finally {
           // entityManager.close();
        }
        return kw;
    }
    
    
    // note id is the related Keyword.id NOT the Business Concept is
    public Long deleteUserBusConcept(String subjectName, Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Long deleted_id = null;
        //Long up_id = this.getCurrentSubjectUserProfileId();
        Query q = 
                em
                .createQuery("select a from BusinessConcept a where a.userProfile.user.email = ?1 and a.businessConcept.id = ?2");
        q.setParameter(1, subjectName);
        q.setParameter(2, id);
         try {
            em.getTransaction().begin();
            BusinessConcept us  = (BusinessConcept) q.getSingleResult();
            deleted_id = us.getId();
            em.remove(us);
            em.getTransaction().commit();
         } catch (NoResultException nre) {
            em.getTransaction().rollback();
         } finally {
             em.close();
         }
        return deleted_id;
    }
    
    public List<KeywordValue> getUserBusConcepts(String subjectName) {  
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a.businessConcept from BusinessConcept a where a.userProfile.user.email = ?1");
        q.setParameter(1, subjectName);
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
    
    
}
