<%-- 
    Document   : newjsp
    Created on : 20-01-2016, 4:31:22
    Author     : Nelson
--%>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>My WSE</title>
    </head>
    <body>
        <h1 align="center">Ingrese su búsqueda</h1>
        <form  align="center" form="searchbar" action="results.jsp" method="POST">
            <input  align="center" type="text" name="searchbox" value="" /><br>
            <input  align="center" type="submit" value="Buscar" />
        </form>
    </body>
</html>
