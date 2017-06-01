/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.jpa;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author mwave
 */
public class JPAUtil {


        private static final String PERSISTENCE_UNIT_NAME = "CRM";
        private static EntityManagerFactory factory;

        public static EntityManagerFactory getEntityManagerFactory() {
            if (factory == null) {
                factory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
            }
            return factory;
        }

        public static void shutdown() {
            if (factory != null) {
                factory.close();
            }
        }
    }
