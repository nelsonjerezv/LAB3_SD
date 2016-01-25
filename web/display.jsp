<%-- 
    Document   : display
    Created on : 24/01/2016, 11:15:58 PM
    Author     : Nelson
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
        String cuerpo = request.getParameter("cuerpo");
        String titulo = request.getParameter("titulo");
        %>
        
        <h1>
            <%=titulo%>
        </h1>
        <p>
            <%=cuerpo%>
        </p>
    </body>
</html>
