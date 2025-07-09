package com.imohsenb.ISO8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum MESSAGE_FUNCTION {

    Request("0"),
    Advice("2");

    private final String code;


}
