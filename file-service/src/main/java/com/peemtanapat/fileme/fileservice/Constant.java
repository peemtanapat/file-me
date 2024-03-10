package com.peemtanapat.fileme.fileservice;

public final class Constant {

        public final static String UPLOAD_SUCCESS_SUBJECT = "file-me: Result of File Uploading (Success)";
        public final static String UPLOAD_FAIL_SUBJECT = "file-me: Result of File Uploading (Fail)";
        public final static String UPLOAD_SUCCESS_BODY = "You have successfully uploaded file " +
                        "'{{filename}}' " +
                        "({{size}} KB) " +
                        "at {{time}}";
        public final static String UPLOAD_FAIL_BODY = "You have unsuccessfully uploaded file " +
                        "'{{filename}}' " +
                        "({{size}} KB) " +
                        "at {{time}}";

        public final static String INVALID_TOKEN_MSG = "invalid token";

}