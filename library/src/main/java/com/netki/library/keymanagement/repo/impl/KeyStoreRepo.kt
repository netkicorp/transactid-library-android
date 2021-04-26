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

class KeyStoreRepo(masterKeyAlias: String?, applicationContext: Context?) : KeyManagementRepo {

    private lateinit var masterKeyAlias: String
    private lateinit var applicationContext: Context

    init {
        if (masterKeyAlias != null && applicationContext != null) {
            this.masterKeyAlias = masterKeyAlias
            this.applicationContext = applicationContext
            val keyStore = KeyStore.getInstance(KEYSTORE_TYPE).apply {
                load(null)
            }
            val secretKeyEntry = keyStore.getEntry(masterKeyAlias, null)
            if (secretKeyEntry == null) {
                generateSecretKey(masterKeyAlias)
            }

            val certsFolder = File("${applicationContext.filesDir}/$CERTS_SCHEMA")
            if (!certsFolder.exists()) {
                certsFolder.mkdirs()
            }

            val privateKeysFolder = File("${applicationContext.filesDir}/$PRIVATE_KEY_SCHEMA")
            if (!privateKeysFolder.exists()) {
                privateKeysFolder.mkdirs()
            }
        }
    }

    private fun generateSecretKey(keyStoreAlias: String): SecretKey {
        val keyGenerator = getInstance(KeyProperties.KEY_ALGORITHM_AES, KEYSTORE_TYPE)
        val spec = KeyGenParameterSpec
            .Builder(keyStoreAlias, KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT)
            .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            .build()

        keyGenerator.init(spec)
        return keyGenerator.generateKey()
    }

    /**
     * {@inheritDoc}
     */
    override fun storeCertificatePem(certificateId: String, certificatePem: String) =
        storeFile(certificateId, certificatePem, CERTS_SCHEMA)

    /**
     * {@inheritDoc}
     */
    override fun storePrivateKeyPem(privateKeyId: String, privateKeyPem: String) =
        storeFile(privateKeyId, privateKeyPem, PRIVATE_KEY_SCHEMA)


    private fun storeFile(fileId: String, content: String, schema: String) {
        val encryptedFile = EncryptedFile.Builder(
            File("${applicationContext.filesDir}/$schema", fileId),
            applicationContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val fileContent = content.toByteArray(StandardCharsets.UTF_8)
        encryptedFile.openFileOutput().apply {
            write(fileContent)
            flush()
            close()
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun fetchCertificatePem(certificateId: String) = fetchFileContent(certificateId, CERTS_SCHEMA)

    /**
     * {@inheritDoc}
     */
    override fun fetchPrivateKeyPem(privateKeyId: String) = fetchFileContent(privateKeyId, PRIVATE_KEY_SCHEMA)

    private fun fetchFileContent(fileId: String, schema: String): String? {
        val file = File("${applicationContext.filesDir}/$schema", fileId)
        if (!file.exists()) {
            return null
        }
        val decryptedFile = EncryptedFile.Builder(
            file,
            applicationContext,
            masterKeyAlias,
            EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB
        ).build()

        val inputStream = decryptedFile.openFileInput()
        val byteArrayOutputStream = ByteArrayOutputStream()
        var nextByte: Int = inputStream.read()
        while (nextByte != -1) {
            byteArrayOutputStream.write(nextByte)
            nextByte = inputStream.read()
        }

        return String(byteArrayOutputStream.toByteArray())
    }
}
