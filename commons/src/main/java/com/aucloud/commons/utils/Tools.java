package com.aucloud.commons.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class Tools {

    public static String generateRandomString(int length) {
        return RandomStringUtils.secureStrong().nextAlphanumeric(length);
    }
}
