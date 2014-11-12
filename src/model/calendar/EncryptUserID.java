package model.calendar;

import java.security.MessageDigest;

public class EncryptUserID {

	/**
	 * Constant cipher seed - DO NOT CHANGE.
	 * http://www.miraclesalad.com/webtools/md5.php - Du kan her saette userid foerst og derefter hashkey for at teste
	 */
	private final String HASHKEY = "v.eRyzeKretW0r_t";
	private static MessageDigest digester;

	public EncryptUserID() {
		try {
			digester = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Enkryptere en tekst streng som bliver parset til funktionen
	public String crypt(String str) {
		if (str == null || str.length() == 0) {
			throw new IllegalArgumentException("Error");
		}

		String stringKey = str + HASHKEY;
		digester.update(stringKey.getBytes());
		byte[] hash = digester.digest();
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < hash.length; i++) {
			if ((0xff & hash[i]) < 0x10) {
				hexString.append("0" + Integer.toHexString((0xFF & hash[i])));
			} else {
				hexString.append(Integer.toHexString(0xFF & hash[i]));
			}
		}
		return hexString.toString();
	}

}