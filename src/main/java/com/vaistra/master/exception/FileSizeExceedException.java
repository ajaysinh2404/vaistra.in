package com.vaistra.master.exception;

public class FileSizeExceedException extends RuntimeException{
    public FileSizeExceedException(String msg){
        super(msg);
    }
}
