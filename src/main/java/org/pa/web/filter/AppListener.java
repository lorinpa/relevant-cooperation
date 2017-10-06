/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.web.filter;

import javax.ejb.EJB;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;
import org.pa.ejb.RssTimer;
import org.pa.repository.SystemPropertyRepository;

/**
 * Web application lifecycle listener.
 *
 * @author mwave
 */
@WebListener
public class AppListener implements ServletContextListener {
    
    @EJB(lookup ="java:global/co/RssTimer!org.pa.ejb.RssTimer")
    private RssTimer rssTimer;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jwt_key = SystemPropertyRepository.getInstance().findJwtKey();
        sce.getServletContext().setAttribute(JWT_ATTR_KEY,jwt_key);
        /* get all the rss feeds at startup */
        if (rssTimer != null) {
            rssTimer.democracyAtWorkRssTimer();
            rssTimer.usFedWorkerCoopRssTimer();
            rssTimer.demEnterpriseRssTimer();
            rssTimer.redditRssTimer();
            rssTimer.googleRssTimer();          
        } else {
            System.err.println("Could not do ejb lookup");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
