package com.netki.library.keymanagement.service

import com.netki.exceptions.*
import com.netki.model.AttestationCertificate
import com.netki.model.AttestationInformation
import java.security.PrivateKey
import java.security.cert.X509Certificate

internal interface KeyManagementService {

    /**
     * Generate a key pair for each one of the attestations provided.
     *
     * @param attestationsInformation list of attestations with their corresponding data.
     * @throws CertificateProviderException if there is an error creating the certificates.
     * @throws CertificateProviderUnauthorizedException if there is an error with the authorization to connect to the provider.
     */
    @Throws(CertificateProviderException::class, CertificateProviderUnauthorizedException::class)
    fun generateCertificates(attestationsInformation: List<AttestationInformation>)
}
