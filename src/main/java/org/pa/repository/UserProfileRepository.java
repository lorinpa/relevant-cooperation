/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.pa.data.KeywordValue;
import org.pa.data.UserProfile;
import org.pa.data.UserProvidedService;
import org.pa.dto.UserProfileDTO;
import org.pa.exception.UniqueException;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class UserProfileRepository extends CommonRepository {
    
    private static UserProfileRepository instance;
    
    public static UserProfileRepository getInstance() {
        if (instance == null) {
            instance = new UserProfileRepository();
        }
        return instance;
    }
    
   // private EntityManager entityManager;

    private UserProfileRepository() {
       // this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
     @Transactional
    public Object addNew(Object s) throws Exception {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        em.getTransaction().begin();
        em.persist(s);
        em.getTransaction().commit();
        em.close();
        return s;
    }
    
     @Transactional
    public void delete(Object t) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        UserProfile up = (UserProfile) t;
        if (!em.contains(up)) {
            Query q = em.createQuery("select a from UserProfile a where a.id = ?1");
            q.setParameter(1, up.getId());
            up = (UserProfile) q.getSingleResult();
        }
        em.getTransaction().begin();
        em.remove(up);
        em.getTransaction().commit();
        em.close();
    }
    
    @Transactional
    public UserProfile findBySubjectName(String subjectName) throws javax.persistence.NoResultException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        q.setParameter(1, subjectName);
        UserProfile result = (UserProfile) q.getSingleResult();
        em.close();
        return result;
    }
    
    @Transactional
    public UserProfileDTO getProfileData(String subjectName) throws javax.persistence.NoResultException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        UserProfileDTO up = null;
        //String subjectName = (String) SecurityUtils.getSubject().getPrincipal();
        //this.entityManager = getEntityManager();
        Query q = em.createQuery("select new org.pa.dto.UserProfileDTO(name,location) from UserProfile a where a.user.email = ?1");
        q.setParameter(1, subjectName);
        up = (UserProfileDTO) q.getSingleResult();
        up.setEmail(subjectName);
        em.close();
        return up;
    }
    
    @Transactional 
    public List<KeywordValue> findAll() {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select a from UserProfile a");
        List<KeywordValue> list = q.getResultList();
        em.close();
        return list;
    }
 
    
    public String updateLocation(String subjectName, String location) {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        UserProfile up = null;
        //this.entityManager = getEntityManager();
        Query q = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        q.setParameter(1, subjectName);
        em.getTransaction().begin();
        up = (UserProfile) q.getSingleResult();
        up.setLocation(location);
        em.merge(up);
        em.getTransaction().commit();
        em.close();
        return location;
    }
    
    /*
        if name exists for other user throw exception, otherwise update to new name
    */
    public String updateName(String subjectName, String name) throws UniqueException {
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select COUNT(a.id) from UserProfile a where a.user.email != ?1 and a.name = ?2");
        q.setParameter(1, subjectName);
        q.setParameter(2, name);
        em.getTransaction().begin();
        Long count = (Long) q.getSingleResult();
        if (count >0 ) { 
            em.getTransaction().rollback();
            throw new UniqueException();
        }
        Query qu = em.createQuery("select a from UserProfile a where a.user.email = ?1");
        qu.setParameter(1, subjectName);
        //em.getTransaction().begin();
        UserProfile up = (UserProfile) qu.getSingleResult();
        up.setName(name);
        em.merge(up);
        em.getTransaction().commit();
        em.close();
        return name;
    }
}
