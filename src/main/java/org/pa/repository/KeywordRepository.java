/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.pa.data.KeywordValue;
import org.pa.dto.SearchResultDTO;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class KeywordRepository extends CommonRepository {
    
    private static KeywordRepository instance;
    
    public static KeywordRepository getInstance() {
        if (instance == null) {
            instance = new KeywordRepository();
        }
        return instance;
    }
    
   // private EntityManager entityManager;
   
    private KeywordRepository() {
       // this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
   
    @Transactional
    public Object addNew(Object s) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        return s;
    }
    
    
    @Transactional
    public KeywordValue update(Serializable id, String keywordValue) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from KeywordValue a where a.id = ?1");
        q.setParameter(1, id);
        em.getTransaction().begin();
        KeywordValue kw = (KeywordValue)q.getSingleResult();
        kw.setKeywordValue(keywordValue);
        em.merge(kw);
        em.getTransaction().commit();
        em.close();
        return kw;
    }

    @Transactional
    public void delete(Object t) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        KeywordValue kw = (KeywordValue) t;
        if (!em.contains(kw)) {
            Query q = em.createQuery("select a from KeywordValue a where a.id = ?1");
            q.setParameter(1, kw.getId());
            kw = (KeywordValue) q.getSingleResult();
        }
        em.getTransaction().begin();
        em.remove(kw);
        em.getTransaction().commit();
        em.close();
    }
    
     @Transactional
    public void deleteById(Long id) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from KeywordValue a where a.id = ?1");
        q.setParameter(1, id);
        KeywordValue kw = (KeywordValue) q.getSingleResult();
        em.getTransaction().begin();
        em.remove(kw);
        em.getTransaction().commit();
        em.close();
    }
    
    
   @Transactional
    public Object findOne(Serializable id) throws javax.persistence.NoResultException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from KeywordValue a where a.id = ?1");
        q.setParameter(1, id);
        Object obj=  q.getSingleResult();
        em.close();
        return obj;
    }
    
    @Transactional
    public KeywordValue findByValue(String value) throws javax.persistence.NoResultException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
       
        Query q = em.createQuery("select a from KeywordValue a where a.keyword = ?1");
        q.setParameter(1, value);
        KeywordValue result = (KeywordValue) q.getSingleResult();
        em.close();
        return result;
    }
    
    @Transactional 
    public List<KeywordValue> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from KeywordValue a");
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
    
    @Transactional 
    public List<KeywordValue> findAllAscending() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from KeywordValue a ORDER BY a.keyword ASC");
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
    
    public List<KeywordValue> findUnused() {
        List<KeywordValue> result = new ArrayList<KeywordValue>();
        List<Long> ids= new ArrayList<Long>();
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {        
            Query bq = em.createQuery("select a.businessConcept.id from BusinessConcept a LEFT OUTER JOIN a.businessConcept KeywordValue");
            List<Long> blist = bq.getResultList();
            ids.addAll(blist);
            Query pq = em.createQuery("select a.partnerService.id from PartnerService a LEFT OUTER JOIN a.partnerService KeywordValue");
            List<Long> plist = pq.getResultList();
            ids.addAll(plist);
            Query uq = em.createQuery("select a.userProvidedService.id from UserProvidedService a LEFT OUTER JOIN a.userProvidedService KeywordValue");
            List<Long> ulist = uq.getResultList();
            ids.addAll(ulist);
            Query rq = em.createQuery("select a from KeywordValue a WHERE a.id not in ?1");
            rq.setParameter(1, ids);
            result = rq.getResultList();
        } catch (Exception e) {
            String debug = e.getMessage();
        } finally {
            em.close();
        }
        
        return result;
    }
    
    public List<SearchResultDTO> search(String subjectName) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = 
          em.createQuery("select new org.pa.dto.SearchResultDTO(a.userProfile.id, a.userProfile.name, a.userProvidedService.keyword, a.userProfile.location)"
                + " from UserProvidedService a,  PartnerService b "
                  + "where LOCATE(b.partnerService.keyword, a.userProvidedService.keyword) > 0 and "
                + " a.userProfile.user.email != ?1 and b.userProfile.user.email = ?2 order by a.userProfile.name,  a.userProvidedService.keyword");
       
        q.setParameter(1, subjectName);
        q.setParameter(2, subjectName);
        List<SearchResultDTO> list = q.getResultList();
        em.close();
        return list;
    }
    
    public List<SearchResultDTO> searchBusConcepts(String subjectName) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = 
          em.createQuery("select new org.pa.dto.SearchResultDTO(a.userProfile.id, a.userProfile.name, a.businessConcept.keyword, a.userProfile.location)"
                + " from BusinessConcept a,  BusinessConcept b "
                  + "where LOCATE(b.businessConcept.keyword, a.businessConcept.keyword) > 0 and "
                + " a.userProfile.user.email != ?1 and b.userProfile.user.email = ?2 order by a.userProfile.name,  a.businessConcept.keyword");
   
        q.setParameter(1, subjectName);
        q.setParameter(2, subjectName);
        List<SearchResultDTO> list = q.getResultList();
        em.close();
        return list;
    }
    
}
