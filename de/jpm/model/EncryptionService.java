/*
 * jPassMaster
 * 
 * A free tool to Manage your Accounts.
 * And Sync them with your dropbox account. 
 * The Software is made in hope to be usefull to you as it is for me.
 * 
 * This class is a part of jPass Master. 
 * EMail: jpassmaster@thorstenweiskopf.de
 * 
 * 
 *   Copyright (C) 2012  Thortsten Weiskopf
 *
 *    This program is free software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as published by
 *    the Free Software Foundation, either version 3 of the License, or
 *    (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package de.jpm.model;

/*
 *
 * This file is from a part of Universal Password Manager.
 *   
 * Universal Password Manager is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * Universal Password Manager is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

*/

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import org.bouncycastle.crypto.BufferedBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoException;
import org.bouncycastle.crypto.PBEParametersGenerator;
import org.bouncycastle.crypto.digests.SHA256Digest;
import org.bouncycastle.crypto.engines.AESEngine;
import org.bouncycastle.crypto.generators.PKCS12ParametersGenerator;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.paddings.PKCS7Padding;
import org.bouncycastle.crypto.paddings.PaddedBufferedBlockCipher;


/**
 *
 * @author Thorty.w
 */
public class EncryptionService {

    private static final String randomAlgorithm = "SHA1PRNG";
    /**
     *
     */
    public static final int SALT_LENGTH = 8;

    private byte[] salt;
    private BufferedBlockCipher encryptCipher;
    private BufferedBlockCipher decryptCipher;

    /**
     *
     * @param password
     * @throws CryptoException
     * @throws NoSuchAlgorithmException
     */
    public EncryptionService(char[] password) throws CryptoException, NoSuchAlgorithmException {

            this.salt = generateSalt();

        initCipher(password);
    }

    /**
     *
     * @param password
     * @param salt
     */
    public EncryptionService(char[] password, byte[] salt) {
        this.salt = salt;
        initCipher(password);
    }

    /**
     *
     * @param password
     */
    public void initCipher(char[] password) {
        PBEParametersGenerator keyGenerator = new PKCS12ParametersGenerator(new SHA256Digest());
        keyGenerator.init(PKCS12ParametersGenerator.PKCS12PasswordToBytes(password), salt, 20);
        CipherParameters keyParams = keyGenerator.generateDerivedParameters(256, 128);
        
        encryptCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        encryptCipher.init(true, keyParams);
        decryptCipher = new PaddedBufferedBlockCipher(new CBCBlockCipher(new AESEngine()), new PKCS7Padding());
        decryptCipher.init(false, keyParams);
    }

    private byte[] generateSalt() throws NoSuchAlgorithmException {
        SecureRandom saltGen = SecureRandom.getInstance(randomAlgorithm);
        byte pSalt[] = new byte[SALT_LENGTH];
        saltGen.nextBytes(pSalt);
        return pSalt;
    }

    /**
     *
     * @param plainText
     * @return
     * @throws CryptoException
     */
    public byte[] encrypt(byte[] plainText) throws CryptoException {
        byte[] encryptedBytes = new byte[encryptCipher.getOutputSize(plainText.length)];
        int outputLength = encryptCipher.processBytes(plainText, 0, plainText.length, encryptedBytes, 0);
    
            outputLength += encryptCipher.doFinal(encryptedBytes, outputLength);
    


        byte[] results = new byte[outputLength];
        System.arraycopy(encryptedBytes, 0, results, 0, outputLength);
        return results;
    }
    
    /**
     *
     * @param encryptedBytes
     * @return
     * @throws CryptoException
     */
    public byte[] decrypt(byte[] encryptedBytes) throws CryptoException {
        byte[] decryptedBytes = new byte[decryptCipher.getOutputSize(encryptedBytes.length)];
        int outputLength = decryptCipher.processBytes(encryptedBytes, 0, encryptedBytes.length, decryptedBytes, 0);
  
            outputLength += decryptCipher.doFinal(decryptedBytes, outputLength);


        byte[] results = new byte[outputLength];
        System.arraycopy(decryptedBytes, 0, results, 0, outputLength);
        return results;
    }

    /**
     *
     * @return
     */
    public byte[] getSalt() {
        return salt;
    }
    
    /**
     *
     * @param salt
     */
    public void setSalt(byte[] salt){
        this.salt = salt;
    }

}
