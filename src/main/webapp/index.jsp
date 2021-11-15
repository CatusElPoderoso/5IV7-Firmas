<%-- 
    Document   : index
    Created on : 13 nov. 2021, 19:42:48
    Author     : Aaron
--%>

<%@page import="java.nio.charset.StandardCharsets"%>
<%@page import="java.security.spec.PKCS8EncodedKeySpec"%>
<%@page import="java.security.PrivateKey"%>
<%@page import="java.security.spec.KeySpec"%>
<%@page import="java.security.KeyFactory"%>
<%@page import="java.io.InputStream"%>
<%@page import="java.nio.file.Paths"%>
<%@page import="com.mycompany.firmastarea.cifraryotrascosasyasi"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <link rel="stylesheet" href="css.css">
    <%
        try{
        ServletContext context = request.getServletContext();
        String path = context.getRealPath("/");
        cifraryotrascosasyasi.cifrarFirmarySacarLLaves(request.getParameter("texto"),path);
        
        }catch(Exception e){
        }
    %>
    <header>
        <h1>Cifrar, Firmar y exportar llave privada uwu</h1>
    </header>
    <body>
        <div class="container">
            <div class="form">
                <form method="POST" action="">
                    <p>Ingrese el texto a cifrar</p>
                    <input type="text" name="texto" value="" />
                    <br>
                    <input type="submit"/>
                    <br>
                </form>
            </div>
        </div>
        <a href="verificar.jsp">Ir a verificar y descifrar</a>
    </body>
</html>
