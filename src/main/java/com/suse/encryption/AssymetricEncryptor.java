package com.suse.encryption;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class AssymetricEncryptor {

    private Cipher cipher;

    public AssymetricEncryptor() throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.cipher = Cipher.getInstance("RSA");
    }

    public void encryptText(final PrivateKey privateKey, final String path) throws InvalidKeyException, IOException {
        final String sampleText = "This is a sample text";
        byte[] encryptedText = null;
        this.cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        try {
            encryptedText = this.cipher.doFinal(sampleText.getBytes());
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        }
        writeToFile(encryptedText, path);
    }

    public PrivateKey getPrivate(String filename) throws Exception {
        final byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        final PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        final KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

     public PublicKey getPublic(String filename) throws Exception {
        final byte[] keyBytes = Files.readAllBytes(new File(filename).toPath());
        final X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    private void writeToFile(final byte[] encryptedText, final String path) throws IOException {
        final File file = new File(path+"encryptedText");
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        try {
            fileOutputStream.write(encryptedText);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            fileOutputStream.close();
        }
    }

    public void decrypt(final String path, final PublicKey key) throws InvalidKeyException, IOException, BadPaddingException, IllegalBlockSizeException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        final byte[] encryptedText = Files.readAllBytes(new File(path).toPath());
        byte[] decryptedText = this.cipher.doFinal(encryptedText);
        final String decryptTxt = new String(decryptedText);
        System.out.println(decryptTxt);
    }
}
