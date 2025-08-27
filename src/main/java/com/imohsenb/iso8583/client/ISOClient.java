package com.imohsenb.iso8583.client;

import com.imohsenb.iso8583.message.ISOMessage;

import java.io.IOException;

/**
 * @author Mohsen Beiranvand
 */
public interface ISOClient {

    /**
     * @throws ISOClientException
     * @throws IOException
     */
    void connect() throws ISOClientException, IOException;

    /**
     *
     */
    void disconnect();

    /**
     * @param isoMessage
     * @return
     * @throws ISOClientException
     * @throws IOException
     */
    byte[] sendMessageSync(ISOMessage isoMessage) throws ISOClientException, IOException;

    /**
     * @return
     */
    boolean isConnected();

    /**
     * @return
     */
    boolean isClosed();

    /**
     * @param isoClientEventListener
     */
    void setEventListener(ISOClientEventListener isoClientEventListener);

}
