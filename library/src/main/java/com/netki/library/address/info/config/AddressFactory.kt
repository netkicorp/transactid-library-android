package com.netki.library.address.info.config

import com.netki.address.info.config.AddressInformationFactory
import com.netki.address.info.main.AddressInformationProvider
import com.netki.library.address.info.main.Address
import com.netki.library.address.info.main.impl.AddressNetki
import com.netki.library.address.info.service.AddressService
import com.netki.library.address.info.service.impl.AddressNetkiService
import org.koin.core.context.startKoin
import org.koin.dsl.module

/**
 * Factory to initialize Address dependencies.
 *
 */
internal object AddressFactory {

    /**
     * Initialize the address module.
     *
     */
    fun init(authorizationKey: String) {
        val addressModule = module {
            single {
                AddressInformationFactory.getInstance(authorizationKey) as AddressInformationProvider
            }
            single {
                AddressNetkiService(get()) as AddressService
            }
            single {
                AddressNetki(get()) as Address
            }
        }
        startKoin {
            modules(addressModule)
        }
    }
}
