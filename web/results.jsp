<%-- 
    Document   : results
    Created on : 20-01-2016, 5:25:31
    Author     : Nelson
--%>

<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.Socket"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="lab_3_sd.ClientStart" %>
<%@ page import="lab_3_sd.ClienteGET" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My WSE</title>
    </head>
    <body>
        <%
            String parametro = request.getParameter("searchbox");
            parametro = ClientStart.filtrarSW(parametro);
//            parametro = ClientStart.GET(parametro);
            parametro = ClienteGET.clientGET(parametro);
        %>
        <p> <%= parametro %> </p>
    </body>
</html>
