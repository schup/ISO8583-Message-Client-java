package com.imohsenb.ISO8583.utils;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class FixedBitSetTest {

    @Test
    void testConstructorAndToString() {
        FixedBitSet bitSet = new FixedBitSet(8);
        assertThat(bitSet.toString()).isEqualTo("00000000");

        bitSet.set(0);
        bitSet.set(7);
        assertThat(bitSet.toString()).isEqualTo("10000001");
    }

    @Test
    void testFromHexString() {
        FixedBitSet bitSet = new FixedBitSet(16);
        bitSet.fromHexString("F00F");
        assertThat(bitSet.toString()).isEqualTo("1111000000001111");

        FixedBitSet bitSet2 = new FixedBitSet(4);
        bitSet2.fromHexString("A");
        assertThat(bitSet2.toString()).isEqualTo("1010");
    }

    @Test
    void testToHexString() {
        FixedBitSet bitSet = new FixedBitSet(16);
        bitSet.set(0);
        bitSet.set(1);
        bitSet.set(2);
        bitSet.set(3);
        bitSet.set(12);
        bitSet.set(13);
        bitSet.set(14);
        bitSet.set(15);
        assertThat(bitSet.toHexString()).isEqualTo("F00F");

        FixedBitSet bitSet2 = new FixedBitSet(4);
        bitSet2.set(1);
        bitSet2.set(3);
        assertThat(bitSet2.toHexString()).isEqualTo("5"); // 0101 in binary is 5 in hex
    }

    @Test
    void testGetIndexes() {
        FixedBitSet bitSet = new FixedBitSet(10);
        bitSet.set(0);
        bitSet.set(3);
        bitSet.set(9);

        ArrayList<Integer> expectedIndexes = new ArrayList<>(Arrays.asList(1, 4, 10));
        assertThat(bitSet.getIndexes()).isEqualTo(expectedIndexes);

        FixedBitSet emptyBitSet = new FixedBitSet(5);
        assertThat(emptyBitSet.getIndexes()).isEmpty();
    }
}