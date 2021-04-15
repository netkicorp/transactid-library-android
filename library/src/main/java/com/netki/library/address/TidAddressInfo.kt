package com.netki.library.address

import com.netki.library.address.info.config.AddressFactory
import com.netki.exceptions.AddressProviderErrorException
import com.netki.exceptions.AddressProviderUnauthorizedException
import com.netki.library.address.info.main.Address
import com.netki.model.AddressCurrency
import com.netki.model.AddressInformation
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * Fetch the detailed information about an address.
 */
object TidAddressInfo : KoinComponent {

    /**
     * Method initialize the Address Information module.
     *
     * @param authorizationKey Key to connect to an external address provider.
     */
    @JvmStatic
    @JvmOverloads
    fun init(authorizationKey: String) {
        AddressFactory.init(authorizationKey)
    }

    private val address by inject<Address>()

    /**
     * Fetch the information of a given address.
     *
     * @param currency of the address.
     * @param address to fetch the information.
     * @throws AddressProviderErrorException if there is an error fetching the information from the provider.
     * @throws AddressProviderUnauthorizedException if there is an error with the authorization to connect to the provider.
     * @return information of the address.
     */
    @Throws(AddressProviderErrorException::class, AddressProviderUnauthorizedException::class)
    fun getAddressInformation(currency: AddressCurrency, address: String): AddressInformation =
        this.address.getAddressInformation(currency, address)
}
