package com.aucloud.aupay.encryption;


import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Random;

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

    public static String decryptByDES(String source,String key){
        String decodeStr = "";
        try{
            DESKeySpec deskey = new DESKeySpec(key.getBytes());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey secretKey = keyFactory.generateSecret(deskey);
            Cipher cipher = Cipher.getInstance("DES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用同一个key
            byte[] decode = Base64.getMimeDecoder().decode(source);
            byte[] result = cipher.doFinal(decode);
            decodeStr = new String(result);
        }catch (Exception e){
            e.printStackTrace();
        }
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
            encodeStr = byteToHex(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return encodeStr;

    }

    public static String byteToHex(byte[] bytes) {
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

    public static byte[] hexToBytes(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String sha256_HMAC(String message, String secret) {
        String hash = "";
        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            byte[] bytes = sha256_HMAC.doFinal(message.getBytes());
            hash = byteToHex(bytes);
        } catch (Exception e) {
            System.out.println("Error HmacSHA256 ===========" + e.getMessage());
        }
        return hash;
    }

    public static String getURLEncoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String URLDecoderString(String str) {
        String result = "";
        if (null == str) {
            return "";
        }
        try {
            result = java.net.URLDecoder.decode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }
    public static String getSaltMD5(String password) {
        // 生成一个16位的随机数
        Random random = new Random();
        StringBuilder sBuilder = new StringBuilder(16);
        sBuilder.append(random.nextInt(99999999)).append(random.nextInt(99999999));
        int len = sBuilder.length();
        if (len < 16) {
            for (int i = 0; i < 16 - len; i++) {
                sBuilder.append("0");
            }
        }
        // 生成最终的加密盐
        String salt = sBuilder.toString();
        password = encryptByMD5(password + salt);
        char[] cs = new char[48];
        for (int i = 0; i < 48; i += 3) {
            cs[i] = password.charAt(i / 3 * 2);
            char c = salt.charAt(i / 3);
            cs[i + 1] = c;
            cs[i + 2] = password.charAt(i / 3 * 2 + 1);
        }
        return String.valueOf(cs);
    }
    public static boolean getSaltverifyMD5(String password, String md5str) {
        char[] cs1 = new char[32];
        char[] cs2 = new char[16];
        for (int i = 0; i < 48; i += 3) {
            cs1[i / 3 * 2] = md5str.charAt(i);
            cs1[i / 3 * 2 + 1] = md5str.charAt(i + 2);
            cs2[i / 3] = md5str.charAt(i + 1);
        }
        String salt = new String(cs2);
        return encryptByMD5(password + salt).equals(String.valueOf(cs1));
    }

    public static String encodeChinese(String text, String secret) {
        String str = null;

        return str;
    }

    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(getSaltMD5("1234"));
//        System.out.println(encryptByBase64("123456"));
//        System.out.println(encryptByDES("123456","86367INTHF"));
//        System.out.println(decryptByDES("kV/QbIvGuzU=","86367INTHF"));
//        System.out.println(getSaltverifyMD5("1qazxs","337c3e09cb70e7a25d014c66f52f18c9327a21c696e21a0c"));



    }


}
