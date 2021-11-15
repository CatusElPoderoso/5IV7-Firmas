/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.firmastarea;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

/**
 *
 * @author Aaron
 */
public class cifraryotrascosasyasi {
    public static void cifrarFirmarySacarLLaves(String texto,String ruta) throws NoSuchAlgorithmException, IOException, NoSuchPaddingException, SignatureException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        KeyPairGenerator generadorRSA = KeyPairGenerator.getInstance("RSA");
        KeyPair llavesRSA = generadorRSA.generateKeyPair();
        PublicKey llavePublica = llavesRSA.getPublic();
        PrivateKey llavePrivada = llavesRSA.getPrivate();
        saveKey(llavePrivada,ruta+"llaveprivada.key");
        Cipher rsa = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        byte[] textoEnBytes = texto.getBytes();
        Signature firma = Signature.getInstance("MD5WithRSA");
        firma.initSign(llavePrivada);
        firma.update(textoEnBytes);
        byte[] firmadocumento = firma.sign();
        saveDatos(firmadocumento,ruta+"firma.sign");
        rsa.init(Cipher.ENCRYPT_MODE, llavePublica);
        byte[] cifrado = rsa.doFinal(textoEnBytes);
        saveDatos(cifrado,ruta+"cifrado.txt");
    }
    
    private static void saveKey(Key key, String archivo) throws FileNotFoundException, IOException {
        byte[] llavesPubPriv = key.getEncoded();
        //genero mi archivo
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavesPubPriv);
        fos.close();
    }
    private static void saveDatos(byte[] datos, String archivo) throws FileNotFoundException, IOException {
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(datos);
        fos.close();
    }
}
