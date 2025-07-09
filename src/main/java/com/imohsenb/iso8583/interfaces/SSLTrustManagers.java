package com.imohsenb.iso8583.interfaces;

import com.imohsenb.iso8583.builders.ISOClientBuilder;

import javax.net.ssl.TrustManager;

/**
 * @author Mohsen Beiranvand
 */
public interface SSLTrustManagers {
    ISOClientBuilder.ClientBuilder setTrustManagers(TrustManager[] trustManagers);
}