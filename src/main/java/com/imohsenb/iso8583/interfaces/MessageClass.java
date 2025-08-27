package com.imohsenb.iso8583.interfaces;

import com.imohsenb.iso8583.message.GeneralMessageClassBuilder;

/**
 * @author Mohsen Beiranvand
 */
public interface MessageClass {
    /**
     * Determine if funds are available, get an approval but do not post to account for reconciliation.
     *
     * @return
     */
    GeneralMessageClassBuilder authorization();

    /**
     * Determine if funds are available, get an approval and post directly to the account.
     *
     * @return
     */
    GeneralMessageClassBuilder financial();

    /**
     * Used for hot-card, TMS and other exchanges
     *
     * @return
     */
    GeneralMessageClassBuilder fileAction();

    /**
     * Reverses the action of a previous authorization.
     *
     * @return
     */
    GeneralMessageClassBuilder reversal();

    /**
     * Transmits settlement information label.
     *
     * @return
     */
    GeneralMessageClassBuilder reconciliation();

    /**
     * Transmits administrative advice.
     *
     * @return
     */
    GeneralMessageClassBuilder administrative();

    GeneralMessageClassBuilder feeCollection();

    /**
     * Used for secure key exchange, logon, echo test and other network functions
     *
     * @return
     */
    GeneralMessageClassBuilder networkManagement();
}
