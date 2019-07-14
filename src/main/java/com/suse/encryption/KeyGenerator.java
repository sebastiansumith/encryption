package com.suse.encryption;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

public class KeyGenerator {

    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyGenerator(final int keylength) throws NoSuchAlgorithmException {
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(keylength);
    }

    public void createKeys(){
        this.keyPair = this.keyPairGenerator.generateKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return this.privateKey;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public void writeKeysToFolder(final String path, final byte[] key) throws IOException {
        final File file = new File(path);
        file.getParentFile().mkdirs();

        final FileOutputStream fos = new FileOutputStream(file);
        try {
            fos.write(key);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fos.close();
        }
    }
}
