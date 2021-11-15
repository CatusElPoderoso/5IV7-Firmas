<%-- 
    Document   : descifrar
    Created on : 13/11/2021, 08:50:18 PM
    Author     : cactu
--%>

<%@page import="java.io.IOException"%>
<%@page import="java.io.FileReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@page import="javax.servlet.http.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
    <link rel="stylesheet" href="css.css">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    
    <body>
        <h1>Verificar y descifrar</h1>
        <form name="formulario" method="POST" enctype="multipart/formdata">
            <label for="archivo">Archivo cifrado:</label><br>
            <input type="file" id="archivo" name="archivo" onchange="cargarArchivo(this)" accept=".txt, .TXT"/>
            <br>
            <label for="firma">Firma del archivo cifrado:</label><br>
            <input type="file" id="firma" name="firma" accept=".txt, .TXT, .KEY"/>
            <br>
            <label for="llave">Llave privada del archivo cifrado:</label><br>
            <input type="file" id="llave" name="llavepriv" accept=".txt, .TXT"/>
            <br>
            <label for="llave">Llave pública del archivo cifrado:</label><br>
            <input type="file" id="llave" name="llavepubli" accept=".txt, .TXT"/>
            <br>
            <br>
            <input type="submit" name="proceso" value="Verificar y descifrar archivo"/>
        </form> 
    <iframe name="null" style="display: none;"></iframe>
    <br>
    <pre id="output"></pre>
      
    <a href="index.jsp">Volver a cifrar y firmar</a>
    
    <script type="text/javascript">
        // muestra el contenido del archivo cifrado
        document.getElementById('archivo').addEventListener('change', function() {
            var fr = new FileReader();
            fr.onload = function(){
                document.getElementById('output')
                        .textContent=fr.result;
            };
            fr.readAsText(this.files[0]);
        });
        
    </script>
        
    <script type="text/javascript" src="./codigo.js"></script>

    </body>

