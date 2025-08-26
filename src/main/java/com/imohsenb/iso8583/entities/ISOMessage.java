package com.imohsenb.iso8583.entities;

import com.imohsenb.iso8583.enums.FIELDS;
import com.imohsenb.iso8583.enums.FieldType;
import com.imohsenb.iso8583.exceptions.ISOException;
import com.imohsenb.iso8583.security.ISOMacGenerator;
import com.imohsenb.iso8583.utils.FixedBitSet;
import com.imohsenb.iso8583.utils.StringUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Represents an ISO 8583 message, encapsulating its structure, data elements,
 * and utility methods for parsing and manipulation.
 * This class handles the various components of an ISO 8583 message, including
 * the Message Type Indicator (MTI), primary bitmap, and individual data elements.
 *
 * @author Mohsen Beiranvand
 */
@Slf4j
public class ISOMessage {

    private final TreeMap<Integer, byte[]> dataElements = new TreeMap<>();

    @Getter
    private boolean isNil = true;
    private String message;
    @Getter
    private String mti;
    private byte[] msg;
    /**
     * The header of the ISO 8583 message.
     */
    @Getter
    private byte[] header;
    /**
     * The body of the ISO 8583 message, excluding the header.
     */
    @Getter
    private byte[] body;
    @Getter
    private byte[] primaryBitmap;
    @Getter
    private int msgClass;
    @Getter
    private int msgFunction;
    @Getter
    private int msgOrigin;
    /**
     * The total length of the ISO 8583 message in bytes.
     */
    private int length = 0;

    /**
     * Returns the total length of the ISO 8583 message in bytes.
     *
     * @return The length of the message.
     */
    public int length() {
        return length;
    }

    /**
     * Retrieves the value of a specific data element as a byte array.
     *
     * @param fieldNo The 1-based number of the field to retrieve.
     * @return The value of the specified field in byte array format.
     * @throws ISOException If the specified field does not exist in the message.
     */
    public byte[] getField(int fieldNo) throws ISOException {
        if (!dataElements.containsKey(fieldNo)) {
            throw new ISOException("Field No " + fieldNo + " does not exists");
        }
        return dataElements.get(fieldNo);
    }

    /**
     * Retrieves the value of a specific data element as a byte array, using the {@link FIELDS} enum.
     *
     * @param field The {@link FIELDS} enum representing the field to retrieve.
     * @return The value of the specified field in byte array format, or null if the field does not exist.
     */
    public byte[] getField(FIELDS field) {
        return dataElements.get(field.getNo());
    }

    /**
     * Retrieves the value of a specific data element as a String.
     *
     * @param fieldNo The 1-based number of the field to retrieve.
     * @return The value of the specified field in String format.
     * @throws ISOException If the specified field does not exist or cannot be converted.
     */
    public String getStringField(int fieldNo) throws ISOException {
        return getStringField(FIELDS.valueOf(fieldNo));

    }

    /**
     * Retrieves the value of a specific data element as a String, using the {@link FIELDS} enum.
     *
     * @param field The {@link FIELDS} enum representing the field to retrieve.
     * @return The value of the specified field in String format.
     * @throws ISOException If the specified field does not exist or cannot be converted.
     */
    public String getStringField(FIELDS field) throws ISOException {

        return getStringField(field, false);
    }

    /**
     * Retrieves the value of a specific data element as a String, with an option for ASCII conversion.
     *
     * @param fieldNo  The 1-based number of the field to retrieve.
     * @param asciiFix If true, attempts to convert the field's hexadecimal representation to ASCII.
     * @return The value of the specified field in String format.
     * @throws ISOException If the specified field does not exist or cannot be converted.
     */
    public String getStringField(int fieldNo, boolean asciiFix) throws ISOException {
        return getStringField(FIELDS.valueOf(fieldNo), asciiFix);

    }

    /**
     * Retrieves the value of a specific data element as a String, using the {@link FIELDS} enum and an option for ASCII conversion.
     *
     * @param field    The {@link FIELDS} enum representing the field to retrieve.
     * @param asciiFix If true, attempts to convert the field's hexadecimal representation to ASCII.
     * @return The value of the specified field in String format.
     * @throws ISOException If the specified field does not exist or cannot be converted.
     */
    public String getStringField(FIELDS field, boolean asciiFix) throws ISOException {

        String temp = StringUtil.fromByteArray(getField(field.getNo()));
        if (asciiFix && field.getType() != FieldType.N) {
            return StringUtil.hexToAscii(temp);
        }
        return temp;
    }

    /**
     * Sets the raw ISO 8583 message and parses its components.
     * This method extracts the header, body, primary bitmap, and data elements from the provided byte array.
     *
     * @param message         The ISO 8583 message in byte array format.
     * @param headerAvailable A boolean indicating whether a 5-byte header is present in the message.
     * @return The current ISOMessage instance, populated with the parsed message data.
     * @throws ISOException If an error occurs during message parsing.
     */
    public ISOMessage setMessage(byte[] message, boolean headerAvailable) throws ISOException {

        isNil = false;

        msg = message;
        length = msg.length / 2;

        int headerOffset = 0;

        if (headerAvailable) {
            headerOffset = 5;
        }

        try {

            this.header = Arrays.copyOfRange(msg, 0, headerOffset);
            this.body = Arrays.copyOfRange(msg, headerOffset, msg.length);
            this.primaryBitmap = Arrays.copyOfRange(body, 2, 10);

            parseHeader();
            parseBody();

        } catch (Exception e) {
            throw new ISOException(e.getMessage(), e.getCause());
        }

        return this;
    }

    /**
     * Sets the raw ISO 8583 message and parses its components, assuming a 5-byte header is present.
     * This is a convenience method that calls {@link #setMessage(byte[], boolean)} with `headerAvailable` set to true.
     *
     * @param message The ISO 8583 message in byte array format.
     * @return The current ISOMessage instance, populated with the parsed message data.
     * @throws ISOException If an error occurs during message parsing.
     */
    public ISOMessage setMessage(byte[] message) throws ISOException {
        return this.setMessage(message, true);
    }

    /**
     * Parses the Message Type Indicator (MTI) from the message body.
     * Extracts the message class, function, and origin from the MTI.
     */
    private void parseHeader() {
        if (body.length > 2) {
            mti = StringUtil.fromByteArray(Arrays.copyOfRange(body, 0, 2));
            msgClass = Integer.parseInt(mti.substring(1, 2));
            msgFunction = Integer.parseInt(mti.substring(2, 3));
            msgOrigin = Integer.parseInt(mti.substring(3, 4));
        }
    }

    /**
     * Parses the data elements from the message body based on the primary bitmap.
     * This method iterates through the set bits in the primary bitmap to identify
     * and extract each data element, handling both fixed and variable-length fields.
     */
    private void parseBody() {
        FixedBitSet pb = new FixedBitSet(64);
        pb.fromHexString(StringUtil.fromByteArray(primaryBitmap));
        int offset = 10;

        for (int o : pb.getIndexes()) {

            FIELDS field = FIELDS.valueOf(o);

            if (field.isFixed()) {
                int len = field.getLength();
                if (field.getType() == FieldType.N) {
                    if (len % 2 != 0) {
                        len++;
                    }
                    len = len / 2;
                    addElement(field, Arrays.copyOfRange(body, offset, offset + len));
                } else {
                    addElement(field, Arrays.copyOfRange(body, offset, offset + len));
                }
                offset += len;
            } else {
                int formatLength = switch (field.getFormat()) {
                    case "LLL" -> 2;
                    default -> 1;
                };

                int flen = Integer.parseInt(StringUtil.fromByteArray(Arrays.copyOfRange(body, offset, offset + formatLength)));

                if (field.getType() == FieldType.Z || field.getType() == FieldType.N) {
                    flen /= 2;
                }

                offset = offset + formatLength;

                addElement(field, Arrays.copyOfRange(body, offset, offset + flen));

                offset += flen;
            }

        }
    }

    /**
     * Adds a parsed data element to the internal map of data elements.
     *
     * @param field The {@link FIELDS} enum representing the field.
     * @param data  The byte array value of the data element.
     */
    private void addElement(FIELDS field, byte[] data) {
        dataElements.put(field.getNo(), data);
    }


    /**
     * Returns a Set view of the data elements contained in this ISOMessage.
     * Each element in the set is a Map.Entry where the key is the field number (Integer)
     * and the value is the field data (byte array).
     *
     * @return A Set of Map.Entry objects representing the data elements.
     */
    public Set<Map.Entry<Integer, byte[]>> getEntrySet() {
        return dataElements.entrySet();
    }

    /**
     * Checks if a specific data element exists in the message, using the {@link FIELDS} enum.
     *
     * @param field The {@link FIELDS} enum representing the field to check.
     * @return True if the field exists and has a value in the message, false otherwise.
     */
    public boolean fieldExits(FIELDS field) {
        return fieldExits(field.getNo());
    }

    /**
     * Checks if a specific data element exists in the message, using its field number.
     *
     * @param no The 1-based field number to check.
     * @return True if the field exists and has a value in the message, false otherwise.
     */
    public boolean fieldExits(int no) {
        return dataElements.containsKey(no);
    }

    /**
     * Validates the Message Authentication Code (MAC) of the ISO 8583 message.
     * This method is particularly useful for validating the MAC in response messages.
     *
     * @param isoMacGenerator An implementation of {@link ISOMacGenerator} used to generate the MAC for validation.
     * @return True if the message MAC is valid, false otherwise.
     * @throws ISOException If an error occurs during MAC generation or validation.
     */
    public boolean validateMac(ISOMacGenerator isoMacGenerator) throws ISOException {

        if (!fieldExits(FIELDS.F64_MAC) || getField(FIELDS.F64_MAC).length == 0) {
            log.info("validate mac : not exists");
            return false;
        }
        byte[] mBody = new byte[getBody().length - 8];
        System.arraycopy(getBody(), 0, mBody, 0, getBody().length - 8);
        byte[] oMac = Arrays.copyOf(getField(FIELDS.F64_MAC), 8);
        byte[] vMac = isoMacGenerator.generate(mBody);

        return Arrays.equals(oMac, vMac);
    }

    /**
     * Returns the complete ISO 8583 message as a hexadecimal string.
     * If the message has not been converted to a string yet, it performs the conversion.
     *
     * @return The ISO 8583 message in hexadecimal string format.
     */
    @Override
    public String toString() {
        if (message == null) {
            message = StringUtil.fromByteArray(msg);
        }
        return message;
    }

    /**
     * Returns a string representation of all data elements in the ISOMessage,
     * formatted for human readability. Each field is displayed with its name and value.
     *
     * @return A string containing the formatted representation of all data elements.
     */
    public String fieldsToString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("\r\n");
        for (Map.Entry<Integer, byte[]> item :
                dataElements.entrySet()) {
            stringBuilder
                    .append(FIELDS.valueOf(item.getKey()).name())
                    .append(" : ")
                    .append(StringUtil.fromByteArray(item.getValue()))
                    .append("\r\n");
        }
        stringBuilder.append("\r\n");
        return stringBuilder.toString();
    }

    /**
     * Clears all internal data and resets the ISOMessage to its initial state.
     * This method sets all byte array fields to null and clears the message string.
     */
    public void clear() {

        if (header != null) {
            Arrays.fill(header, (byte) 0);
        }
        if (body != null) {
            Arrays.fill(body, (byte) 0);
        }
        if (primaryBitmap != null) {
            Arrays.fill(primaryBitmap, (byte) 0);
        }

        message = null;
        header = null;
        body = null;
        primaryBitmap = null;
        dataElements.clear();
    }

}
