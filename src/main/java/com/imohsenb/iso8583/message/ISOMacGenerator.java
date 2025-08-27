package com.imohsenb.iso8583.message;

/**
 * ISOMacGenerator
 *
 * @author Mohsen Beiranvand
 */
public abstract class ISOMacGenerator {

    public abstract byte[] generate(byte[] data);

}