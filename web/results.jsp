<%-- 
    Document   : results
    Created on : 20-01-2016, 5:25:31
    Author     : Nelson
--%>

<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.Socket"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.FileReader"%>
<%@ page import="lab_3_sd.ClientStart" %>
<%@ page import="lab_3_sd.ClienteGET" %>
<!DOCTYPE html>
<html>
    <head>
         <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>My WSE</title>
    </head>
    <body>
        <%
            String parametro = request.getParameter("searchbox");
            parametro = ClientStart.filtrarSW(parametro);
            parametro = ClienteGET.clientGET(parametro);
            
            if (parametro.equals("MISS")) {
                    response.sendRedirect("notfound.jsp"); 
            }
            else{
                String[] documentos = parametro.split(",");
                ArrayList<String[]> docs = new ArrayList();

                for (int i = 0; i < documentos.length; i++) {
                    String[] docActual = new String[2];
                    docActual = documentos[i].split("\\*");
                    docActual[0] = docActual[0].replaceAll("[^a-z \\nA-Z]","");
                    docActual[1] = docActual[1].replaceAll("[^a-z \\nA-Z]","");
                    docs.add(docActual);
                }
            
        
                out.println((String) "<p>");
                for(int i = 0; i < docs.size(); i++)
                {
                   out.println((String)docs.get(i)[0] + "<br>");
                }
                out.println((String) "</p>");
            }
            %>
        
        
        
    </body>
</html>
