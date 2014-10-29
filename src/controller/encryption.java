package controller;

public class encryption {

	private byte key = (byte) 3.1470;

	public byte[] xorEncrypt (String gson) {

		byte[] input = gson.getBytes();

		byte[] encrypted = input;

		for (int i=0; i<encrypted.length; i++)
		{
			encrypted[i] = (byte) (encrypted[i] ^ key);
		}

		return encrypted;
	}

	public String xorDecrypt (byte[] reply) {

		byte[] decrypted = reply;

		for (int i=0; i<decrypted.length; i++)
		{
			decrypted[i] = (byte) (decrypted[i] ^ key);
		}

		return decrypted.toString();
	}
}
