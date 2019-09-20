package com.tangzy.tzymvp.util;


import android.util.Base64;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

/**
 * RsaUtils class
 *
 * @author Administrator
 * @date 2019/9/20
 */
public class RsaUtils {
    private static Map<Integer, String> keyMap = new HashMap<Integer, String>();  //用于封装随机产生的公钥与私钥

    public static byte[] getA( String publicKey){

        byte[] decode = Base64.decode(publicKey, Base64.DEFAULT);
//        return Base64.encodeToString(publicKey, Base64.DEFAULT);

        Logger.d("tangzy1", Base64.encodeToString(decode, Base64.DEFAULT));
        return decode;
    }

    public static void main() {
        try {
            //生成公钥和私钥
            genKeyPair();
            //加密字符串
            String message = "df723820";

            Logger.d("tangzy","随机生成的公钥为:" + keyMap.get(0));
            Logger.d("tangzy","随机生成的私钥为:" + keyMap.get(1));
            String messageEn = encrypt(message, keyMap.get(0));
            Logger.d("tangzy",message + "\t加密后的字符串为:" + messageEn);
            String messageDe = decrypt(messageEn, keyMap.get(1));
            Logger.d("tangzy","还原后的字符串为:" + messageDe);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 随机生成密钥对
     *
     * @throws NoSuchAlgorithmException
     */
    public static void genKeyPair() throws NoSuchAlgorithmException {
        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");
        // 初始化密钥对生成器，密钥大小为96-1024位
        keyPairGen.initialize(1024, new SecureRandom());
        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥
//        String publicKeyString = new String(Base64.encodeBase64(publicKey.getEncoded()));
        String publicKeyString = Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
        // 得到私钥字符串
        String privateKeyString = Base64.encodeToString((privateKey.getEncoded()), Base64.DEFAULT);
        // 将公钥和私钥保存到Map
        keyMap.put(0, publicKeyString);  //0表示公钥
        keyMap.put(1, privateKeyString);  //1表示私钥
    }

    /**
     * RSA公钥加密
     *
     * @param str       加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt(String str, String publicKey) throws Exception {
        //base64编码的公钥
//        byte[] decoded = Base64.decodeBase64(publicKey);
        byte[] decoded = Base64.decode(publicKey, Base64.DEFAULT);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
//        String outStr = Base64.encodeBase64String(cipher.doFinal(str.getBytes("UTF-8")));
        String outStr = Base64.encodeToString(cipher.doFinal(str.getBytes("UTF-8")), Base64.DEFAULT);
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param str        加密字符串
     * @param privateKey 私钥
     * @return 铭文
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt(String str, String privateKey) throws Exception {
        //64位解码加密后的字符串
//        byte[] inputByte = Base64.decodeBase64(str.getBytes("UTF-8"));
        byte[] inputByte = Base64.decode(str.getBytes("UTF-8"), Base64.DEFAULT);
        //base64编码的私钥
//        byte[] decoded = Base64.decodeBase64(privateKey);
        byte[] decoded = Base64.decode(privateKey, Base64.DEFAULT);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        String outStr = new String(cipher.doFinal(inputByte));
        return outStr;
    }
}
