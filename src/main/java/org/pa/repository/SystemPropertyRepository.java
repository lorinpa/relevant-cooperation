/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.pa.data.SystemProperty;
import org.pa.jpa.JPAUtil;

/**
 *
 * @author mwave
 */
public class SystemPropertyRepository {
    private static SystemPropertyRepository instance;
    
    public final static String JWT_DB_VALUE = "JWT_KEY";

    public SystemPropertyRepository() {
    }
    
    
    
    public static SystemPropertyRepository getInstance() {
        if (instance == null) {
            instance = new SystemPropertyRepository();
        }
        return instance;
    }
    
    public String findJwtKey() {
        String result = "secret";
        EntityManager em = JPAUtil.getEntityManagerFactory().createEntityManager();
        try {
            Query q = em.createQuery("select a.property_value from SystemProperty a where a.property_name = ?1");
            q.setParameter(1, JWT_DB_VALUE);
            result = (String) q.getSingleResult();
           
        } catch(Exception e) {
            
        } finally {
           em.close();
        }
        return result;
    }
}
