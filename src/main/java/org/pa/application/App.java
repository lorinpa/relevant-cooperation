package org.pa.application;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import org.pa.service.UserService;
import org.pa.service.KeywordValueService;
import org.pa.service.ProposalService;
import org.pa.service.UserProfileService;

/**
 *
 * @author mwave
 */
@ApplicationPath("/api")
public class App extends Application {
   
   private Set<Object> singletons = new HashSet<Object>();
   private Set<Class<?>> empty = new HashSet<Class<?>>();

   public App()
   {
      singletons.add(new UserService());
      singletons.add(new KeywordValueService());
      singletons.add(new UserProfileService());
      singletons.add(new ProposalService());
   }

   @Override
   public Set<Class<?>> getClasses()
   {
      return empty;
   }

   @Override
   public Set<Object> getSingletons()
   {
      return singletons;
   }
}
