<%-- 
    Document   : newjsp
    Created on : 20-01-2016, 4:31:22
    Author     : Nelson
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>My WSE</title>
    </head>
    <body>
        <h1>Ingrese su búsqueda</h1>
        <form form="searchbar" action="results.jsp" method="POST">
            <input type="text" name="searchbox" value="" /><br>
            <input type="submit" value="Buscar" />
        </form>
    </body>
</html>
