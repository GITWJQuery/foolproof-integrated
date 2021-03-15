package com.select.integrated.utils;

import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * RSA工具类
 */
public class RSAUtil {

    /**
     * 数字签名，密钥算法
     */
    private static final String RSA_KEY_ALGORITHM = "RSA";

    /**
     * 数字签名签名/验证算法
     */
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    /**
     * RSA密钥长度
     */
    private static final int KEY_SIZE = 2048;

    /**
     * 私钥
     */
    private static final String PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCSD7eINa0EORZ95T0BbL+6GhyySeNyFP5COPURV+hf58hDWjybNdSbhqH3F9Pi+5aQDPLLbjX5daYBouvPQiIoMqDL0zAAiVA32kdhz1mfLffSwSYCH2tYIUXMZj2iDg67jQhMJi+9ACYLXCk0SGnY0u1Lyeo7EP8AXSgxQ04sEXiGUnR+2M31unuXlFTk0zoFn5dLk+wZQPAUUzaCiNmgoL+nAPOZjjuzgxRn7VNXZPS4Y8X6hA4cgSNDgIxyspv2iWvoz39689oAk44LzhnCk93plEzJddtBmbVsaka+P7RXJPHElLwP5348CUIhjygBnO6AtLBnB5Wi7tZnU6eXAgMBAAECggEAM4XTyfqVM54nrvVEVdJUt4MqiEHEN6PsJNQOrrWBxQLwA8I7Cs0sWEhfrdf76F+zNtE6fEQ+SecUThN4dz3u6pshWYT/s9C8OVeIQppwFNSUTQXRMIKAzS+/fWC7cQTgcYxIVwRJZrQ8t1klN8orfE67Yjaxp3BRY4F5Ahep5aUjLTixscC3PH28EMCUXDzqDQBB+GCdp7cDTy7NhH56fkpATnHMxOgR41qdYVySQGn//eio+iMvGL7FRHzhMP8C+uLKMyKF3FwLs7JkmSK8wH4skm1RqTe+NQIdfdQsX0KiaeMc2a9WDFdiF1XnJ5ZP6xqRJktkVH0sAsRZclq6UQKBgQDRuxca43LvMTX1UdycAlfRF05ijhLCLO1FMdJGZxEW7iPgYJH6o9/9QsOehZjVQnujW0y9RS1AvRw8PbkV02Mj2CBLfPi+w/q1EDNl1PLce/N8YSXde+79Mi8KZh6Kyfn1lyl0ZdJrBqr+cwjd1LBx2fVVbMfg5QxD8DOXFBTJEwKBgQCySMk3ExO8vHIA1G1n58LajBc8ZWsgfBluWqxiEAo1lq9LepbNTLXD4kqPJ4TBcVQ9f6Ho2g6zBKiKO9xwu4YVpz0m//OfLI3RbK/iR0wJWV/8tkcC/zLY3pQT78xGSspjebE4yx+ai934TXEuqlCqIqqKbdCQ4GxPEDYB1i2b7QKBgAI8p5tuz5wDf1rVe6/iSoN9ZyWf9E1D42f4hxCfKsx+z70l6ois1MfDvYhkTmP/6IeugpwGL9xf5/wwgFfeQFFyja2gcJrARl1GBfZRFiIJ5GfwNVIubzceIOhQfITocJUQ/q0yJNNlZ8989JxBF3yvYXF+zob0inPzDOnQmgQnAoGAQC8oEaXSkz4cpOPVR3UOl3Ob4eh5OsayuSH3sY4ovsYy8a9+nwag4ZaUVRlbF/Fe7I0IkzX92MEylCGc6RD54s3EnEfFQ2bc36qLX6FfZHZCsk5FHnUVrzHiiZLpLd07I7jp7pJiHFeCYCQxPSWfyMCWOSPz1FSXaZdieiLLZLkCgYBBa2C2+ByaW5xsjEzxISw+C24aeJywiVaiihHhinNLOBaqQZ4ADZrciPp5l5CNCPMKSHNWVJdnU+oOak1fkwn9WpfRgRWctX24S9DyimBuwFZg7eTgtEPJKMfrBdKbKn/x/TA+nZFX0EzaxiUY0se85xBSbyqE0pjtEK1wgDJE2A==";

    /**
     * 随机KEY
     */
    private static final String SECRAND_KEY = "*C*E*L*S*-*U*U*A*S*";

    /**
     * 初始化RSA密钥对
     */
    private static Map<String, String> initKey() throws Exception {
        KeyPairGenerator keygen = KeyPairGenerator.getInstance(RSA_KEY_ALGORITHM);
        SecureRandom secrand = new SecureRandom();
        // 初始化随机产生器
        secrand.setSeed(SECRAND_KEY.getBytes());
        // 初始化密钥生成器
        keygen.initialize(KEY_SIZE, secrand);
        KeyPair keys = keygen.genKeyPair();
        String pub_key = Base64.encodeBase64String(keys.getPublic().getEncoded());
        String pri_key = Base64.encodeBase64String(keys.getPrivate().getEncoded());
        Map<String, String> keyMap = new HashMap<>(2);
        keyMap.put("publicKey", pub_key);
        keyMap.put("privateKey", pri_key);
        return keyMap;
    }

    /**
     * 数字签名
     */
    public static String sign(byte[] data, String pri_key) throws Exception {
        // 取得私钥
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key_bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 生成私钥
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initSign(priKey);
        // 更新
        signature.update(data);

        return Base64.encodeBase64String(signature.sign());
    }

    /**
     * RSA校验数字签名
     */
    public static boolean verify(byte[] data, byte[] sign, String pub_key) throws Exception {
        // 转换公钥材料
        // 实例化密钥工厂
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        // 初始化公钥
        // 密钥材料转换
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key_bytes);
        // 产生公钥
        PublicKey pubKey = keyFactory.generatePublic(x509KeySpec);
        // 实例化Signature
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        // 初始化Signature
        signature.initVerify(pubKey);
        // 更新
        signature.update(data);
        // 验证
        return signature.verify(sign);
    }

    /**
     * 公钥加密
     */
    private static byte[] encryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥加密
     */
    public static String encryptByPubKey(String data, String pub_key) throws Exception {
        // 私匙加密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] enSign = encryptByPubKey(data.getBytes(), pub_key_bytes);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 私钥加密
     */
    private static byte[] encryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥加密
     */
    public static String encryptByPriKey(String data, String pri_key) throws Exception {
        // 私匙加密
        byte[] pri_key_bytes = Base64.decodeBase64(pri_key);
        byte[] enSign = encryptByPriKey(data.getBytes(), pri_key_bytes);
        return Base64.encodeBase64String(enSign);
    }

    /**
     * 公钥解密
     */
    private static byte[] decryptByPubKey(byte[] data, byte[] pub_key) throws Exception {
        // 取得公钥
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(pub_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(x509KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        return cipher.doFinal(data);
    }

    /**
     * 公钥解密
     */
    public static String decryptByPubKey(String data, String pub_key) throws Exception {
        // 公匙解密
        byte[] pub_key_bytes = Base64.decodeBase64(pub_key);
        byte[] design = decryptByPubKey(Base64.decodeBase64(data), pub_key_bytes);
        return new String(design);
    }

    /**
     * 私钥解密
     */
    private static byte[] decryptByPriKey(byte[] data, byte[] pri_key) throws Exception {
        // 取得私钥
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(pri_key);
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_KEY_ALGORITHM);
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return cipher.doFinal(data);
    }

    /**
     * 私钥解密
     */
    public static String decryptByPriKey(String data) throws Exception {
        // 私匙解密
        byte[] pri_key_bytes = Base64.decodeBase64(PRIVATE_KEY);
        byte[] design = decryptByPriKey(Base64.decodeBase64(data), pri_key_bytes);
        return new String(design);
    }
//
//    public static void main(String[] args) throws Exception {
//        long time = System.currentTimeMillis();
//
//
//        // 私匙解密
//        String priKeyStr = RSAUtils.decryptByPriKey("jyFeGPJoA8pZ4qzfGCM3dsZjvSSsTwpxk0Rtide1uI0bZ9SK0YcSogaOD4kO6Se+1rAjebKpEG1/CC8+xmbZofDiF8fql+z6Nxvu4T4cgdayJAFAk0BZwvs5Dq2is6PcJHGsLOBRIa3wECLQRpW/hlaA3vcpVicNVD6XtpR2wNwi7zQjgVI5KVu3BWfIA84DAHcxfu8H7/YhGFGxmCLbpnWnfUuZPCV/GYS1pLq+6NsDTNcycXKbi+kGShxlY92uWu5xjqnv51qTCjYvqKIJz/xmkLKcjiCO/D8G2Hs7BWUcnVUxYTSsO71qhHSFVMa2fRjTVktJSr18tJltc7cHuA==");
//        System.out.println("私匙解密结果：\n" + priKeyStr);
//        System.out.println("私匙解密时间：\n" + (System.currentTimeMillis() - time));
//    }
}
