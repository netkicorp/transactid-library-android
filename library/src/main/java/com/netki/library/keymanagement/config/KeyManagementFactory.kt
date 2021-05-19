package com.netki.library.keymanagement.config

import com.netki.keygeneration.config.KeyGenerationFactory
import com.netki.keygeneration.main.KeyGeneration
import com.netki.library.keymanagement.main.KeyManagement
import com.netki.library.keymanagement.main.impl.KeyManagementNetki
import com.netki.library.keymanagement.service.KeyManagementService
import com.netki.library.keymanagement.service.impl.KeyManagementNetkiService
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.security.KeyStore

/**
 * Factory to initialize KeyManagement dependencies.
 */
object KeyManagementFactory {

    /**
     * Initialize the KeyManagement dependencies.
     *
     * @param authorizationCertificateProviderKey to authorize the connection to the certificate provider.
     * @param authorizationCertificateProviderUrl to connect to the certificate provider.
     */
    fun init(
        authorizationCertificateProviderKey: String,
        authorizationCertificateProviderUrl: String
    ) {
        val keyManagementModule = module {
            single {
                KeyGenerationFactory.getInstance(
                    authorizationCertificateProviderKey,
                    authorizationCertificateProviderUrl
                ) as KeyGeneration
            }

            single {
                KeyStore.getInstance("AndroidKeyStore").apply {
                    load(null)
                }
            }

            single { KeyManagementNetkiService(get(), get()) as KeyManagementService }

            single { KeyManagementNetki(get()) as KeyManagement }
        }

        startKoin {
            modules(keyManagementModule)
        }
    }
}
