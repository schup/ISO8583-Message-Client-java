package com.imohsenb.iso8583.builders;

import com.imohsenb.iso8583.entities.ISOMessage;
import com.imohsenb.iso8583.enums.Version;
import com.imohsenb.iso8583.exceptions.ISOException;
import com.imohsenb.iso8583.interfaces.MessageClass;
import com.imohsenb.iso8583.interfaces.MessagePacker;
import com.imohsenb.iso8583.interfaces.UnpackMessage;
import com.imohsenb.iso8583.interfaces.UnpackMethods;
import com.imohsenb.iso8583.utils.StringUtil;
import lombok.experimental.UtilityClass;

/**
 * A builder class for constructing and parsing ISO 8583 messages.
 * This class provides static methods to initiate the building process for
 * both packing (creating) and unpacking (parsing) ISO 8583 messages.
 *
 * @author Mohsen Beiranvand
 */
@UtilityClass
public class ISOMessageBuilder {

    /**
     * Initiates the message packing process for a specific ISO 8583 version.
     *
     * @param version The {@link Version} of the ISO 8583 message to be packed.
     * @return A {@link MessageClass} instance to continue building the message.
     */
    public static MessageClass packer(Version version) {
        return new Builder(version.getCode());
    }

    /**
     * Inner builder class for creating ISO 8583 messages based on their message class.
     * Implements the {@link MessageClass} interface.
     */
    private record Builder(String version) implements MessageClass {

        /**
         * Constructs a new Builder instance with the specified ISO 8583 version.
         *
         * @param version The ISO 8583 version string.
         */
        private Builder {
        }


        /**
         * Initiates the building of an Authorization message.
         *
         * @return A {@link MessagePacker} for building an Authorization message.
         */
        @Override
        public GeneralMessageClassBuilder authorization() {
            return new GeneralMessageClassBuilder(version, "1");
        }

        /**
         * Initiates the building of a Financial message.
         *
         * @return A {@link MessagePacker} for building a Financial message.
         */
        @Override
        public GeneralMessageClassBuilder financial() {
            return new GeneralMessageClassBuilder(version, "2");
        }

        /**
         * Initiates the building of a File Action message.
         *
         * @return A {@link MessagePacker} for building a File Action message.
         */
        @Override
        public GeneralMessageClassBuilder fileAction() {
            return new GeneralMessageClassBuilder(version, "3");
        }

        /**
         * Initiates the building of a Reversal message.
         *
         * @return A {@link MessagePacker} for building a Reversal message.
         */
        @Override
        public GeneralMessageClassBuilder reversal() {
            return new GeneralMessageClassBuilder(version, "4");
        }

        /**
         * Initiates the building of a Reconciliation message.
         *
         * @return A {@link MessagePacker} for building a Reconciliation message.
         */
        @Override
        public GeneralMessageClassBuilder reconciliation() {
            return new GeneralMessageClassBuilder(version, "5");
        }

        /**
         * Initiates the building of an Administrative message.
         *
         * @return A {@link MessagePacker} for building an Administrative message.
         */
        @Override
        public GeneralMessageClassBuilder administrative() {
            return new GeneralMessageClassBuilder(version, "6");
        }

        /**
         * Initiates the building of a Fee Collection message.
         *
         * @return A {@link MessagePacker} for building a Fee Collection message.
         */
        @Override
        public GeneralMessageClassBuilder feeCollection() {
            return new GeneralMessageClassBuilder(version, "7");
        }

        /**
         * Initiates the building of a Network Management message.
         *
         * @return A {@link MessagePacker} for building a Network Management message.
         */
        @Override
        public GeneralMessageClassBuilder networkManagement() {
            return new GeneralMessageClassBuilder(version, "8");
        }

    }


    /**
     * Initiates the message unpacking process.
     *
     * @return An {@link UnpackMessage} instance to continue parsing the message.
     */
    public static UnpackMessage unpacker() {
        return new UnpackBuilder();
    }

    /**
     * Inner builder class for unpacking ISO 8583 messages.
     * Implements the {@link UnpackMessage} and {@link UnpackMethods} interfaces.
     */
    public static class UnpackBuilder implements UnpackMessage, UnpackMethods {

        private byte[] message;

        /**
         * Sets the ISO 8583 message in byte array format for unpacking.
         *
         * @param message The ISO 8583 message as a byte array.
         * @return The current {@link UnpackMethods} instance for chaining.
         */
        @Override
        public UnpackMethods setMessage(byte[] message) {
            this.message = message;
            return this;
        }

        /**
         * Sets the ISO 8583 message in hexadecimal string format for unpacking.
         *
         * @param message The ISO 8583 message as a hexadecimal string.
         * @return The current {@link UnpackMethods} instance for chaining.
         */
        @Override
        public UnpackMethods setMessage(String message) {
            setMessage(StringUtil.hexStringToByteArray(message));
            return this;
        }

        /**
         * Builds and returns the parsed {@link ISOMessage} object.
         *
         * @return An {@link ISOMessage} object containing the parsed data.
         * @throws ISOException If an error occurs during the message parsing process.
         */
        @Override
        public ISOMessage build() throws ISOException {

            ISOMessage finalMessage = new ISOMessage();
            finalMessage.setMessage(message);
            return finalMessage;
        }


    }

}
