<%-- 
    Document   : logout
    Created on : Jul 11, 2017, 2:47:44 PM
    Author     : mwave
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
      <%
        response.setStatus(301);
        response.setHeader("Location", "/co/#/logout");
        response.setHeader("Connection", "close");
    %>
    </body>
</html>
