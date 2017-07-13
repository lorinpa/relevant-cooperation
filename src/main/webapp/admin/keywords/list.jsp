<%-- 
    Document   : list
    Created on : Jul 11, 2017, 3:17:13 PM
    Author     : mwave
--%>

<%@page import="org.pa.data.KeywordValue"%>
<%@page import="java.util.List"%>
<%@page import="org.pa.repository.KeywordRepository"%>
<%
  List<KeywordValue> list = KeywordRepository.getInstance().findAllAscending();
%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
     <%@include file="/WEB-INF/jspf/menu-style.jspf" %>
 
    <body>
        <%@include file="/WEB-INF/jspf/admin-menu.jspf" %>
         <div style="margin-left: 10%;margin-right: 10%; margin-top: 12px;">
        <table>
       <%  for (KeywordValue kw: list) { %>
       <tr>
           <td><%= kw.getKeyword() %></td><td><a href="modify.jsp?id=<%=kw.getId()%>">Modify</a></td>
       </tr>
                
       <% } %>
        </table>
         </div>
    </body>
</html>
