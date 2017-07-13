<%-- 
    Document   : delete
    Created on : Jul 12, 2017, 1:36:06 PM
    Author     : mwave
--%>
<%@page import="org.pa.data.KeywordValue"%>
<%@page import="org.pa.repository.KeywordRepository"%>
<% 
    KeywordValue kw = null;
    String value = "";
    if ( "GET".equals(request.getMethod())) {
        Long ID = new Long(request.getParameter("id"));
        kw = (KeywordValue)KeywordRepository.getInstance().findOne(ID);
        value = kw.getKeyword();
    }
    
    if ( "POST".equals(request.getMethod())) {
        String newValue = request.getParameter("nv");
        Long ID = new Long(request.getParameter("id"));
        KeywordRepository.getInstance().deleteById(ID);
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
        <% if ( "GET".equals(request.getMethod())) {  %>
        <form method="POST" action="delete.jsp">
            <input type="hidden" name="id" value="<%= kw.getId() %>"/>
            
            <table>
                <tr><td><%= value %></td></tr>
                <tr><td><input type="submit"/></td></tr>
            </table>
        </form>
        <% } %>
        </div>
    </body>
</html>
