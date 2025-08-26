package com.imohsenb.iso8583.builders;

import com.imohsenb.iso8583.entities.ISOMessage;
import com.imohsenb.iso8583.enums.*;
import com.imohsenb.iso8583.exceptions.ISOException;
import com.imohsenb.iso8583.security.ISOMacGenerator;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@Slf4j
class GeneralMessageClassBuilderTest {

    @Test
    void checkLeftPadding() throws Exception {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setLeftPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setField(FIELDS.F11_STAN, "1")
                .setField(FIELDS.F24_NII_FunctionCode, "333")
                .build();
        log.debug(isoMessage.toString());
        assertThat(isoMessage).hasToString("08002020010000000000920000000001F333");
    }

    @Test
    void checkRightPadding() throws Exception {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setRightPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setField(FIELDS.F11_STAN, "1")
                .setField(FIELDS.F24_NII_FunctionCode, "333")
                .build();
        log.debug(isoMessage.toString());
        assertThat(isoMessage).hasToString("08002020010000000000920000000001333F");
    }

    @Test
    void checkSetHeader() throws ISOException {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setRightPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setHeader("1002230000")
                .build();
        //Then
        assertThat(isoMessage).hasToString("100223000008002000000000000000920000");
    }

    @Test
    void checkWithoutSetHeader() throws ISOException {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setRightPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .build();
        //Then
        assertThat(isoMessage).hasToString("08002000000000000000920000");
    }


    @Test
    void evenPanShouldHaveCorrectLengthPrefix() throws Exception {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setLeftPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setField(FIELDS.F2_PAN, "1234567890123456")
                .build();
        log.debug(isoMessage.toString());
        assertThat(isoMessage).hasToString("08006000000000000000161234567890123456920000");
    }

    @Test
    void oddPanShouldHaveCorrectLengthPrefixAndPaddingChar() throws Exception {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setLeftPadding((byte) 0xF)
                .mti(MESSAGE_FUNCTION.Request, MESSAGE_ORIGIN.Acquirer)
                .processCode("920000")
                .setField(FIELDS.F2_PAN, "1234567890123456789")
                .build();
        log.debug(isoMessage.toString());
        assertThat(isoMessage).hasToString("080060000000000000001901234567890123456789920000");
    }

//    @Test
//    void testSetFieldWithByteArray() throws ISOException {
//        long pan = 1234567890123456L;
//        byte[] panArray = ByteBuffer.allocate(8).putLong(pan).array();
//        long value = ByteBuffer.wrap(panArray).getLong();
//        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
//                .networkManagement()
//                .setField(FIELDS.F2_PAN, panArray)
//                .build();
//        assertThat(isoMessage.toString()).contains(String.valueOf(pan));
//    }

    @Test
    void testSetFieldWithString() throws ISOException {
        ISOMessage isoMessage = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setField(FIELDS.F2_PAN, "1234567890123456")
                .build();
        assertThat(isoMessage.toString()).contains("1234567890123456");
    }

    @Test
    void testSetFieldWithNullValue() {
        assertThrows(NullPointerException.class, () -> ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .setField(FIELDS.F2_PAN, (byte[]) null));
    }


    @Test
    void testGenerateMacWithNullGenerator() throws ISOException {
        ISOMessage message = ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .generateMac(null)
                .build();
        assertThat(message.getField(FIELDS.F64_MAC)).isNullOrEmpty();
    }

    @Test
    void testGenerateMacWithNullMac() {
        ISOMacGenerator macGenerator = mock(ISOMacGenerator.class);
        when(macGenerator.generate(new byte[0])).thenReturn(null);

        assertThrows(ISOException.class, () -> ISOMessageBuilder.packer(Version.V1987)
                .networkManagement()
                .generateMac(macGenerator));
    }

    @Test
    void testProcessCodeWithEnums() throws ISOException {
        GeneralMessageClassBuilder financial = new GeneralMessageClassBuilder(Version.V1987, "2");


        ISOMessage isoMessage = financial
                .processCode(PC_TTC_100.Authorization, PC_ATC.SavingAccount, PC_ATC.CheckingAccount)
                .build();
        assertThat(isoMessage.toString()).contains("001020");
    }

    @Test
    void testBuildTwice() throws ISOException {
        GeneralMessageClassBuilder builder = ISOMessageBuilder
                .packer(Version.V1987)
                .networkManagement();

        builder.setField(FIELDS.F2_PAN, "1234567890123456");

        ISOMessage isoMessage1 = builder.build();
        ISOMessage isoMessage2 = builder.build();

        assertThat(isoMessage1.toString()).isNotEqualTo(isoMessage2.toString());
        assertThat(isoMessage2.toString()).doesNotContain("1234567890123456");
    }
}
