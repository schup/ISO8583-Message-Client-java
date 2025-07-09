package com.imohsenb.ISO8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum PC_TTC_100 {

    Authorization("00"),
    AuthorizationVoid("02"),
    Refund_Return("20"),
    Refund_Return_void("22"),
    BalanceInquiry("30");

    private final String code;

}
