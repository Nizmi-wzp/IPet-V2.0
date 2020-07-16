package com.wzp.pet.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;
//DES对称加密
public class DESUtil {
    private static Key key;
    private static String KEY_STR="myKey";
    private static String CHARSETNAME="UTF-8";
    private static String ALGORITHM="DES";

    static{
        try{
            KeyGenerator generator=KeyGenerator.getInstance(ALGORITHM);
           /* SecureRandom secureRandom=SecureRandom.getInstance("SHAIPRNG");*/
            SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");//注意sha1prng
            // 设置上密钥种子
            secureRandom.setSeed(KEY_STR.getBytes());
            generator.init(secureRandom);
            key=generator.generateKey();
            generator=null;

        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }
    //获取加密后信息
    public static String getEncryptString(String str){
        BASE64Encoder base64Encoder=new BASE64Encoder();
        try{
            byte[] bytes=str.getBytes(CHARSETNAME);
            Cipher cipher=Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] doFinal=cipher.doFinal(bytes);
            return base64Encoder.encode(doFinal);
        }catch(Exception e){
            throw new RuntimeException(e);
        }
    }

    public static String getDecryptString(String str) {
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try {
            byte[] bytes = base64Decoder.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] doFinal = cipher.doFinal(bytes);
            return new String(doFinal, CHARSETNAME);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args){
        //存在问题加密固定算法,易破解
        System.out.println(getEncryptString("root"));
        System.out.println(getDecryptString("WnplV/ietfQ="));

        System.out.println(getEncryptString("123456"));
        System.out.println(getDecryptString("QAHlVoUc49w="));
        //System.out.println(getDecryptString());
    }
}
