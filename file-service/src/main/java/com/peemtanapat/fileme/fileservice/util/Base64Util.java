package com.peemtanapat.fileme.fileservice.util;

import java.util.Base64;

public class Base64Util {

    public String encode(String text) {
        String encodedString = Base64.getEncoder().encodeToString(text.getBytes());
        return encodedString;
    }

    public String decode(String encodedString) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedString);
        return new String(decodedBytes);
    }
}
