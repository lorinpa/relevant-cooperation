/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.web.filter;

import java.util.List;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.pa.data.KeywordValue;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;
import org.pa.repository.SystemPropertyRepository;

/**
 * Web application lifecycle listener.
 *
 * @author mwave
 */
@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String jwt_key = SystemPropertyRepository.getInstance().findJwtKey();
        sce.getServletContext().setAttribute(JWT_ATTR_KEY,jwt_key);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
