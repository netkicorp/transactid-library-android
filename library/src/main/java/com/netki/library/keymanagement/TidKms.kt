package com.netki.library.keymanagement

import android.content.Context
import com.netki.exceptions.*
import com.netki.library.keymanagement.config.KeyManagementFactory
import com.netki.library.keymanagement.main.KeyManagement
import com.netki.model.AttestationCertificate
import com.netki.model.AttestationInformation
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.security.PrivateKey
import java.security.cert.X509Certificate

/**
 * Access the key management system.
 */
object TidKms : KoinComponent {

    /**
     * Method to initialize the KeyManagement system.
     * All the parameters are optional depending the functions that want to be used.
     *
     * @param authorizationCertificateProviderKey to authorize the connection to the certificate provider.
     * @param authorizationCertificateProviderUrl to connect to the certificate provider.
     */
    @JvmStatic
    @JvmOverloads
    fun init(
        authorizationCertificateProviderKey: String = "",
        authorizationCertificateProviderUrl: String = ""
    ) {
        KeyManagementFactory.init(
            authorizationCertificateProviderKey,
            authorizationCertificateProviderUrl
        )
    }

    private val keyManagement by inject<KeyManagement>()

    /**
     * Generate a key pair for each one of the attestations provided.
     *
     * @param attestationsInformation list of attestations with their corresponding data.
     * @throws CertificateProviderException if there is an error creating the certificates.
     * @throws CertificateProviderUnauthorizedException if there is an error with the authorization to connect to the provider.
     */
    @Throws(CertificateProviderException::class, CertificateProviderUnauthorizedException::class)
    fun generateCertificates(attestationsInformation: List<AttestationInformation>) {
        keyManagement.generateCertificates(attestationsInformation)
    }
}
