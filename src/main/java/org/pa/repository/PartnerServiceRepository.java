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
import org.pa.data.PartnerService;
import org.pa.data.KeywordValue;
import org.pa.data.UserProfile;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class PartnerServiceRepository extends CommonRepository {
     private static PartnerServiceRepository instance;
    
    private PartnerServiceRepository() {
       // this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public static PartnerServiceRepository getInstance() {
        if (instance == null) {
            instance = new PartnerServiceRepository();
        }
        return instance;
    }
    
    
    private PartnerService addPartnerServiceRelation(String subjectName,Long keyWordValueId) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        //UserProfile up = (UserProfile)UserProfileRepository.getInstance().findBySubjectName(subjectName);
        Query qu = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        qu.setParameter(1, subjectName);
        UserProfile up = (UserProfile) qu.getSingleResult();
      
        //KeywordValue kw = (KeywordValue) KeywordRepository.getInstance().findOne(keyWordValueId);
        Query qk = em.createQuery("select a from KeywordValue a where a.id = ?1");
        qk.setParameter(1, keyWordValueId);
        KeywordValue kw  = (KeywordValue) qk.getSingleResult();
        
        PartnerService s = new PartnerService();
        s.setUserProfile(up);
        s.setPartnerService(kw);
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }
    
    // used when the frontend has a new string to add as a keyword and assign it as a partner service relationship
    public KeywordValue addPartnerService(String subjectName, String keywordValue) {
        KeywordValue kw = null;
        try {
            kw = KeywordRepository.getInstance().findByValue(keywordValue);
        } catch(NoResultException nre){
            try {
                kw = (KeywordValue) KeywordRepository.getInstance().addNew(new KeywordValue(keywordValue));     
            } catch(Exception e) { }
        }
        if (kw != null) {
              PartnerService r = this.addPartnerServiceRelation(subjectName, kw.getId());
              kw = r.getPartnerService();
        }
        return kw;
    }
    
    // used when the frontend has an existing Keyword (with id) and  wants to add a partner service relationship
    public KeywordValue addPartnerService(String subjectName, Long id) {
        KeywordValue kw = null;
        try {
            kw = (KeywordValue)KeywordRepository.getInstance().findOne(id);
            this.addPartnerServiceRelation(subjectName, kw.getId()); 
          
        } catch(NoResultException nre){
            
        } catch (Exception e) {      
        } finally {
          
        }
        return kw;
    }
    
    
    // note id is the related Keyword.id NOT the Partner Service id
    public Long deletePartnerService(String subjectName, Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Long deleted_id = null;
        //Long up_id = this.getCurrentSubjectUserProfileId();
        Query q = 
                em
                .createQuery("select a from PartnerService a where a.userProfile.user.email = ?1 and a.partnerService.id = ?2");
        q.setParameter(1, subjectName);
        q.setParameter(2, id);
         try {
            em.getTransaction().begin();
            PartnerService us  = (PartnerService) q.getSingleResult();
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
    
    public List<KeywordValue> getPartnerServices(String subjectName) {  
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a.partnerService from PartnerService a where a.userProfile.user.email = ?1");
        q.setParameter(1, subjectName);
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
}
