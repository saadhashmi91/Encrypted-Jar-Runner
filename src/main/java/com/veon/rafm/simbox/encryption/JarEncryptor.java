package com.veon.rafm.simbox.encryption;


import org.mapdb.DB;
import org.mapdb.DBMaker;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.concurrent.ConcurrentMap;

public class JarEncryptor {
    private DB db;

    public JarEncryptor(){
    }

    final static String algorithm = "AES";

  public static  SecretKeySpec getKey(String password) throws Exception{

        byte[] salt = {
                (byte)0xc7, (byte)0x73, (byte)0x21, (byte)0x8c,
                (byte)0x7e, (byte)0xc8, (byte)0xee, (byte)0x99
        };



      ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
      outputStream.write( salt);
      outputStream.write( password.getBytes("UTF-8") );
      byte[] key = outputStream.toByteArray();
      MessageDigest sha = MessageDigest.getInstance("SHA-1");
      key = sha.digest(key);
      key = Arrays.copyOf(key, 16); // use only first 128 bit
      SecretKeySpec secret = new SecretKeySpec(key, "AES");
        return  secret;

      //  Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
       // cipher.init(Cipher.ENCRYPT_MODE, secret);

       // byte[] iv = cipher.getParameters().getParameterSpec(IvParameterSpec.class).getIV();
       // byte[] ciphertext = cipher.doFinal("Hello, World!".getBytes("UTF-8"));
    }

}
