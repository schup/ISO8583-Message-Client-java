package com.imohsenb.ISO8583.enums;

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
