package com.solution_challenge.keinearmut

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashUtils {
    fun hashString(input: String): String? {
        return try {
            val digest = MessageDigest.getInstance("SHA-256")
            val hashBytes = digest.digest(input.toByteArray())
            val builder = StringBuilder()
            for (hashByte in hashBytes) {
                builder.append(String.format("%02x", hashByte))
            }
            builder.toString()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
            null
        }
    }
}