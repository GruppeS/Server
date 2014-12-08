package controller;

import config.Configurations;

public class Encryption {

	private Configurations cf = new Configurations();
	private double key = Double.parseDouble(cf.getEncryptionkey());
	private byte keyByte = (byte) key;

	/**
	 * Encrypts a json string using XOR
	 * @param gsonString
	 * @return encrypted
	 */
	public byte[] encrypt(String json) {
		byte[] input = json.getBytes();
		byte[] encrypted = input;
		for (int i = 0; i < encrypted.length; i++)
			encrypted[i] = (byte) (encrypted[i] ^ keyByte);

		return encrypted;
	}

	/**
	 * Decrypts a byte[] using XOR
	 * @return decrypted
	 */
	public String decrypt(byte[] encrypted) {
		for(int i = 0 ; i<encrypted.length ; i++)
		{
			encrypted[i] = (byte)(encrypted[i]^keyByte);
		}
		String decrypted = new String(encrypted).trim();
		return decrypted;
	}
}
