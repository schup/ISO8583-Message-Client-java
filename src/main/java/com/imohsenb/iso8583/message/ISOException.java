package com.imohsenb.iso8583.message;

/**
 * @author Mohsen Beiranvand
 */
public class ISOException extends Exception {

    public ISOException(String message) {
        super(message);
    }

    public ISOException(String message, Throwable cause) {
        super(message, cause);
    }
}
