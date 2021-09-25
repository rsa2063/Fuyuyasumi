package com.example.fuyuyasumi;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import org.apache.commons.

/**
 * Created by zhangbin on 2016/1/23.
 */
public class notice {
    public static SecretKey key = null;
    public static Cipher cipher = null;
    public static byte[] a = null, b = null;
    public static void notify(String t) {
        Toast.makeText(MyApplication.context, t, Toast.LENGTH_SHORT).show();
    }

    //加密文件名
    public static  void encryptFilename(String desDirectoryName){
        initKeyCipher();

        File desDirectory = null;
        if(desDirectoryName.isEmpty()) {
            desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        } else {
            desDirectory = new File(desDirectoryName);
        }
        //File desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        //File desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        File[] files = desDirectory.listFiles();
        if(desDirectory.isDirectory()) {
            Toast.makeText(MyApplication.context, "destination file is directory",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MyApplication.context, "not directory",Toast.LENGTH_LONG).show();
        }

        if(!desDirectory.exists())
        {
            desDirectory.mkdirs();
        }

        if(files == null) {
            Toast.makeText(MyApplication.context, "no file",Toast.LENGTH_LONG).show();
            return;
        }
        for(File f : files) {
            String name1 = f.getName();
            try {
                Log.d("file", "name1 is " + name1 + "||base64 is " + Base64.encodeToString(name1.getBytes("utf-8"), Base64.DEFAULT));
            } catch (Exception e) {
                e.printStackTrace();
            }

            //String name2 = encryptAES(name1);
            String name2 = encodeFilenameByBase64(name1);
            try {
                Log.d("file", "name2 is /" + name2 + "/" + "name2 is trim /" + name2.trim() + "/");
            } catch (Exception e) {
                e.printStackTrace();
            }
            String parent = f.getParent();
            File temp = new File(parent + File.separator + name2.trim());
            boolean result = f.renameTo(temp);
            if(!result) {
                try {
                    FileUtils.copyFile(f, temp);
                } catch (Exception e) {
                    Log.d("file error rename", e.getMessage());
                }
            }
            Log.d("file", "temp is /" + temp.getName() + "/|" + temp.getPath()
            + "rename result is " + (result?"true":"false"));
            Toast.makeText(MyApplication.context, parent + "/" + name1,Toast.LENGTH_LONG).show();
        }


        /*File desDirectory = null;
        if(desDirectoryName.isEmpty()) {
            desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        } else {
            desDirectory = new File(desDirectoryName);
        }
        File[] files = desDirectory.listFiles();
        if(desDirectory.isDirectory()) {
            Toast.makeText(MyApplication.context, "destination file is directory",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MyApplication.context, "not directory",Toast.LENGTH_LONG).show();
        }

        for(File f : files) {
            String name1 = f.getName();
            Log.d("file 原文件名 decode", "name1 is " + name1);
            String name2 = encryptAES(name1);
            Log.d("file 加密后文件名 decode", "name2 is " + name2);
            String parent = f.getParent();
            File temp = new File(parent + File.separator + name2);
            f.renameTo(temp);
            Log.d("file decode", "temp is " + temp.getName() + "|" + temp.getPath());
            Toast.makeText(MyApplication.context, parent + "/" + name1,Toast.LENGTH_LONG).show();
        }*/

    }


    //解密文件名
    public static  void decryptFilename(String desDirectoryName){
        File desDirectory = null;
        if(desDirectoryName.isEmpty()) {
            desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        } else {
            desDirectory = new File(desDirectoryName);
        }
        File[] files = desDirectory.listFiles();
        if(desDirectory.isDirectory()) {
            Toast.makeText(MyApplication.context, "destination file is directory",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MyApplication.context, "not directory",Toast.LENGTH_LONG).show();
        }

        for(File f : files) {
            String name1 = f.getName();
            Log.d("file 原文件名 decode", "name1 is " + name1);
            //String name2 = decryptAES(name1);
            //String name2 = decryptAES(name1);
            String name2 = decodeFilenameByBase64(name1);
            Log.d("file 加密后文件名 decode", "name2 is " + name2);
            String parent = f.getParent();
            File temp = new File(parent + File.separator  + name2);
            //boolean result = f.renameTo(f);
            boolean result = f.renameTo(temp);
            Log.d("file decode", "temp is " + temp.getName() + "|" + temp.getPath()
            + "rename result is " + (result?"true":"false"));
            Toast.makeText(MyApplication.context, parent + "/" + name1,Toast.LENGTH_LONG).show();
        }

    }

    public static void initKeyCipher(){
        try{
            KeyGenerator kg = KeyGenerator.getInstance("AES");
            kg.init(128, new SecureRandom("you know".getBytes("utf-8")));
            SecretKey sk = kg.generateKey();
            byte [] raw = sk.getEncoded();
            key = new SecretKeySpec(raw, "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            //cipher.init(Cipher.ENCRYPT_MODE, key);
           // Log.d("file 加密密钥 key", "file key is " + encodeFilename(key.getEncoded()));
            //byte[] content = msg.getBytes("utf-8");
            //byte[] byte_aes = cipher.doFinal(content);
            //return encodeFilename(byte_aes);
        }catch (Exception e) {
            //e.printStackTrace();
            Log.d("file error encode",e.getMessage());
        }
    }

    public static String encryptAES(String msg) {
        try{
//            KeyGenerator kg = KeyGenerator.getInstance("AES");
//            kg.init(128, new SecureRandom("you know".getBytes("utf-8")));
//            SecretKey sk = kg.generateKey();
//            byte [] raw = sk.getEncoded();
//            key = new SecretKeySpec(raw, "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            Log.d("file 加密密钥 key", "file key is " + encodeFilename(key.getEncoded()));
            byte[] content = msg.getBytes("utf-8");
            Log.d("file 加密密钥 key", "明文编码后字节长度 is " + content.length);
            byte[] byte_aes = cipher.doFinal(content);
            a = byte_aes;

            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_de = cipher.doFinal(byte_aes);
            Log.d("file 加密密钥 key", "立即 解密 is " + new String(byte_de, "utf-8"));
            return encodeFilename(byte_aes);
        }catch (Exception e) {
            //e.printStackTrace();
            Log.d("file error encode",e.getMessage());
        }

        return null;
    }

    public static String decryptAES(String msg) {
        try{
//            KeyGenerator kg = KeyGenerator.getInstance("AES");
//            kg.init(128, new SecureRandom("you know".getBytes("utf-8")));
//            SecretKey sk = kg.generateKey();
//            byte [] raw = sk.getEncoded();
//            //key = new SecretKeySpec("you know".getBytes("utf-8"), "AES");
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, key);
            Log.d("file 解密密钥 key", "file key is " + encodeFilename(key.getEncoded()));
            byte[] content = decodeFilename(msg);
            Log.d("file equal is ", Arrays.toString(a).equals(Arrays.toString(content)) ? "true" : "false" + "a is " + bytesToHexString(a) + "b is " + bytesToHexString(content));
            Log.d("file 加密密钥 key", "密文字符串解码后解密前后字节长度 is " + content.length);
            byte[] byte_aes = cipher.doFinal(content); //加解密数据必须是16字节的倍数？
            return new String(byte_aes, "utf-8");
        }catch (Exception e) {
            //e.printStackTrace();
            Log.d("file error decode",e.getMessage());
        }

        return null;
    }

    public static String encodeFilename(byte[] byte_aes) {
        String temp = Base64.encodeToString(byte_aes, Base64.DEFAULT);
        Log.d("file", "ba64 encode  is " + temp);
        temp = temp.replace("+", "youknow");
        temp = temp.replace("=", "YouKnow");
        temp = temp.replace("/", "YouAre");
        temp = temp.replaceAll("\r|\n", "");
        return temp;
    }

    public static byte[] decodeFilename(String temp) {
        temp = temp.replace("youknow", "+");
        temp = temp.replace("YouKnow", "=");
        temp = temp.replace("YouAre", "/");
        Log.d("file decode", "base 64 decode  is " + temp);
        return  Base64.decode(temp, Base64.DEFAULT);
    }

    //不加密直接用base64编码隐藏文件名
    public static String encodeFilenameByBase64(String source) {
        String temp = "";
        try {
            temp = Base64.encodeToString(source.getBytes("utf-8"), Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }

        temp = temp.replace("+", "youknow");
        temp = temp.replace("=", "YouKnow");
        temp = temp.replace("/", "YouAre");
        temp = temp.replaceAll("\r|\n", "");
        return temp;
    }

    public static String decodeFilenameByBase64(String temp) {
        temp = temp.replace("youknow", "+");
        temp = temp.replace("YouKnow", "=");
        temp = temp.replace("YouAre", "/");
        try {
            temp = new String(Base64.decode(temp.getBytes("utf-8"), Base64.DEFAULT), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  temp;
    }

    public static String bytesToHexString(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            String t = Integer.toHexString(0xff & b);
            if (t.length() == 1) {
                builder.append("0");
            }
            builder.append(t);
        }
        return builder.toString();
    }
}
