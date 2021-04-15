package com.netki.library.bip75.config

import com.netki.bip75.main.Bip75
import com.netki.bip75.main.impl.Bip75Netki
import com.netki.library.bip75.service.Bip75Service
import com.netki.library.bip75.service.impl.Bip75ServiceNetki
import com.netki.message.config.MessageFactory
import com.netki.message.main.Message
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.security.Security

/**
 * Factory to initialize Bip75 dependencies.
 */
internal object Bip75Factory {

    /**
     * Initialize Bip75 dependencies.
     *
     * @param authorizationKey pass this parameter if address information will be required.
     */
    @JvmOverloads
    fun init(authorizationKey: String? = null) {
        val bip75Module = module {
            single {
                MessageFactory.getInstance(authorizationKey) as Message
            }
            single {
                Bip75ServiceNetki(get()) as Bip75Service
            }
            single {
                Bip75Netki(get()) as Bip75
            }
        }
        startKoin {
            modules(bip75Module)
        }
    }
}
