package com.imohsenb.iso8583.utils;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

/**
 * A fixed-size BitSet implementation that extends {@link java.util.BitSet}.
 * This class provides additional utility methods for converting between
 * hexadecimal strings and managing bit indexes, specifically tailored for
 * scenarios where a fixed number of bits is required.
 *
 * @author Mohsen Beiranvand
 */
public class FixedBitSet extends BitSet {

    private final int nbits;

    /**
     * Constructs a new FixedBitSet with the specified number of bits.
     * All bits are initially set to false.
     *
     * @param nbits The number of bits in the FixedBitSet.
     */
    public FixedBitSet(final int nbits) {
        super(nbits);
        this.nbits = nbits;
    }

    /**
     * Returns a string representation of the FixedBitSet, where each bit is represented by '1' or '0'.
     * The length of the string is equal to the fixed number of bits (nbits).
     *
     * @return A binary string representation of the FixedBitSet.
     */
    @Override
    public String toString() {
        final StringBuilder buffer = new StringBuilder(nbits);

        for (int i = 0; i < nbits; i++) {
            buffer.append(get(i) ? '1' : '0');
        }

        return buffer.toString();
    }

    /**
     * Populates the FixedBitSet from a hexadecimal string.
     * Each hexadecimal character in the input string corresponds to 4 bits in the FixedBitSet.
     *
     * @param value The hexadecimal string to convert.
     * @return The current FixedBitSet instance, with bits set according to the input hexadecimal string.
     */
    public FixedBitSet fromHexString(String value) {
        int offset = 0;
        for (int i = 0; i < value.length(); i = i + 1) {
            String item = value.substring(i, i + 1);
            byte bitem = (byte) Integer.parseInt(item, 16);
            if ((bitem & 0b1000) > 0) {
                set(offset);
            }
            if ((bitem & 0b0100) > 0) {
                set(offset + 1);
            }
            if ((bitem & 0b0010) > 0) {
                set(offset + 2);
            }
            if ((bitem & 0b0001) > 0) {
                set(offset + 3);
            }
            offset += 4;
        }
        return this;
    }

    /**
     * Converts the FixedBitSet to its hexadecimal string representation.
     * Each 4 bits are converted into one uppercase hexadecimal character.
     *
     * @return A string containing the uppercase hexadecimal representation of the FixedBitSet.
     */
    public String toHexString() {
        final StringBuilder buffer = new StringBuilder(nbits);
        String bStr = toString();

        for (int c = 0; c < nbits; c = c + 4) {
            int decimal = Integer.parseInt(bStr.substring(c, c + 4), 2);
            String hexStr = Integer.toHexString(decimal).toUpperCase();
            buffer.append(hexStr);
        }
        return buffer.toString();
    }

    /**
     * Returns a list of 1-based indexes of the bits that are set to true in this FixedBitSet.
     *
     * @return A List of Integers, where each integer represents the 1-based index of a set bit.
     */
    public List<Integer> getIndexes() {
        List<Integer> list = new ArrayList<>();
        int indx = -1;
        int size = size();
        while (indx < size) {
            indx = nextSetBit(indx + 1);
            if (indx == -1) {
                break;
            }
            list.add(indx + 1);
        }
        return list;
    }

}
