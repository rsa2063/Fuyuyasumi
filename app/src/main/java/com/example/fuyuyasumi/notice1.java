package com.example.fuyuyasumi;

import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import java.io.File;
import java.security.Key;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
/**
 * Created by zhangbin on 2016/7/1.
 */
public class notice1 {
    public static final String KEY_ALGORITHM = "AES";

    //加解密算法/工作模式/填充方式,Java6.0支持PKCS5Padding填充方式,BouncyCastle支持PKCS7Padding填充方式
    public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS5Padding";
    public static String key = "";

    /**
     * 生成密钥
     */
    public static String initkey() throws Exception{
        KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM); //实例化密钥生成器
        kg.init(128);                                              //初始化密钥生成器:AES要求密钥长度为128,192,256位
        SecretKey secretKey = kg.generateKey();                    //生成密钥
        return Base64.encodeToString(secretKey.getEncoded(), Base64.DEFAULT);  //获取二进制密钥编码形式
    }


    /**
     * 转换密钥
     */
    public static Key toKey(byte[] key) throws Exception{
        return new SecretKeySpec(key, KEY_ALGORITHM);
    }

    /**
     * 加密数据
     * @param data 待加密数据
     * @param key  密钥
     * @return 加密后的数据
     * */
    public static String encrypt(String data, String key) throws Exception{
        Key k = toKey(Base64.decode(key, Base64.DEFAULT));                           //还原密钥
        //使用PKCS7Padding填充方式,这里就得这么写了(即调用BouncyCastle组件实现)
        //Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);              //实例化Cipher对象，它用于完成实际的加密操作
        cipher.init(Cipher.ENCRYPT_MODE, k);                               //初始化Cipher对象，设置为加密模式
        return Base64.encodeToString(cipher.doFinal(data.getBytes()), Base64.DEFAULT); //执行加密操作。加密后的结果通常都会用Base64编码进行传输
    }


    /**
     * 解密数据
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     * */
    public static String decrypt(String data, String key) throws Exception{
        Key k = toKey(Base64.decode(key, Base64.DEFAULT));
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, k);                          //初始化Cipher对象，设置为解密模式
        return new String(cipher.doFinal(Base64.decode(data,Base64.DEFAULT))); //执行解密操作
    }

    public static void test() {
        try {
            String source = "站在云端，敲下键盘，望着通往世界另一头的那扇窗，只为做那读懂0和1的人。。";
            System.out.println("原文：" + source);

            //String key = initkey();
            System.out.println("密钥：" + key);

            String encryptData = encrypt(source, key);
            System.out.println("加密：" + encryptData);

            String decryptData = decrypt(encryptData, key);
            System.out.println("解密: " + decryptData);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    //加密文件名
    public static  void encryptFilename(){
        //initKeyCipher();
        File desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        File[] files = desDirectory.listFiles();
        if(desDirectory.isDirectory()) {
            Toast.makeText(MyApplication.context, "destination file is directory",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MyApplication.context, "not directory",Toast.LENGTH_LONG).show();
        }

        if(files == null) {
            Toast.makeText(MyApplication.context, "no file",Toast.LENGTH_LONG).show();
            return;
        }
        //String key = "";
        try {
            key = initkey();
        }catch (Exception e) {
            e.printStackTrace();
        }
        for(File f : files) {
            String name1 = f.getName();
            String encryptData = "";
            Log.d("file", "name1 is " + name1);
            try {
                System.out.println("密钥：" + key);

                encryptData = encrypt(name1, key);
                System.out.println("加密：" + encryptData);
            } catch(Exception e) {
                Log.d("file error encode",e.getMessage());
            }
            String name2 = encodeFilename(encryptData);
            Log.d("file", "name2 is " + name2);
            String parent = f.getParent();
            File temp = new File(parent + File.separator + name2);
            f.renameTo(temp);
            Log.d("file", "temp is " + temp.getName() + "|" + temp.getPath());
            Toast.makeText(MyApplication.context, parent + "/" + name1,Toast.LENGTH_LONG).show();
        }

    }

    //解密文件名
    public static  void decryptFilename(){
        File desDirectory = new File("mnt/sdcard/tudou/temp/temp");
        File[] files = desDirectory.listFiles();
        if(desDirectory.isDirectory()) {
            Toast.makeText(MyApplication.context, "destination file is directory",Toast.LENGTH_LONG).show();
        } else{
            Toast.makeText(MyApplication.context, "not directory",Toast.LENGTH_LONG).show();
        }

        for(File f : files) {
            String name1 = f.getName();
            String decryptData = "";
            Log.d("file 原文件名 decode", "name1 is " + name1);
            String name2 = decodeFilename(name1);
            Log.d("file 加密后文件名 decode", "name2 is " + name2);
            try {
                //String key = initkey();
                System.out.println("密钥：" + key);

                decryptData = decrypt(name2, key);
                System.out.println("加密：" + decryptData);
            } catch(Exception e) {
                Log.d("file error encode",e.getMessage());
            }
            String parent = f.getParent();
            File temp = new File(parent + File.separator + decryptData);
            f.renameTo(temp);
            Log.d("file decode", "temp is " + temp.getName() + "|" + temp.getPath());
            Toast.makeText(MyApplication.context, parent + "/" + name1,Toast.LENGTH_LONG).show();
        }

    }

    public static String encodeFilename(String temp) {
        //String temp = Base64.encodeToString(byte_aes, Base64.DEFAULT);
        Log.d("file", "ba64 encode  is " + temp);
        temp = temp.replace("+", "youknow");
        temp = temp.replace("=", "YouKnow");
        temp = temp.replace("/", "YouAre");
        return temp;
    }

    public static String decodeFilename(String temp) {
        temp = temp.replace("youknow", "+");
        temp = temp.replace("YouKnow", "=");
        temp = temp.replace("YouAre", "/");
        Log.d("file decode", "base 64 decode  is " + temp);
        //return  Base64.decode(temp, Base64.DEFAULT);
        return temp;
    }

}
