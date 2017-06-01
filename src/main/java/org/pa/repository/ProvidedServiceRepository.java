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
import org.pa.data.KeywordValue;
import org.pa.data.UserProfile;
import org.pa.data.UserProvidedService;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class ProvidedServiceRepository extends  CommonRepository {
     private static ProvidedServiceRepository instance;
    
    private ProvidedServiceRepository() {
        //this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public static ProvidedServiceRepository getInstance() {
        if (instance == null) {
            instance = new ProvidedServiceRepository();
        }
        return instance;
    }
    
    private UserProvidedService addUerProvidedService(String subjectName,Serializable keyWordValueId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query qu = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        qu.setParameter(1, subjectName);
        UserProfile up = (UserProfile) qu.getSingleResult();
        
        Query qk = em.createQuery("select a from KeywordValue a where a.id = ?1");
        qk.setParameter(1, keyWordValueId);
        KeywordValue kw  = (KeywordValue) qk.getSingleResult();
        UserProvidedService s = new UserProvidedService();
        s.setUserProfile(up);
        s.setUserProvidedService(kw);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }
    
    // used when the frontend has a new string to add as a keyword and assign it as userPriovidedService relationship
    public KeywordValue addUserProvided(String subjectName, String keywordValue) {
      //  EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        KeywordValue kw = null;
        try {
            kw = KeywordRepository.getInstance().findByValue(keywordValue);
        } catch(NoResultException nre){
            try {
                kw = (KeywordValue) KeywordRepository.getInstance().addNew(new KeywordValue(keywordValue));     
            } catch(Exception e) { }
        }
        if (kw != null) {
          // Long up_id = this.getCurrentSubjectUserProfileId();
           if  (subjectName != null) {
                UserProvidedService r = this.addUerProvidedService(subjectName, kw.getId());
                kw = r.getUserProvidedService();
           }
        }
       // em.close();
        return kw;
    }
    
    // used when the frontend has an existing Keyword (with id) and  wants to add a UserProvided relationship
    public KeywordValue addUserProvided(String subjectName, Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        KeywordValue kw = null;
        try {
            kw = (KeywordValue)KeywordRepository.getInstance().findOne(id);
            //Long up_id = this.getCurrentSubjectUserProfileId();
            if  (subjectName != null) {
                this.addUerProvidedService(subjectName, kw.getId()); 
           }
            
        } catch(NoResultException nre){
            
        } catch (Exception e) {
            
        } finally {
            em.close();
        }
        return kw;
    }
    
    
    // note id is the related Keyword.id NOT the UserProvidedService is
    public Long deleteUserProvided(String subjectName, Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Long deleted_id = null;
        //Long up_id = this.getCurrentSubjectUserProfileId();
        Query q = 
                em
                .createQuery("select a from UserProvidedService a where a.userProfile.user.email= ?1 and a.userProvidedService.id = ?2");
        q.setParameter(1, subjectName);
        q.setParameter(2, id);
         try {
            em.getTransaction().begin();
            UserProvidedService us  = (UserProvidedService) q.getSingleResult();
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
    
    public List<KeywordValue> getUserProvidedServices(String subjectName) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a.userProvidedService from UserProvidedService a where a.userProfile.user.email = ?1");
        q.setParameter(1, subjectName);
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
    
}
