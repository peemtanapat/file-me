package com.peemtanapat.fileme.filemeapigateway.config;

import feign.Logger;

public class CustomLogger extends Logger {
    @Override
    protected void log(String configKey, String format, Object... objects) {
        String message = String.format(configKey + " - " + format, objects);
        System.out.println(message);
    }
}
