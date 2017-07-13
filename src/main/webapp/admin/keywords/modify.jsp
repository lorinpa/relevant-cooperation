<%-- 
    Document   : modify
    Created on : Jul 11, 2017, 4:06:14 PM
    Author     : mwave
--%>
<%@page import="org.pa.data.KeywordValue"%>
<%@page import="org.pa.repository.KeywordRepository"%>
<% 
    KeywordValue kw = null;
    if ( "GET".equals(request.getMethod())) {
        Long ID = new Long(request.getParameter("id"));
        kw = (KeywordValue)KeywordRepository.getInstance().findOne(ID);
    }
    
    if ( "POST".equals(request.getMethod())) {
        String newValue = request.getParameter("nv");
        Long ID = new Long(request.getParameter("id"));
        kw = KeywordRepository.getInstance().update(ID, newValue);
        response.sendRedirect("list.jsp");
    }
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <%@include file="/WEB-INF/jspf/menu-style.jspf" %>
 
    <body>
        <%@include file="/WEB-INF/jspf/admin-menu.jspf" %>
        <div style="margin-left: 10%;margin-right: 10%;">
        <form method="POST" action="modify.jsp">
            <input type="hidden" name="id" value="<%= kw.getId() %>"/>
            
            <table>
                <tr><td><input type="text" name="nv" value="<%= kw.getKeyword()  %>"/></td></tr>
                <tr><td><input type="submit"/></td></tr>
            </table>
        </form>
        </div>
    </body>
</html>
