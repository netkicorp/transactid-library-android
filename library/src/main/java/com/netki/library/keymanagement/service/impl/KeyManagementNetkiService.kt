package com.netki.library.keymanagement.service.impl

import com.netki.exceptions.*
import com.netki.exceptions.ExceptionInformation.CERTIFICATE_INFORMATION_NOT_UNIQUE_IDENTIFIER
import com.netki.exceptions.ExceptionInformation.CERTIFICATE_INFORMATION_STRING_NOT_CORRECT_ERROR_PROVIDER
import com.netki.exceptions.ExceptionInformation.KEY_PAIR_EXISITING
import com.netki.extensions.isAlphaNumeric
import com.netki.keygeneration.main.KeyGeneration
import com.netki.keygeneration.util.toPrincipal
import com.netki.library.keymanagement.service.KeyManagementService
import com.netki.model.AttestationCertificate
import com.netki.model.AttestationInformation
import com.netki.security.Certificate
import com.netki.security.Parameters.KEY_ALGORITHM
import com.netki.security.toCertificate
import com.netki.security.toPemFormat
import com.netki.security.toPrivateKey
import java.security.KeyPair
import java.security.KeyPairGenerator
import java.security.KeyStore


internal class KeyManagementNetkiService(
    private val keyGeneration: KeyGeneration,
    private val keyStore: KeyStore
) : KeyManagementService {

    /**
     * {@inheritDoc}
     */
    override fun generateCertificates(attestationsInformation: List<AttestationInformation>) {
        validateAttestationData(attestationsInformation)

        val keyPair = generateKeyPair()
        keyPair.private

        val attestationsCertificate = attestationsInformation.map {
            AttestationCertificate(
                attestation = it.attestation,
                csr = Certificate.csrObjectToPem(
                    Certificate.generateCSR(it.attestation.toPrincipal(it.data, it.ivmsConstraint), keyPair)
                ),
                publicKeyPem = keyPair.public.toPemFormat(),
                identifier = it.identifier
            )
        }

        val certificates = keyGeneration.generateCertificates(attestationsCertificate)

        val certificatesGenerated = mutableListOf<AttestationCertificate>()

        attestationsCertificate.forEach { attestationCertificate ->
            certificates.forEach { certificate ->
                if (certificate.attestation == attestationCertificate.attestation) {
                    certificatesGenerated.add(
                        AttestationCertificate(
                            attestation = attestationCertificate.attestation,
                            certificatePem = certificate.certificatePem,
                            csr = attestationCertificate.csr,
                            publicKeyPem = keyPair.public.toPemFormat(),
                            identifier = attestationCertificate.identifier,
                            privateKeyPem = keyPair.private.toPemFormat()
                        )
                    )
                }
            }
        }

        storeAttestation(certificatesGenerated)
    }

    private fun validateAttestationData(attestationsInformation: List<AttestationInformation>) {
        attestationsInformation.forEach { attestationInformation ->
            if (!attestationInformation.data.isAlphaNumeric()) {
                throw CertificateProviderException(
                    String.format(
                        CERTIFICATE_INFORMATION_STRING_NOT_CORRECT_ERROR_PROVIDER,
                        attestationInformation.data,
                        attestationInformation.attestation
                    )
                )
            }
            if (attestationInformation.identifier.isNullOrBlank()) {
                throw CertificateProviderException(
                    String.format(
                        CERTIFICATE_INFORMATION_NOT_UNIQUE_IDENTIFIER,
                        attestationInformation.data,
                        attestationInformation.attestation
                    )
                )
            }

            if (keyStore.getEntry(attestationInformation.identifier, null) != null) {
                throw CertificateProviderException(
                    String.format(KEY_PAIR_EXISITING, attestationInformation.identifier)
                )
            }

        }
    }

    private fun generateKeyPair(): KeyPair {
        val kpg = KeyPairGenerator.getInstance(KEY_ALGORITHM)
        kpg.initialize(2048)
        return kpg.generateKeyPair()
    }

    private fun storeAttestation(attestationsCertificate: List<AttestationCertificate>) {
        attestationsCertificate.forEach { attestationCertificate ->
            keyStore.setKeyEntry(
                attestationCertificate.identifier,
                attestationCertificate.privateKeyPem?.toPrivateKey(),
                null,
                arrayOf(attestationCertificate.certificatePem?.toCertificate())
            )
        }
    }
}
