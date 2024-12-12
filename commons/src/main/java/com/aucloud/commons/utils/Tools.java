package com.aucloud.commons.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Tools {

    public static String generateRandomString(int length) {
        return RandomStringUtils.secureStrong().nextAlphanumeric(length);
    }

    public static String hexToAddress(String strHex) {
        if (strHex.length() > 42) {
            if (strHex.charAt(0) == '0' && (strHex.charAt(1) == 'X' || strHex.charAt(1) == 'x')) {
                strHex = strHex.substring(2);
            }
            strHex = strHex.substring(24);
            return "0x" + strHex;
        }
        return null;
    }
}
