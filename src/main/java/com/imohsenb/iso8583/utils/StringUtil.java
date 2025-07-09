package com.imohsenb.iso8583.utils;

import java.nio.ByteBuffer;
import java.util.Arrays;

/**
 * Utility class for string manipulation, focusing on conversions between byte arrays,
 * hexadecimal strings, and ASCII representations.
 * This class provides various helper methods for common string and byte array operations
 * often encountered in data processing and communication protocols.
 *
 * @author Mohsen Beiranvand
 */
public final class StringUtil {

    private final static char[] hexArray = "0123456789ABCDEF".toCharArray();

    /**
     * Converts a byte array to its hexadecimal string representation.
     * Each byte is converted into two hexadecimal characters (0-F).
     *
     * @param data The byte array to convert.
     * @return A string containing the hexadecimal representation of the input byte array.
     */
    public static String fromByteArray(byte[] data) {
        char[] hexChars = new char[data.length * 2];
        for (int j = 0; j < data.length; j++) {
            int v = data[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

    /**
     * Converts a byte array containing hexadecimal characters to an ASCII string.
     * This method first converts the byte array to a hexadecimal string, then converts
     * that hexadecimal string to its ASCII representation.
     *
     * @param data The byte array to convert.
     * @return An ASCII string representation of the input byte array.
     */
    public static String asciiFromByteArray(byte[] data) {
        return hexToAscii(fromByteArray(data));
    }

    /**
     * Converts an ASCII string to its hexadecimal string representation.
     * Each character in the ASCII string is converted to its corresponding hexadecimal value.
     *
     * @param asciiStr The ASCII string to convert.
     * @return A string containing the hexadecimal representation of the input ASCII string.
     */
    public static String asciiToHex(String asciiStr) {
        char[] chars = asciiStr.toCharArray();
        StringBuilder hex = new StringBuilder();
        for (char ch : chars) {
            hex.append(Integer.toHexString(ch));
        }

        return hex.toString();
    }

    /**
     * Converts a hexadecimal string to its ASCII string representation.
     * Each pair of hexadecimal characters is converted to its corresponding ASCII character.
     *
     * @param hexStr The hexadecimal string to convert.
     * @return An ASCII string representation of the input hexadecimal string.
     */
    public static String hexToAscii(String hexStr) {
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < hexStr.length(); i += 2) {
            String str = hexStr.substring(i, i + 2);
            output.append((char) Integer.parseInt(str, 16));
        }

        return output.toString();
    }

    /**
     * Converts a byte array to a byte array representing its hexadecimal string.
     * Each byte in the input array is converted into two hexadecimal characters,
     * which are then stored as ASCII bytes in the output array.
     *
     * @param data The byte array to convert.
     * @return A byte array containing the ASCII representation of the hexadecimal values.
     */
    public static byte[] asciiToHex(byte[] data) {

        char[] hexChars = new char[data.length * 2];
        for (int j = 0; j < data.length; j++) {
            int v = data[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }

        byte[] res = new byte[hexChars.length];
        for (int i = 0; i < hexChars.length; i++) {
            res[i] = (byte) hexChars[i];
        }

        Arrays.fill(hexChars, '\u0000');
        return res;
    }

    /**
     * Converts a hexadecimal string to a byte array.
     * If the input string has an odd length, it is padded with a leading '0'.
     *
     * @param s The hexadecimal string to convert.
     * @return A byte array representing the hexadecimal string.
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();

        if (len % 2 != 0) {
            s = "0" + s;
            len++;
        }

        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }

        return data;
    }

    /**
     * Converts the content of a ByteBuffer to its hexadecimal string representation.
     * The conversion is performed on the portion of the buffer from its start to its current position.
     *
     * @param readBuffer The ByteBuffer to convert.
     * @return A string containing the hexadecimal representation of the ByteBuffer's content.
     */
    public static String fromByteBuffer(ByteBuffer readBuffer) {

        return fromByteArray(Arrays.copyOfRange(readBuffer.array(), 0, readBuffer.position()));
    }


    /**
     * Converts an integer value to a padded, uppercase hexadecimal string.
     * If the hexadecimal representation has an odd length, it is padded with a leading '0'.
     *
     * @param value The integer to convert.
     * @return A string containing the padded, uppercase hexadecimal representation of the integer.
     */
    public static String intToHexString(int value) {
        String hs = Integer.toHexString(value);
        if (hs.length() % 2 != 0) {
            hs = "0" + hs;
        }
        hs = hs.toUpperCase();
        return hs;
    }

    /**
     * Converts a byte array representing ASCII characters to a byte array representing
     * their hexadecimal values. This involves a series of conversions:
     * byte array (ASCII) -> hex string -> ASCII string -> hex byte array.
     *
     * @param bytes The byte array containing ASCII characters.
     * @return A byte array representing the hexadecimal values of the input ASCII characters.
     */
    public static byte[] asciiToByteArray(byte[] bytes) {
        return StringUtil.hexStringToByteArray(StringUtil.hexToAscii(StringUtil.fromByteArray(bytes)));
    }

    /**
     * Converts a string to its Unicode hexadecimal notation.
     * Each character in the string is converted to its 4-digit Unicode hexadecimal representation,
     * prefixed with "\\u".
     *
     * @param str The string to convert.
     * @return A string containing the Unicode hexadecimal representation of the input string.
     */
    public static String toHexString(String str) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            sb.append(toHexString(str.charAt(i)));
        }
        return sb.toString();
    }

    /**
     * Converts a character into its 4-digit Hexadecimal notation of Unicode.
     * For example, 'a' becomes "\\u0061".
     *
     * @param ch The character to convert.
     * @return A string containing the 4-digit Unicode hexadecimal representation of the character, prefixed with "\\u".
     */
    public static String toHexString(char ch) {
        String hex = Integer.toHexString(ch);
        while (hex.length() < 4) {
            hex = "0" + hex;
        }
        hex = "\\u" + hex.toUpperCase();
        return hex;
    }

}
