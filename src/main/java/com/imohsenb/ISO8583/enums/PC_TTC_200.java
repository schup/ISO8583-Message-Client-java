package com.imohsenb.ISO8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum PC_TTC_200 {

    Purchase("00"),
    Withdrawal("01"),
    Void("02"),
    Refund_Return("20"),
    Payment_Deposit_Refresh("21"),
    AccountTransfer("40"),
    PurchaseAdvise("00"),
    Refund_Return_advise("20");

    private final String code;

}
