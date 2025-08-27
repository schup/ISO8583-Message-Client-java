package com.imohsenb.iso8583.client;

import javax.net.ssl.TrustManager;

/**
 * @author Mohsen Beiranvand
 */
public interface SSLTrustManagers {
    ISOClientBuilder.ClientBuilder setTrustManagers(TrustManager[] trustManagers);
}