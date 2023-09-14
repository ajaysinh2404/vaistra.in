package com.vaistra.master.exception;

public class DuplicateEntryException extends RuntimeException {
    public DuplicateEntryException(String msg) {
        super(msg);
    }
}
