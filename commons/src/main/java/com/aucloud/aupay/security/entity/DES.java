package com.aucloud.aupay.security.entity;

import javax.crypto.SecretKey;

public class DES {

    private SecretKey key;
    private String encode;
    private String src;

    public SecretKey getKey() {
        return key;
    }

    public void setKey(SecretKey key) {
        this.key = key;
    }

    public String getEncode() {
        return encode;
    }

    public void setEncode(String encode) {
        this.encode = encode;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    public String toString() {
        return "DES{" +
                "key=" + key +
                ", encode='" + encode + '\'' +
                ", src='" + src + '\'' +
                '}';
    }
}
