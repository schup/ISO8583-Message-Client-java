package com.imohsenb.iso8583.client;

/**
 * @author Mohsen Beiranvand
 */
public class ISOClientException extends Exception {

    public ISOClientException(String message) {
        super(message);
    }

    public ISOClientException(Exception e) {
        super(e);
    }
}
