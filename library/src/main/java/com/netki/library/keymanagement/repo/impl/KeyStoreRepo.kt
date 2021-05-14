package com.netki.library.keymanagement.repo.impl

import android.content.Context
import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import androidx.security.crypto.EncryptedFile
import com.netki.library.keymanagement.repo.KeyManagementRepo
import java.io.ByteArrayOutputStream
import java.io.File
import java.nio.charset.StandardCharsets
import java.security.KeyStore
import javax.crypto.KeyGenerator.*
import javax.crypto.SecretKey

private const val CERTS_SCHEMA = "certs/user/attestations/"
private const val PRIVATE_KEY_SCHEMA = "keys/user/priv/"
private const val KEYSTORE_TYPE = "AndroidKeyStore"

class KeyStoreRepo()  {

}
