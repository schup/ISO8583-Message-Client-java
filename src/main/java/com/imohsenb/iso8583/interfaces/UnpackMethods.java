package com.imohsenb.iso8583.interfaces;

import com.imohsenb.iso8583.entities.ISOMessage;
import com.imohsenb.iso8583.exceptions.ISOException;

/**
 * @author Mohsen Beiranvand
 */
public interface UnpackMethods {

    ISOMessage build() throws ISOException;
}
