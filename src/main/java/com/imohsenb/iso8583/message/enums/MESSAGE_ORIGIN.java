package com.imohsenb.iso8583.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum MESSAGE_ORIGIN {

    Acquirer("0");

    private final String code;

}
