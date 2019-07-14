package com.suse.encryption;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class KeyGeneratorTest {

    private static KeyGenerator keyGenerator;

    @BeforeClass()
    public static void setUp(){
        try {
            keyGenerator = new KeyGenerator(2048);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    @Test()
    public void testKeyGeneration() throws IOException {
        keyGenerator.createKeys();
        keyGenerator.writeKeysToFolder("src/main/resources/publicKey", keyGenerator.getPublicKey().getEncoded());
        keyGenerator.writeKeysToFolder("src/main/resources//privateKey", keyGenerator.getPrivateKey().getEncoded());
    }
}
