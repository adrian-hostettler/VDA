package util;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 
 * @author Adrian Hostettler
 *
 * @version 1.0
 *
 * @category util
 * 
 * Quelle Code-Struktur: https://github.com/ISchwarz23/FritzBox-API/blob/master/src/main/java/de/ingo/fritzbox/utils/Encryption.java
 * 
 */
public class Encryption {
	/**
	 * Ergänzt den String mit einem MD5 Hash zur Verschlüsselung
	 * @param stringToHash
	 * @return bigInt
	 * @throws NoSuchAlgorithmException
	 */
	public static String hashMD5(final String stringToHash) throws NoSuchAlgorithmException {
		MessageDigest digest;
		digest = MessageDigest.getInstance("MD5");
		digest.update(stringToHash.getBytes());

		final byte byteData[] = digest.digest();
		final BigInteger bigInt = new BigInteger(1, byteData);

		return bigInt.toString(16);
	}

}
