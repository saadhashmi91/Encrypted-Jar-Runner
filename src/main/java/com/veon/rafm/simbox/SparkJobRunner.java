package com.veon.rafm.simbox;

import com.veon.rafm.simbox.encryption.EncryptedClassLoader;
import com.veon.rafm.simbox.encryption.JarEncryptor;

import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;

public class SparkJobRunner {

    SparkJobRunner()
    {

    }

    public void runJob(String password,String mainClass,String encryptedPath) throws Exception    {
///
        File file = new File(encryptedPath);
        //getClass().getResource("/encrypted").getFile()
        SecretKeySpec spec= JarEncryptor.getKey(password);

        EncryptedClassLoader myClassLoader = new EncryptedClassLoader(spec,file);
        Class dynamicClass = myClassLoader.findClass(mainClass);

        Method m = dynamicClass.getMethod("main", String[].class);
        m.invoke(null, new Object[] {null});
        myClassLoader.closeDB();
    }



    public static void main(String[] args) throws Exception{

        String password = args[0];
        String mainClass= args[1];
        String encryptedPath=args[2];

        new SparkJobRunner().runJob(password,mainClass,encryptedPath);


    }


}
