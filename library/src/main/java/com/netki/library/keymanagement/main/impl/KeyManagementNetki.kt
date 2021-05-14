package com.netki.library.keymanagement.main.impl

import com.netki.library.keymanagement.main.KeyManagement
import com.netki.library.keymanagement.service.KeyManagementService
import com.netki.model.AttestationInformation
import java.security.PrivateKey
import java.security.cert.X509Certificate

internal class KeyManagementNetki(private val keyManagementService: KeyManagementService) : KeyManagement {

    /**
     * {@inheritDoc}
     */
    override fun generateCertificates(attestationsInformation: List<AttestationInformation>) {
        keyManagementService.generateCertificates(attestationsInformation)
    }
}
