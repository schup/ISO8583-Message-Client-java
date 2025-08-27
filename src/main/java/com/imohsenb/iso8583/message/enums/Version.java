package com.imohsenb.iso8583.message.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum Version {

    V1987("0"),
    V1993("1"),
    V2003("2");

    private final String code;

}
