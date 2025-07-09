package com.imohsenb.iso8583.utils;

import org.junit.jupiter.api.Test;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.assertThat;

class StringUtilTest {

    @Test
    void testFromByteArray() {
        byte[] data = {(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF};
        assertThat(StringUtil.fromByteArray(data)).isEqualTo("DEADBEEF");
        byte[] emptyData = {};
        assertThat(StringUtil.fromByteArray(emptyData)).isEmpty();
    }

    @Test
    void testAsciiFromByteArray() {
        byte[] data = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // "Hello"
        assertThat(StringUtil.asciiFromByteArray(data)).isEqualTo("Hello");
    }

    @Test
    void testAsciiToHex_String() {
        assertThat(StringUtil.asciiToHex("Hello")).isEqualTo("48656c6c6f");
        assertThat(StringUtil.asciiToHex("")).isEmpty();
    }

    @Test
    void testHexToAscii() {
        assertThat(StringUtil.hexToAscii("48656c6c6f")).isEqualTo("Hello");
        assertThat(StringUtil.hexToAscii("")).isEmpty();
    }

    @Test
    void testAsciiToHex_ByteArray() {
        byte[] data = {0x48, 0x65, 0x6C, 0x6C, 0x6F}; // "Hello"
        byte[] expected = {0x34, 0x38, 0x36, 0x35, 0x36, 0x43, 0x36, 0x43, 0x36, 0x46}; // "48656C6C6F" as bytes
        assertThat(StringUtil.asciiToHex(data)).isEqualTo(expected);
    }

    @Test
    void testHexStringToByteArray() {
        byte[] expected = {(byte) 0xDE, (byte) 0xAD, (byte) 0xBE, (byte) 0xEF};
        assertThat(StringUtil.hexStringToByteArray("DEADBEEF")).isEqualTo(expected);
        byte[] expectedPadded = {(byte) 0x01, (byte) 0x23};
        assertThat(StringUtil.hexStringToByteArray("123")).isEqualTo(expectedPadded);
        assertThat(StringUtil.hexStringToByteArray("")).isEqualTo(new byte[]{});
    }

    @Test
    void testFromByteBuffer() {
        ByteBuffer buffer = ByteBuffer.wrap("Hello".getBytes(StandardCharsets.UTF_8));
        buffer.position(buffer.limit()); // Simulate reading the whole buffer
        assertThat(StringUtil.fromByteBuffer(buffer)).isEqualTo("48656C6C6F");

        ByteBuffer emptyBuffer = ByteBuffer.wrap(new byte[]{});
        emptyBuffer.position(emptyBuffer.limit());
        assertThat(StringUtil.fromByteBuffer(emptyBuffer)).isEmpty();
    }

    @Test
    void testIntToHexString() {
        assertThat(StringUtil.intToHexString(255)).isEqualTo("FF");
        assertThat(StringUtil.intToHexString(10)).isEqualTo("0A");
        assertThat(StringUtil.intToHexString(0)).isEqualTo("00");
        assertThat(StringUtil.intToHexString(256)).isEqualTo("0100");
    }

    @Test
    void testAsciiToByteArray_complex() {
        byte[] input = {0x31, 0x32, 0x33}; // ASCII for "123"
        // fromByteArray("123") -> "313233"
        // hexToAscii("313233") -> "123"
        // hexStringToByteArray("123") -> {0x01, 0x23}
        byte[] expected = {0x01, 0x23};
        assertThat(StringUtil.asciiToByteArray(input)).isEqualTo(expected);
    }

    @Test
    void testToHexString_String() {
        assertThat(StringUtil.toHexString("abc")).isEqualTo("\\u0061\\u0062\\u0063");
        assertThat(StringUtil.toHexString("")).isEmpty();
    }

    @Test
    void testToHexString_Char() {
        assertThat(StringUtil.toHexString('a')).isEqualTo("\\u0061");
        assertThat(StringUtil.toHexString('Z')).isEqualTo("\\u005A");
        assertThat(StringUtil.toHexString(' ')).isEqualTo("\\u0020");
    }
}