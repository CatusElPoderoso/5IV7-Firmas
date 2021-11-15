/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author cactu
 */
public class ProcesoArchivo extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, NoSuchAlgorithmException, InvalidKeySpecException, Exception {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            KeyFactory factory = KeyFactory.getInstance("RSA");
            
            InputStream input = null;
            Part parchivo = request.getPart("archivo");    
            
                if(parchivo.getSize()>0){
                    input = parchivo.getInputStream();
                }
            
            Part pllavepubli = request.getPart("llavepubli");
            String str_publi = pllavepubli.toString();
            byte[] byte_publi = Base64.getDecoder().decode(str_publi);
            // llave pública
            PublicKey llavepubli = factory.generatePublic(new X509EncodedKeySpec(byte_publi));
            
            Part pllavepriv = request.getPart("llavepriv");           
            String str_priv = pllavepriv.toString();
            byte[] byte_priv = Base64.getDecoder().decode(str_priv);
            // llave privada
            PrivateKey llavepriv = factory.generatePrivate(new X509EncodedKeySpec(byte_priv));
            
            Part pfirma = request.getPart("firma");
            String firma = pfirma.toString();
                
            InputStreamReader reader = new InputStreamReader(input);
            Stream <String> stream = new BufferedReader(reader).lines();
            String cifrado = stream.collect(Collectors.joining());

            
            boolean verificando = new checando().verificar(cifrado, firma, llavepubli);
            
            if(verificando){
                System.out.println("El documento es verídico");
                // descifra
                String descifrado = new checando().descifrar(cifrado, llavepriv);
                // crea archivo
                new checando().generarArchivo(descifrado);
            }else{
                System.out.println("El documento es falso");
                response.sendRedirect(error.jsp);
            }
                
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ProcesoArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProcesoArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(ProcesoArchivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ProcesoArchivo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>


}
