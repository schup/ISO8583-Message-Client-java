package com.imohsenb.iso8583.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class ByteArrayTest {

    private ByteArray byteArray;
    private static final int FRAME_SIZE = 512;

    @BeforeEach
    void setUp() {
        byteArray = new ByteArray();
    }

    @Test
    @DisplayName("Initial state should be empty with correct limits")
    void testInitialState() {
        assertThat(byteArray.position()).as("Initial position").isZero();
        assertThat(byteArray.limit()).as("Initial limit").isEqualTo(FRAME_SIZE);
        assertThat(byteArray.array()).as("Initial array").isEmpty();
        assertThat(byteArray.toString()).as("Initial toString").isEmpty();
    }

    @Test
    @DisplayName("append(byte[]) should add bytes to the end")
    void testAppendByteArray() {
        byte[] data1 = "Hello".getBytes(StandardCharsets.UTF_8);
        byteArray.append(data1);

        assertThat(byteArray.position()).isEqualTo(5);
        assertThat(byteArray.array()).isEqualTo(data1);
        assertThat(byteArray).hasToString("Hello");

        byte[] data2 = " World".getBytes(StandardCharsets.UTF_8);
        byteArray.append(data2);

        assertThat(byteArray.position()).isEqualTo(11);
        assertThat(byteArray.array()).isEqualTo("Hello World".getBytes(StandardCharsets.UTF_8));
        assertThat(byteArray).hasToString("Hello World");
    }

    @Test
    @DisplayName("append(byte) should add a single byte")
    void testAppendByte() {
        byteArray.append((byte) 'A');
        assertThat(byteArray.position()).isEqualTo(1);
        assertThat(byteArray.array()).isEqualTo(new byte[]{'A'});
    }

    @Test
    @DisplayName("append() should correctly expand buffer when capacity is exceeded")
    void testAppendWithBufferExpansion() {
        // This test will fail with the original buggy code but pass with the fix.
        byte[] initialData = new byte[FRAME_SIZE - 2];
        Arrays.fill(initialData, (byte) 'A');
        byteArray.append(initialData);

        assertThat(byteArray.position()).isEqualTo(FRAME_SIZE - 2);

        // This append should trigger expandBuffer()
        byte[] extraData = "Extra".getBytes(StandardCharsets.UTF_8);
        byteArray.append(extraData);

        assertThat(byteArray.position()).isEqualTo(FRAME_SIZE - 2 + 5);
        assertThat(byteArray.limit()).as("Limit should increase by one frame size").isEqualTo(FRAME_SIZE * 2);

        byte[] expected = new byte[FRAME_SIZE - 2 + 5];
        System.arraycopy(initialData, 0, expected, 0, initialData.length);
        System.arraycopy(extraData, 0, expected, initialData.length, extraData.length);

        assertThat(byteArray.array()).isEqualTo(expected);
    }

    @Test
    @DisplayName("prepend(byte[]) should add bytes to the beginning")
    void testPrependByteArray() {
        byteArray.append("World".getBytes(StandardCharsets.UTF_8));
        byteArray.prepend("Hello ".getBytes(StandardCharsets.UTF_8));

        assertThat(byteArray.position()).isEqualTo(11);
        assertThat(byteArray.array()).isEqualTo("Hello World".getBytes(StandardCharsets.UTF_8));
        assertThat(byteArray).hasToString("Hello World");
    }

    @Test
    @DisplayName("prepend(byte) should add a single byte to the beginning")
    void testPrependByte() {
        byteArray.append("BC".getBytes(StandardCharsets.UTF_8));
        byteArray.prepend((byte) 'A');

        assertThat(byteArray.position()).isEqualTo(3);
        assertThat(byteArray.array()).isEqualTo("ABC".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("array() should return a defensive copy, not the internal buffer")
    void testArrayReturnsCopy() {
        byte[] data = "test".getBytes(StandardCharsets.UTF_8);
        byteArray.append(data);

        byte[] retrievedArray = byteArray.array();
        assertThat(retrievedArray).isEqualTo(data);

        // Modify the retrieved array
        retrievedArray[0] = 'X';

        // The internal state of byteArray should not have changed
        assertThat(byteArray.array()).as("Internal array should not be modified externally").isEqualTo(data);
    }

    @Test
    @DisplayName("compact() should shrink the buffer to the current position")
    void testCompact() {
        byteArray.append("data".getBytes(StandardCharsets.UTF_8));
        assertThat(byteArray.position()).isEqualTo(4);
        assertThat(byteArray.limit()).isEqualTo(FRAME_SIZE);

        byteArray.compact();

        assertThat(byteArray.position()).as("Position should be unchanged after compact").isEqualTo(4);
        assertThat(byteArray.limit()).as("Limit should be equal to position after compact").isEqualTo(4);
        assertThat(byteArray.array()).isEqualTo("data".getBytes(StandardCharsets.UTF_8));
    }

    @Test
    @DisplayName("clear() should reset the ByteArray to its initial state")
    void testClear() {
        byteArray.append("some data".getBytes(StandardCharsets.UTF_8));
        byteArray.clear();

        assertThat(byteArray.position()).as("Position should be 0 after clear").isZero();
        assertThat(byteArray.limit()).as("Limit should be reset to frameSize after clear").isEqualTo(FRAME_SIZE);
        assertThat(byteArray.array()).as("Array should be empty after clear").isEmpty();
    }

    @Test
    @DisplayName("replace() should clear and append new data")
    void testReplace() {
        byteArray.append("old data".getBytes(StandardCharsets.UTF_8));
        byte[] newData = "new data".getBytes(StandardCharsets.UTF_8);
        byteArray.replace(newData);

        assertThat(byteArray.position()).isEqualTo(newData.length);
        assertThat(byteArray.limit()).isEqualTo(FRAME_SIZE);
        assertThat(byteArray.array()).isEqualTo(newData);
    }

    @Test
    @DisplayName("Methods should support chaining")
    void testMethodChaining() {
        byte[] data1 = "Hello".getBytes(StandardCharsets.UTF_8);
        byte[] data2 = " World".getBytes(StandardCharsets.UTF_8);
        byte[] data3 = "Start ".getBytes(StandardCharsets.UTF_8);

        byteArray.append(data1).append(data2).prepend(data3);

        assertThat(byteArray).hasToString("Start Hello World");
        assertThat(byteArray.position()).isEqualTo(17);
    }
}