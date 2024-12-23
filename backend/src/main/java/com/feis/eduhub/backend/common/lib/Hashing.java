package com.feis.eduhub.backend.common.lib;

import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/**
 * Utility class for strings hashing and verification using Argon2id hashing
 * algorithm (Spring Security Crypto & BouncyCastle).
 * 
 * Configured with 16 bytes
 * salt length, 32 bytes hash length, 4
 * threads parallelism, 64MB memory (65536KB), and 4 iterations.
 * 
 * @see Argon2PasswordEncoder
 */
public class Hashing {
    // NOTE: 16 bytes salt, 32 bytes hash, 2 threads, 64MB used, 4 iterations
    private final static Argon2PasswordEncoder argon2PasswordEncoder = new Argon2PasswordEncoder(16, 32, 4, 65536, 4);

    private Hashing() {
    }

    public static String hash(CharSequence str) {
        return argon2PasswordEncoder.encode(str);
    }

    public static boolean verify(CharSequence rawStr, String hashedStr) {
        return argon2PasswordEncoder.matches(rawStr, hashedStr);
    }
}