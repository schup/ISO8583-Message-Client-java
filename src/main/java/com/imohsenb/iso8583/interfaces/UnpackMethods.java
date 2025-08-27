package com.imohsenb.iso8583.interfaces;

import com.imohsenb.iso8583.message.ISOException;
import com.imohsenb.iso8583.message.ISOMessage;

/**
 * @author Mohsen Beiranvand
 */
public interface UnpackMethods {

    ISOMessage build() throws ISOException;
}
