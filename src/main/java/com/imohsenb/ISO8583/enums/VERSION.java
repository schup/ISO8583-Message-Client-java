package com.imohsenb.ISO8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum VERSION {

    V1987("0"),
    V1993("1"),
    V2003("2");

    private final String code;

}
