package com.netki.library.bip75.service.impl

import com.netki.library.bip75.service.Bip75Service
import com.netki.message.main.Message
import com.netki.model.*

/**
 * {@inheritDoc}
 */
internal class Bip75ServiceNetki(
    private val message: Message
) : Bip75Service {

    /**
     * {@inheritDoc}
     */
    override fun createInvoiceRequest(invoiceRequestParameters: InvoiceRequestParameters) =
        message.createInvoiceRequest(invoiceRequestParameters)

    /**
     * {@inheritDoc}
     */
    override fun parseInvoiceRequest(invoiceRequestBinary: ByteArray, recipientParameters: RecipientParameters?) =
        message.parseInvoiceRequest(invoiceRequestBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun parseInvoiceRequestWithAddressesInfo(
        invoiceRequestBinary: ByteArray,
        recipientParameters: RecipientParameters?
    ) = message.parseInvoiceRequestWithAddressesInfo(invoiceRequestBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun isInvoiceRequestValid(
        invoiceRequestBinary: ByteArray,
        recipientParameters: RecipientParameters?
    ): Boolean {
        return message.isInvoiceRequestValid(invoiceRequestBinary, recipientParameters)
    }

    /**
     * {@inheritDoc}
     */
    override fun createPaymentRequest(paymentRequestParameters: PaymentRequestParameters, identifier: String) =
        message.createPaymentRequest(paymentRequestParameters, identifier)

    /**
     * {@inheritDoc}
     */
    override fun parsePaymentRequest(paymentRequestBinary: ByteArray, recipientParameters: RecipientParameters?) =
        message.parsePaymentRequest(paymentRequestBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun parsePaymentRequestWithAddressesInfo(
        paymentRequestBinary: ByteArray,
        recipientParameters: RecipientParameters?
    ) = message.parsePaymentRequestWithAddressesInfo(paymentRequestBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun isPaymentRequestValid(
        paymentRequestBinary: ByteArray,
        recipientParameters: RecipientParameters?
    ): Boolean {
        return message.isPaymentRequestValid(paymentRequestBinary, recipientParameters)
    }

    /**
     * {@inheritDoc}
     */
    override fun createPayment(paymentParameters: PaymentParameters, identifier: String) =
        message.createPayment(paymentParameters, identifier)

    /**
     * {@inheritDoc}
     */
    override fun parsePayment(paymentBinary: ByteArray, recipientParameters: RecipientParameters?) =
        message.parsePayment(paymentBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun isPaymentValid(paymentBinary: ByteArray, recipientParameters: RecipientParameters?): Boolean {
        return message.isPaymentValid(paymentBinary, recipientParameters)
    }

    /**
     * {@inheritDoc}
     */
    override fun createPaymentAck(paymentAckParameters: PaymentAckParameters, identifier: String) =
        message.createPaymentAck(paymentAckParameters, identifier)

    /**
     * {@inheritDoc}
     */
    override fun parsePaymentAck(paymentAckBinary: ByteArray, recipientParameters: RecipientParameters?) =
        message.parsePaymentAck(paymentAckBinary, recipientParameters)

    /**
     * {@inheritDoc}
     */
    override fun isPaymentAckValid(paymentAckBinary: ByteArray, recipientParameters: RecipientParameters?): Boolean {
        return message.isPaymentAckValid(paymentAckBinary, recipientParameters)
    }

    /**
     * {@inheritDoc}
     */
    override fun changeStatusProtocolMessage(
        protocolMessage: ByteArray,
        statusCode: StatusCode,
        statusMessage: String
    ) = message.changeStatusProtocolMessage(protocolMessage, statusCode, statusMessage)

    /**
     * {@inheritDoc}
     */
    override fun getProtocolMessageMetadata(protocolMessage: ByteArray) =
        message.getProtocolMessageMetadata(protocolMessage)
}
