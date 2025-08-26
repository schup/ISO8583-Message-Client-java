package com.imohsenb.iso8583.interfaces;

import com.imohsenb.iso8583.entities.ISOMessage;
import com.imohsenb.iso8583.enums.FIELDS;
import com.imohsenb.iso8583.exceptions.ISOException;
import com.imohsenb.iso8583.security.ISOMacGenerator;

/**
 * Defines the contract for classes that can manipulate data elements within an ISO 8583 message.
 * This interface provides methods for setting individual fields, generating MACs, and building the final message.
 *
 * @param <T> The type of the implementing class, allowing for method chaining.
 * @author Mohsen Beiranvand
 */
public interface DataElement<T> {

    /**
     * Builds the ISO 8583 message with the configured data elements.
     *
     * @return The constructed {@link ISOMessage} object.
     * @throws ISOException If an error occurs during message construction.
     */
    ISOMessage build() throws ISOException;

    /**
     * Generates and sets the Message Authentication Code (MAC) for the ISO 8583 message.
     *
     * @param generator An implementation of {@link ISOMacGenerator} to use for MAC generation.
     * @return The current DataElement instance for method chaining.
     * @throws ISOException If an error occurs during MAC generation.
     */
    DataElement<T> generateMac(ISOMacGenerator generator) throws ISOException;

    /**
     * Sets the value of a specific data element using its field number and a String value.
     *
     * @param no    The 1-based number of the field to set.
     * @param value The String value to set for the field.
     * @return The current DataElement instance for method chaining.
     * @throws ISOException If an error occurs while setting the field.
     */
    DataElement<T> setField(int no, String value) throws ISOException;

    /**
     * Sets the value of a specific data element using the {@link FIELDS} enum and a String value.
     *
     * @param field The {@link FIELDS} enum representing the field to set.
     * @param value The String value to set for the field.
     * @return The current DataElement instance for method chaining.
     * @throws ISOException If an error occurs while setting the field.
     */
    DataElement<T> setField(FIELDS field, String value) throws ISOException;

    /**
     * Sets the value of a specific data element using its field number and a byte array value.
     *
     * @param no    The 1-based number of the field to set.
     * @param value The byte array value to set for the field.
     * @return The current DataElement instance for method chaining.
     * @throws ISOException If an error occurs while setting the field.
     */
    DataElement<T> setField(int no, byte[] value) throws ISOException;

    /**
     * Sets the value of a specific data element using the {@link FIELDS} enum and a byte array value.
     *
     * @param field The {@link FIELDS} enum representing the field to set.
     * @param value The byte array value to set for the field.
     * @return The current DataElement instance for method chaining.
     * @throws ISOException If an error occurs while setting the field.
     */
    DataElement<T> setField(FIELDS field, byte[] value) throws ISOException;


    /**
     * Sets the header of the ISO 8583 message.
     *
     * @param header The header string to set.
     * @return The current DataElement instance for method chaining.
     */
    DataElement<T> setHeader(String header);
}
