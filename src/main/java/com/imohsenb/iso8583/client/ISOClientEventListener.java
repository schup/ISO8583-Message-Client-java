package com.imohsenb.iso8583.client;

/**
 * @author Mohsen Beiranvand
 */
public interface ISOClientEventListener {

    void connecting();

    void connected();

    void connectionFailed();

    void connectionClosed();

    void disconnected();

    void beforeSendingMessage();

    void afterSendingMessage();

    void onReceiveData();

    void beforeReceiveResponse();

    void afterReceiveResponse();
}
