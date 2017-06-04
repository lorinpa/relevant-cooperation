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
import org.pa.dto.RegistrationDTO;
import org.pa.exception.UniqueException;
import org.pa.jpa.JPAUtil;
import org.pa.utils.StringGen;

/**
 *
 * @author mwave
 */
public class UserRepository extends CommonRepository {
    
    private static UserRepository instance;
    
    private final String DEFAULT_LOCATION = "Anytown";
    
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
    
    /*
        This method needs to do several things. 
        -   Check/Verify is this an existing account? or a new account
        -   An account is : new User + related new UserProfile()
        -   Create a unique "name" from UserProfile.name (alias)
            -   take the left hand portion of the email address and then iteratate:
                - is the value being used. if not use it. else suffix a couple characters and check again.
        
    */
    public RegistrationDTO createAccount(String email) throws  Exception {
        RegistrationDTO registration = new RegistrationDTO();
        registration.setEmail(email);
        boolean isNewAccount = false;
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            
            Query q = em.createQuery("select COUNT(a.id) from User a where a.email = ?1");
            q.setParameter(1, email);
            em.getTransaction().begin();
            Long count = (Long) q.getSingleResult();
            if (count == 0) isNewAccount = true;
            registration.setIsNewAccount(isNewAccount);
            if (isNewAccount) {
                 boolean nameFound = false;
                 String name = email.split("@")[0];
                
                 Query nq = em.createQuery("select COUNT(a.id) from UserProfile a where a.name = ?1");
                 do {
                     nq.setParameter(1, name);
                     count = (Long) nq.getSingleResult();
                     if (count == 0) { 
                         nameFound = true;
                     } else {
                         name = name+ StringGen.getInstance().generate(3);
                     }
                 } while(!nameFound);
                 String password = StringGen.getInstance().generate(8);
                 User user = new User();
                 user.setEmail(email);
                 user.setPassword(password);
                 em.persist(user);
                 UserProfile userProfile = new UserProfile(name, DEFAULT_LOCATION);
                 userProfile.setUser(user);
                 em.persist(userProfile);
                 em.getTransaction().commit();
                 registration.setName(name);
                 registration.setPassword(password);
                
                  
            } else {
                 Query passwordQuery = em.createQuery("select a.password from User a where a.email = ?1");
                 passwordQuery.setParameter(1, email);
                 String password = (String) passwordQuery.getSingleResult();
                 em.getTransaction().commit();
                 registration.setPassword(password);
            }
            
        } catch (Exception e ) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
                
        return registration;
    }
}
