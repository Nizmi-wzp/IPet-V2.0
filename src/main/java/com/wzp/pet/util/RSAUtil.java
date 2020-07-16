package com.wzp.pet.util;
/*非对称加密算法只适用于少量数据，安全性更高，效率低
输入：必须 比 密钥长度 短至少11个字节, 也就是　KEYSIZE/8 – 11，如果输入的明文过长，必须切割，然后填充
输出：和密钥长度一样长
 */
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class RSAUtil{

    private RSAUtil() {

    }

    /**
     * 日志对象
     */
    private static Logger LOG = LoggerFactory.getLogger(RSAUtil.class);

    /**
     * 密钥长度(bit)
     */
    private static final int KEYSIZE = 1024;

    /**
     * CIPHER_ALGORITHM RSA
     */
    private static final String CIPHER_ALGORITHM_RSA = "RSA/ECB/PKCS1Padding";

    /**
     * RSA加密
     */
    public static final String KEY_ALGORITHM_RSA = "RSA";

    /**
     * RSAPublicKey
     */
    private static final String PUBLIC_KEY = "RSAPublicKey";

    /**
     * RSAPrivateKey
     */
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    /**
     * 获取配对的公钥和私钥
     * @return keyMap
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:29:51
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM_RSA);
        keyPairGen.initialize(KEYSIZE);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        Map<String, Object> keyMap = new HashMap<String, Object>(2);
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 对公钥进行编码转换，并进行BASE64转码
     * @param keyMap 配对的公钥和私钥组成的map
     * @return 处理后的公钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:33:03
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * 对私钥进行编码转换，并进行BASE64转码
     * @param keyMap 配对的公钥和私钥组成的map
     * @return 处理后的私钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:34:39
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);
        return encryptBASE64(key.getEncoded());
    }

    /**
     * RSA加密
     * @param publicKeyStr 公钥
     * @param plainText 需要加密的数组
     * @return 加密后数组
     * @author sucb
     * @date 2017年3月29日下午2:45:26
     */
    public static byte[] encodeRSA(String publicKeyStr, byte[] plainText) {
        try {
            int i = plainText.length/(KEYSIZE/8-11) +1; //加密数组需要切割的段数
            //方式一、补位，保证下面循环中切割时不会出现数组越界
//            byte[] newPlainText = new byte[i * KEYSIZE/8-11];
//            System.arraycopy(plainText, 0, newPlainText, 0, plainText.length);

            byte[] resultByte = new byte[i * KEYSIZE/8];//用于接收加密后数组
            for (int j = 0; j < i; j++) {
                byte[] adata = new byte[KEYSIZE/8-11]; //需要加密的每段数据

                //方式一、补位的情况
//                if(plainText.length < adata.length) {//数据不需要切割的情况
//                    System.arraycopy(newPlainText, j * adata.length, adata, 0, newPlainText.length);
//                }else {
//                    System.arraycopy(newPlainText, j * adata.length, adata, 0, adata.length);
//                }

                //方式二、不补位的情况
                if(plainText.length < adata.length) {//数据不需要切割的情况
                    System.arraycopy(plainText, j * adata.length, adata, 0, plainText.length);
                }else {
                    if(j<i-1) {
                        System.arraycopy(plainText, j * adata.length, adata, 0, adata.length);
                    }else{//不补位的话，最后一段需要加密的数组长度小于KEYSIZE/8-11
                        System.arraycopy(plainText, j * adata.length, adata, 0, plainText.length- j * adata.length);
                    }
                }
                RSAPublicKey key = restorePublicKey(publicKeyStr);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_RSA);
                cipher.init(Cipher.ENCRYPT_MODE, key);
                byte[] cipherText = cipher.doFinal(adata);
                //数据拼接
                System.arraycopy(cipherText, 0, resultByte, j * cipherText.length, cipherText.length);
            }
            return resultByte;
        } catch (Exception e) {
            LOG.error("RSA加密异常.publicKeyStr=({}), plainText=({}), 异常信息=({}).", publicKeyStr, plainText,
                    e.getMessage());
            return null;
        }
    }

    /**
     * RSA解密
     * @param privateKeyStr 私钥
     * @param plainText 需要解密的数组
     * @return 解密后数组
     * @author sucb
     * @date 2017年3月29日下午2:46:14
     */
    public static byte[] decodeRSA(String privateKeyStr, byte[] plainText,int m) {
        int i = plainText.length/(KEYSIZE/8);
        byte[] decodeByte = new byte[i * plainText.length ];
        try {
            byte[] cipherText = new byte[KEYSIZE/8];//需要解密的每段数据（也就是之前每段加密后得到的数组）
            for (int j = 0; j < i; j++) {
                System.arraycopy(plainText, j * cipherText.length, cipherText, 0, cipherText.length);
                RSAPrivateKey key = restorePrivateKey(privateKeyStr);
                Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_RSA);
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] newPlainText = cipher.doFinal(cipherText);
                System.arraycopy(newPlainText, 0, decodeByte, j * newPlainText.length, newPlainText.length);
            }
            byte[] resultByte = new byte[m];
            System.arraycopy(decodeByte, 0, resultByte, 0, resultByte.length);
            return resultByte;
        } catch (Exception e) {
            LOG.error("RSA解密异常.privateKeyStr=({}), plainText=({}), 异常信息=({}).", privateKeyStr, plainText,
                    e.getMessage());
            return null;
        }
    }

    /**
     * 还原公钥，X509EncodedKeySpec 用于构建公钥的规范
     * @param publicKeyStr 公钥
     * @return 还原后的公钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:47:54
     */
    private static RSAPublicKey restorePublicKey(String publicKeyStr) throws Exception {
        byte[] keyBytes = decryptBASE64(publicKeyStr);
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        RSAPublicKey publicKey = (RSAPublicKey) factory.generatePublic(x509EncodedKeySpec);
        return publicKey;
    }

    /**
     * 还原私钥 PKCS8EncodedKeySpec
     * @param privateKeyStr 私钥
     * @return 还原后的私钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:47:37
     */
    private static RSAPrivateKey restorePrivateKey(String privateKeyStr) throws Exception {
        byte[] keyBytes = decryptBASE64(privateKeyStr);
        PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory factory = KeyFactory.getInstance(KEY_ALGORITHM_RSA);
        RSAPrivateKey privateKey = (RSAPrivateKey) factory.generatePrivate(priPKCS8);
        return privateKey;
    }

    /**
     * 进行BASE64转码
     * @param key byte型密钥
     * @return 转码后的String型密钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:49:58
     */
    private static String encryptBASE64(byte[] key) throws Exception {
        return Base64.encodeBase64String(key);
    }

    /**
     * 进行BASE64解码
     * @param key String型密钥
     * @return 解码后的byte型密钥
     * @throws Exception 异常
     * @author sucb
     * @date 2017年3月29日下午2:52:29
     */
    private static byte[] decryptBASE64(String key) throws Exception {
        return Base64.decodeBase64(key);
    }

    public static void main(String[] args) throws Exception {

        Map<String, Object> keyMap = initKey();
        String publicKey = getPublicKey(keyMap);
        String privateKey = getPrivateKey(keyMap);

        String a = "aaaaaaaaaaaa";
        byte[] miByte = encodeRSA(publicKey, a.getBytes());
        byte[] mingByte = decodeRSA(privateKey, miByte, a.getBytes().length);
        String actual = new String(mingByte);
        System.out.println("actual : " + actual);
        System.out.println(a.equals(actual));

        String message = "然而，这几乎是个不可能完成的任务。当p和q是非常大的质数时，根据pq的乘积去分解因子p和q，这是数学上公认的难题。通常，p和q都会选的非常大，比如说200位。这导致n也非常大，有400位。寻找一个400位数字的质数分解并不容易，我们要做的除法运算次数大约为10 199！世界最强的超级计算机天河2号每秒浮点运算是1016级别。那么，分解出p和q，大约需要10174年。10174就是1的后面跟上174个0，时间是不是很长？";
        byte[] miByte2 = encodeRSA(publicKey, message.getBytes());
        byte[] mingByte2 = decodeRSA(privateKey, miByte2, message.getBytes().length);
        String actual2 = new String(mingByte2);
        System.out.println("actual2 : " + actual2);
        System.out.println(message.equals(actual2));
    }

}

