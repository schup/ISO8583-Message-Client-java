package com.imohsenb.ISO8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum PC_ATC {

    Default("00"),
    SavingAccount("10"),
    CheckingAccount("20"),
    CreditCardAccount("30");

    private final String code;

}
