<%-- 
    Document   : otrojsp
    Created on : 23/01/2016, 02:02:26 PM
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
        <h1>Busqueda no encontrada</h1>
        <form
            <input  align="center" type="submit" value="Volver a la pagina principal" />
        </form>
        <%
            if(true){
                response.sendRedirect("start.jsp");
            }
        %>
    </body>
</html>
