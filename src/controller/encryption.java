package controller;

import java.io.IOException;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Encryption {

	private String encryptionKey = "cdc63491uAf24938";
	private char[] key = {'j','a','v','a'};
	int keyLength = key.length;
	
	public String xorEncrypt (String json) {

		char[] jsonArray = json.toCharArray();
		
		int jsonLength = jsonArray.length;
		char[] toEncrypt = new char[jsonLength];
		
		for (int i=0; i<jsonLength; i++)
		{
			toEncrypt[i] = (char) (jsonArray[i] ^ key[i%keyLength]);
		}
		
		String encrypted = new String(toEncrypt);
		
		return new String(new BASE64Encoder().encodeBuffer(encrypted.getBytes()));
	}

	public String xorDecrypt (String json) {
		
		BASE64Decoder decoder = new BASE64Decoder();
		
		try {
			json = new String(decoder.decodeBuffer(json));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		char[] jsonArray = json.toCharArray();
		
		int jsonLength = jsonArray.length;
		
		char[] toDecrypt = new char[jsonLength];
		
		for (int i=0; i<jsonLength; i++)
		{
			toDecrypt[i] = (char) (jsonArray[i] ^ key[i%keyLength]);
		}
		
		return new String(toDecrypt);
	}

	public String aesEncrypt(String password) throws Exception {

		Key aesKey = new SecretKeySpec(encryptionKey.getBytes(), "AES");
		Cipher cipher = Cipher.getInstance("AES");

		cipher.init(Cipher.ENCRYPT_MODE, aesKey);
		byte[] encrypted = cipher.doFinal(password.getBytes());

		return new String(encrypted);
	}
}