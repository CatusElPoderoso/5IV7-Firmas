
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;
import javax.crypto.Cipher;

/**
 *
 * @author cactu
 */
public class checando {
    
    private static Cipher rsa;
    
    public String descifrar (String cifrado, PrivateKey llavePrivada) throws Exception{
        
        rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        
        //descifrar
        rsa.init(Cipher.DECRYPT_MODE, llavePrivada);
        byte[] bytesdescifrados = rsa.doFinal(cifrado.getBytes());
        String textodescifrado = new String(bytesdescifrados);
        System.out.println("Mensaje Descifrado: " + textodescifrado);
        
        return textodescifrado;
    }
    public boolean verificar (String data, String firmadocumento, PublicKey llavePublica) throws Exception{
        Signature firma = Signature.getInstance("MD5WithRSA");    
        
        byte[] firmabytes = Base64.getDecoder().decode(new String(firmadocumento).getBytes("UTF-8"));
        byte[] databytes = Base64.getDecoder().decode(new String(data).getBytes("UTF-8"));
        
        //proceso de verificacion de la firma
        
        firma.initVerify(llavePublica);
        
        //vamos a actualizar el edo del documento
        
        firma.update(databytes);
        
        boolean veridico = firma.verify(firmabytes);
        
        System.out.println("¿El documento es veridico? "
                + firma.verify(firmabytes));
        
        return veridico;
    }
    
    public static void generarArchivo(String textodescifrado) throws FileNotFoundException, IOException {
        try {
            String ruta = "C:/Documentos/";
            String contenido = textodescifrado;
            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

}
