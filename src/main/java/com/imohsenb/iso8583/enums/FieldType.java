package com.imohsenb.iso8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Represents the various data types for fields in an ISO 8583 message.
 * Each enum constant corresponds to a specific data type code used in the ISO 8583 standard.
 */
@Getter
@RequiredArgsConstructor
public enum FieldType {
    /**
     * Binary data type.
     */
    B("b"),
    /**
     * Numeric data type.
     */
    N("n"),
    /**
     * Signed numeric data type (e.g., "x+n" where 'x' is the sign and 'n' is numeric).
     */
    X_N("x+n"),
    /**
     * Numeric and special characters data type.
     */
    NS("ns"),
    /**
     * Track data type (e.g., for Track 2 or Track 3 data).
     */
    Z("z"),
    /**
     * Alphanumeric data type.
     */
    AN("an"),
    /**
     * Alphanumeric and special characters data type.
     */
    ANS("ans"),
    /**
     * Alphabetic or Numeric data type.
     */
    A_N("a|n");

    /**
     * The string code representation of the field type.
     */
    private final String code;
}
