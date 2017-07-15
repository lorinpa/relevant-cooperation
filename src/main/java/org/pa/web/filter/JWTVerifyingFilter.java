/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pa.web.filter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.DatatypeConverter;
import org.apache.shiro.web.filter.AccessControlFilter;
import io.jsonwebtoken.Jwts;
import static org.pa.definitions.RequestDefinitions.JWT_ATTR_KEY;
import org.pa.repository.UserRepository;
 
public class JWTVerifyingFilter extends AccessControlFilter {
 
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse arg1, Object arg2) throws Exception {
        boolean accessAllowed = false;
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String jwt_key = (String) httpRequest.getServletContext().getAttribute(JWT_ATTR_KEY);
        String jwt = httpRequest.getHeader("Authorization");
        if (jwt == null || !jwt.startsWith("Bearer ")) {
            return accessAllowed;
        }
        jwt = jwt.substring(jwt.indexOf(" "));
        String username = Jwts.parser().setSigningKey(DatatypeConverter.parseBase64Binary(jwt_key))
                .parseClaimsJws(jwt).getBody().getSubject();
        //String subjectName = (String) SecurityUtils.getSubject().getPrincipal();
        accessAllowed = UserRepository.getInstance().getUserBySubjectName(username);
        if (accessAllowed) httpRequest.setAttribute("subj", username);
        
        /*if (username.equals(subjectName)) {
            accessAllowed = true;
        }*/
        return accessAllowed;
    }
 
    @Override
    protected boolean onAccessDenied(ServletRequest arg0, ServletResponse arg1) throws Exception {
        HttpServletResponse response = (HttpServletResponse) arg1;
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        return false;
    }
 
}