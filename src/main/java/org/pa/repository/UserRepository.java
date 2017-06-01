/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.pa.data.User;
import org.pa.data.UserProfile;
import org.pa.exception.UniqueException;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class UserRepository extends CommonRepository {
    
    private static UserRepository instance;
    
    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }
    
   // private EntityManager entityManager;

    private UserRepository() {
        //this.entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
    }
    
    public boolean getUserBySubjectName(String subjectName) {
        User up = null;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query q = em.createQuery("select a from User a where a.email = ?1");
            q.setParameter(1, subjectName);
            up = (User) q.getSingleResult();
        } catch(Exception e) {
            
        } finally {
           em.close();
        }
        return up != null;
    }
    
    public boolean updatePassword(String subjectName, String passwd) {
        boolean result = false;
        User up = null;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query q = em.createQuery("select a from User a where a.email = ?1");
            q.setParameter(1, subjectName);
            em.getTransaction().begin();
            up = (User) q.getSingleResult();
            up.setPassword(passwd);
            em.merge(up);
            em.getTransaction().commit();
            result = true;
        } catch(Exception e) {
            em.getTransaction().rollback();
        } finally {
           em.close();
        }
        return result;
    }
    
    public String updateSubject(String subjectName, String newSubjectName) throws UniqueException {
        boolean result = false;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        Query q = em.createQuery("select COUNT(a.id) from User a where a.email = ?1");
        q.setParameter(1, newSubjectName);
        em.getTransaction().begin();
        Long count = (Long) q.getSingleResult();
        if (count >0 ) { 
            em.getTransaction().rollback();
            throw new UniqueException();
        }
        Query qu = em.createQuery("select a from User a where a.email = ?1");
        qu.setParameter(1, subjectName);
        User user = (User) qu.getSingleResult();
        user.setEmail(newSubjectName);
        em.merge(user);
        em.getTransaction().commit();
        em.close();
        return newSubjectName;
    }
}
