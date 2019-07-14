package com.suse.encryption;

import org.junit.BeforeClass;
import org.junit.Test;

import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class AssymetricEncryptorTest {

    private static AssymetricEncryptor encryptor = null;

    @BeforeClass()
    public static void setUp() throws NoSuchAlgorithmException, NoSuchPaddingException {
        encryptor = new AssymetricEncryptor();
    }

    @Test()
    public void testEncryption() throws Exception {
        final PrivateKey key = encryptor.getPrivate("src/main/resources/privateKey");
        encryptor.encryptText(key, "src/main/resources/");
        final PublicKey publicKey = encryptor.getPublic("src/main/resources/publicKey");
        encryptor.decrypt("src/main/resources/encryptedText", publicKey);
    }
}
