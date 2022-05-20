package com.kdz.jarvis.network.signing

import java.lang.StringBuilder
import java.math.BigInteger
import java.security.MessageDigest

class MarvelApiKeyGenerator(
    val publicKey: String,
    val privateKey: String
) {

    fun generate(timeStamp: Long) = generate(timeStamp.toString())

    fun generate(timeStamp: String): String {
        val md = MessageDigest.getInstance("MD5")
        val input = buildInput(timeStamp)
        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
    }

    private fun buildInput(timeStamp: String): String {
        return StringBuilder(timeStamp).append(privateKey).append(publicKey).toString()
    }
}