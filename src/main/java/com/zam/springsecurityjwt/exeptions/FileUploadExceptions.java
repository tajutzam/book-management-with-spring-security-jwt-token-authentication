package com.zam.springsecurityjwt.exeptions;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;

public class FileUploadExceptions extends FileSizeLimitExceededException {

    public FileUploadExceptions(String message, long actual, long permitted) {
        super(message, actual, permitted);
    }
}
