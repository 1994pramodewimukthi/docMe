package edu.esoft.finalproject.DocMe.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;

// KEY SPECIFICATIONS
// EXCEPTIONS

@Component
public class Cryptography {

    private static final Logger LOGGER = LoggerFactory.getLogger(Cryptography.class);
    private Cipher dcipher;
    private Cipher ecipher;
    private String encryptionKey = "StargateFX";

    /**
     * Default constructor
     */
    public Cryptography() throws Exception {
        this.initData();
    }


    public Cryptography(String encryptionKey) throws Exception {
        this.encryptionKey = encryptionKey.trim();
        this.initData();
    }


    public String decrypt(String str) throws Exception {
        try {
            // Decode base64 to get bytes
            byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);
            // Decrypt
            byte[] utf8 = dcipher.doFinal(dec);
            // Decode using utf-8
            return new String(utf8, "UTF8");
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
            LOGGER.error("Cryptography.decrypt() " + e.getMessage());
            throw new Exception(e);
        }
    }


    public String encrypt(String str) throws Exception {
        try {
            // Encode the string into bytes using utf-8
            byte[] utf8 = str.getBytes("UTF8");
            // Encrypt
            byte[] enc = ecipher.doFinal(utf8);
            // Encode bytes to base64 to get a string
            return new sun.misc.BASE64Encoder().encode(enc);
        } catch (BadPaddingException | IllegalBlockSizeException | UnsupportedEncodingException e) {
            LOGGER.error("Cryptography.encrypt() " + e.getMessage());
            throw new Exception(e);
        }
    }

    /**
     * Initialize data
     */
    private void initData() throws Exception {
        // 8-bytes Salt
        byte[] salt = {(byte) 0xA9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
                (byte) 0x56, (byte) 0x34, (byte) 0xE3, (byte) 0x03};

        // Iteration count
        int iterationCount = 19;

        try {
            KeySpec keySpec = new PBEKeySpec(encryptionKey.toCharArray(), salt, iterationCount);
            SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);

            ecipher = Cipher.getInstance(key.getAlgorithm());
            dcipher = Cipher.getInstance(key.getAlgorithm());

            // Prepare the parameters to the cipthers
            AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

            ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
            dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);

        } catch (InvalidAlgorithmParameterException | InvalidKeySpecException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException e) {
            LOGGER.error("Cryptography.initData() " + e.getMessage());
            throw new Exception(e);
        }
    }

    public static void main(String[] args) {

        try {
            Cryptography cryptography = new Cryptography();
            System.out.println(cryptography.decrypt("kfXwYFtS5q0RugMwxyvrpA=="));
            System.out.println(cryptography.encrypt("123456"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
