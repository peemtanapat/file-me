package com.peemtanapat.fileme.fileservice;

public final class Constant {

    public final static String SEND_MAIL_URL = "http://localhost:8081/mail";

    public final static String UPLOAD_SUCCESS_SUBJECT = "file-me: Result of File Uploading (Success)";
    public final static String UPLOAD_SUCCESS_BODY = "You have successfully uploaded file " +
            "'{{filename}}' " +
            "({{size}} KB) " +
            "at {{time}}";

    public final static String INVALID_TOKEN_MSG = "invalid token";

    // TODO: mock
    public final static String OWNER1 = "pm.tanapat@gmail.com";
    public final static String OWNER2 = "tanapat.pm@gmail.com";
    public final static String OWNER3 = "pronsawan.dp@gmail.com";
    public final static String CURRENT_OWNER = OWNER2;

}