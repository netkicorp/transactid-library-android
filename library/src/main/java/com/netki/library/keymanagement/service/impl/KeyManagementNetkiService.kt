package com.netki.library.keymanagement.service.impl

import com.netki.keygeneration.main.KeyGeneration
import com.netki.library.keymanagement.service.KeyManagementService
import com.netki.model.AttestationInformation
import java.security.PrivateKey
import java.security.cert.X509Certificate

internal class KeyManagementNetkiService(
    private val keyGeneration: KeyGeneration
) : KeyManagementService {

    /**
     * {@inheritDoc}
     */
    override fun generateCertificates(attestationsInformation: List<AttestationInformation>) =
        keyGeneration.generateCertificates(attestationsInformation)

    /**
     * {@inheritDoc}
     */
    override fun storeCertificatePem(certificatePem: String): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun storeCertificate(certificate: X509Certificate): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun storePrivateKeyPem(privateKeyPem: String): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun storePrivateKey(privateKey: PrivateKey): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun fetchCertificatePem(certificateId: String): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun fetchCertificate(certificateId: String): X509Certificate {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun fetchPrivateKeyPem(privateKeyId: String): String {
        TODO("Not yet implemented")
    }

    /**
     * {@inheritDoc}
     */
    override fun fetchPrivateKey(privateKeyId: String): PrivateKey {
        TODO("Not yet implemented")
    }
}
