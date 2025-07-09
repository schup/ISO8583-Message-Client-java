package com.imohsenb.iso8583.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mohsen Beiranvand
 */
@Getter
@RequiredArgsConstructor
public enum FIELDS {
    // |Field title                        |no  |type  |len  |fixed |format|
    F1_Bitmap(1, FieldType.B, 64, true, null),
    F2_PAN(2, FieldType.N, 19, false, "LL"),
    F3_ProcessCode(3, FieldType.N, 6, true, null),
    F4_AmountTransaction(4, FieldType.N, 12, true, null),
    F5_AmountSettlement(5, FieldType.N, 12, true, null),
    F6_AmountCardholder(6, FieldType.N, 12, true, null),
    F7_TransmissionDateTime(7, FieldType.N, 10, true, null),
    F8_AmountCardholder_BillingFee(8, FieldType.N, 8, true, null),
    F9_ConversionRate_Settlement(9, FieldType.N, 8, true, null),
    F10_ConversionRate_Cardholder(10, FieldType.N, 8, true, null),
    F11_STAN(11, FieldType.N, 6, true, null),
    F12_LocalTime(12, FieldType.N, 6, true, null),
    F13_LocalDate(13, FieldType.N, 4, true, null),
    F14_ExpirationDate(14, FieldType.N, 4, true, null),
    F15_SettlementDate(15, FieldType.N, 4, true, null),
    F16_CurrencyConversionDate(16, FieldType.N, 4, true, null),
    F17_CaptureDate(17, FieldType.N, 4, true, null),
    F18_MerchantType(18, FieldType.N, 4, true, null),
    F19_AcquiringInstitution(19, FieldType.N, 3, true, null),
    F20_PANExtended(20, FieldType.N, 3, true, null),
    F21_ForwardingInstitution(21, FieldType.N, 3, true, null),
    F22_EntryMode(22, FieldType.N, 3, true, null),
    F23_PANSequence(23, FieldType.N, 3, true, null),
    F24_NII_FunctionCode(24, FieldType.N, 3, true, null),
    F25_POS_ConditionCode(25, FieldType.N, 2, true, null),
    F26_POS_CaptureCode(26, FieldType.N, 2, true, null),
    F27_AuthIdResponseLength(27, FieldType.N, 1, true, null),
    F28_Amount_TransactionFee(28, FieldType.X_N, 8, true, null),
    F29_Amount_SettlementFee(29, FieldType.X_N, 8, true, null),
    F30_Amount_TransactionProcessingFee(30, FieldType.X_N, 8, true, null),
    F31_Amount_SettlementProcessingFee(31, FieldType.X_N, 8, true, null),
    F32_AcquiringInstitutionIdCode(32, FieldType.N, 11, false, "LL"),
    F33_ForwardingInstitutionIdCode(33, FieldType.N, 11, false, "LL"),
    F34_PAN_Extended(34, FieldType.NS, 28, false, "LL"),
    F35_Track2(35, FieldType.Z, 37, false, "LL"),
    F36_Track3(36, FieldType.Z, 104, false, "LLL"),
    F37_RRN(37, FieldType.AN, 12, true, null),
    F38_AuthIdResponse(38, FieldType.AN, 6, true, null),
    F39_ResponseCode(39, FieldType.AN, 2, true, null),
    F40_ServiceRestrictionCode(40, FieldType.AN, 3, true, null),
    F41_CA_TerminalID(41, FieldType.ANS, 8, true, null),
    F42_CA_ID(42, FieldType.ANS, 15, true, null),
    F43_CardAcceptorInfo(43, FieldType.ANS, 40, true, null),
    F44_AddResponseData(44, FieldType.AN, 25, false, "LL"),
    F45_Track1(45, FieldType.AN, 76, false, "LL"),
    F46_AddData_ISO(46, FieldType.AN, 999, false, "LLL"),
    F47_AddData_National(47, FieldType.AN, 999, false, "LLL"),
    F48_AddData_Private(48, FieldType.AN, 999, false, "LLL"),
    F49_CurrencyCode_Transaction(49, FieldType.A_N, 3, true, null),
    F50_CurrencyCode_Settlement(50, FieldType.A_N, 3, true, null),
    F51_CurrencyCode_Cardholder(51, FieldType.A_N, 3, true, null),
    F52_PIN(52, FieldType.B, 8, true, null),
    F53_SecurityControlInfo(53, FieldType.N, 16, true, null),
    F54_AddAmount(54, FieldType.AN, 120, false, "LLL"),
    F55_ICC(55, FieldType.ANS, 999, false, "LLL"),
    F56_Reserved_ISO(56, FieldType.ANS, 999, false, "LLL"),
    F57_Reserved_National(57, FieldType.ANS, 999, false, "LLL"),
    F58_Reserved_National(58, FieldType.ANS, 999, false, "LLL"),
    F59_Reserved_National(59, FieldType.ANS, 999, false, "LLL"),
    F60_Reserved_National(60, FieldType.ANS, 999, false, "LLL"),
    F61_Reserved_Private(61, FieldType.ANS, 999, false, "LLL"),
    F62_Reserved_Private(62, FieldType.ANS, 999, false, "LLL"),
    F63_Reserved_Private(63, FieldType.ANS, 999, false, "LLL"),
    F64_MAC(64, FieldType.B, 16, true, null);


    private final int no;
    private final FieldType type; // Changed from String
    private final int length;
    private final boolean fixed;
    private final String format;


    private static final Map<Integer, FIELDS> map = new HashMap<>();

    static {
        for (FIELDS field : FIELDS.values()) {
            map.put(field.getNo(), field);
        }
    }

    public static FIELDS valueOf(int no) {
        return map.get(no);
    }
}
