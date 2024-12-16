package com.aucloud.aupay.security;


import com.aucloud.aupay.encryption.MD5;
import com.aucloud.aupay.security.entity.DES;
import org.apache.http.NameValuePair;

import javax.crypto.*;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.Comparator;
import java.util.List;

/**
 * 加密算法
 */
public class Encryption {
    /**
     * Base64加密
     */
    public static String encryptByBase64(String src) {
        String encode = Base64.getEncoder().encodeToString(src.getBytes());
        return encode;
    }

    /**
     * Base64解密
     */
    public static String decryptByBase64(String text) {
        byte[] decode = Base64.getDecoder().decode(text);
        String decodeStr = null;
        try {
            decodeStr = new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeStr;
    }

    /**
     * Base64Url加密
     *
     * @param src
     * @return
     */
    public static String encryptByBase64Url(String src) {
        String encode = Base64.getUrlEncoder().encodeToString(src.getBytes());
        return encode;
    }

    /**
     * Base64Url解密
     */
    public static String decryptByBase64Url(String text) {
        byte[] decode = Base64.getUrlDecoder().decode(text);
        String decodeStr = null;
        try {
            decodeStr = new String(decode, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return decodeStr;
    }

    /**
     * DES加密
     */
    public static DES encryptByDES(String src) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, NoSuchPaddingException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance("DES");
        keyGenerator.init(56);//设置长度
        SecretKey secretKey = keyGenerator.generateKey();
        byte[] keyBytes = secretKey.getEncoded();

        DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey generateSecret = secretKeyFactory.generateSecret(desKeySpec);

        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, generateSecret);
        byte[] result = cipher.doFinal(src.getBytes());
        String encode = Base64.getEncoder().encodeToString(result);
        DES des = new DES();
        des.setEncode(encode);
        des.setKey(generateSecret);
        des.setSrc(src);

        return des;
    }

    /**
     * DES解密
     */
    public static String decryptByDES(SecretKey key, String encode) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException {
        Cipher cipher = Cipher.getInstance("DES/ECB/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key);//使用同一个key
        byte[] result = cipher.doFinal(Base64.getDecoder().decode(encode));
        String decodeStr = new String(result);
        return decodeStr;
    }

    /**
     * MD5
     */
    public static String encryptByMD5(String plainText) {
        byte[] secretBytes = null;
        try {
            secretBytes = MessageDigest.getInstance("md5").digest(
                    plainText.getBytes());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("没有这个md5算法！");
        }
        String md5code = new BigInteger(1, secretBytes).toString(16);
        for (int i = 0; i < 32 - md5code.length(); i++) {
            md5code = "0" + md5code;
        }
        return md5code;
    }

    /**
     * SHA256
     */
    public static String encryptBySHA256(String src) {
        MessageDigest messageDigest;
        String encodeStr = "";
        try {
            messageDigest = MessageDigest.getInstance("SHA-256");
            messageDigest.update(src.getBytes("UTF-8"));
            encodeStr = byte2Hex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;

    }

    private static String byte2Hex(byte[] bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        String temp = null;
        for (int i = 0; i < bytes.length; i++) {
            temp = Integer.toHexString(bytes[i] & 0xFF);
            if (temp.length() == 1) {
                //1得到一位的进行补0操作
                stringBuffer.append("0");
            }
            stringBuffer.append(temp);
        }
        return stringBuffer.toString();
    }

    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byte2Hex(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String paymentSign(String source, String secret) {
        String sign = "";
        try {

            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(source.getBytes());
            sign = Base64.getEncoder().encodeToString(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return sign;
    }



    public static String getQuerySign(List<NameValuePair> list, String key){
        String sign = "";
        list.sort(new Comparator<NameValuePair>() {
            @Override
            public int compare(NameValuePair o1, NameValuePair o2) {
                return (o1.getName()).compareTo(o2.getName());
            }
        });
        for(NameValuePair nameValuePair:list){
            sign=sign+nameValuePair.getName()+"="+nameValuePair.getValue()+"&";
        }
        sign = sign + "key=" + key;
        return MD5.md5(sign);
    }

}
