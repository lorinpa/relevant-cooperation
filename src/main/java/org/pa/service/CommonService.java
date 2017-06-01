/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.service;
import javax.servlet.http.HttpServletRequest;
import static org.pa.definitions.RequestDefinitions.REQUEST_SUBJECT;

/**
 *
 * @author mwave
 */
public class CommonService {
    
    protected String getSubjectName(HttpServletRequest request) {
        String subjectName = null;
        subjectName = (String) request.getAttribute(REQUEST_SUBJECT);
        return subjectName;
    }
    
    
}
