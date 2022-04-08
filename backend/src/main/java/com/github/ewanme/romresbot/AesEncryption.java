package com.github.ewanme.romresbot;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.util.Arrays;

public class AesEncryption {
	
	private static SecretKeySpec secretKey;
	private static byte[] key;
	
	public static void setKey(final String newKey) {
		try {
			key = newKey.getBytes("UTF-8");
			MessageDigest sha = MessageDigest.getInstance("SHA-1");
			key = sha.digest(key);
			key = Arrays.copyOf(key, 16);
			secretKey = new SecretKeySpec(key, "AES");
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static String encrypt(final String toEncrypt, final String secret) {
		try {
			setKey(secret);
			Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKey);
			
			return Base64.getEncoder().encodeToString(cipher.doFinal(toEncrypt.getBytes("UTF-8")));
		} catch (Exception e) {
			System.out.println("Could not encrypt: " + e.toString());
		}
		
		return null;
	}
	
	public static String decrypt(final String toDecrypt, final String secret) {
	    try {
	      setKey(secret);
	      Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
	      cipher.init(Cipher.DECRYPT_MODE, secretKey);
	      
	      return new String(cipher.doFinal(Base64.getDecoder().decode(toDecrypt)));
	    } catch (Exception e) {
	      System.out.println("Could not decrypt: " + e.toString());
	    }
	    
	    return null;
	  }
}
