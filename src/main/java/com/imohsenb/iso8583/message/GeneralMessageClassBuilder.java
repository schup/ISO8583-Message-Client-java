package com.imohsenb.iso8583.message;

import com.imohsenb.iso8583.message.enums.Version;

/**
 * Created by Mohsen Beiranvand on 18/04/01.
 */
public class GeneralMessageClassBuilder extends BaseMessageClassBuilder<GeneralMessageClassBuilder> {

    public GeneralMessageClassBuilder(String version, String messageClass) {
        super(version, messageClass);
    }

    public GeneralMessageClassBuilder(Version version, String messageClass) {
        super(version.getCode(), messageClass);
    }
}