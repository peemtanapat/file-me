package com.peemtanapat.fileme.fileservice.util;

public class JWTUtil {

    public static String cleanBearer(String originalToken) {
        return originalToken.replace("Bearer ", "");
    }
}
