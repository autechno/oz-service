package com.aucloud.utils;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.UUID;

public class Tools {

    public static String generateRandomString(int length) {
        return RandomStringUtils.secureStrong().nextAlphanumeric(length);
    }
}
