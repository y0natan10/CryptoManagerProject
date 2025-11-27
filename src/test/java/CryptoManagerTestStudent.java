import junit.framework.TestCase;

/**
 * This class provides student-developed JUnit test cases for the CryptoManager
 * class. It is required to validate the functionality of all public methods
 * implemented for Caesar, Vigenere, and Playfair ciphers.
 * 
 * @author [Your Name]
 * @version 8/3/2025
 */
public class CryptoManagerTestStudent extends TestCase {

	// Constant Test Data
	private String plainText = "STUDENT TEST 2025!";
	private String caesarKey = "7"; // We will use 7 for Caesar Shift
	private String expectedCaesarEncrypt = "Z[\\KLU['[LZ['979<(";
	private String invalidText = "student test 2025!"; // Contains 's', which is out of bounds

	// New keys for the other ciphers
	private String vigenereKey = "CMSC";
	private String playfairKey = "TEACHER";

	// Setup and Teardown are optional for simple tests but included for structure
	protected void setUp() throws Exception {
		super.setUp();
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testIsStringInBounds_Valid() {
		// All characters are between ' ' (ASCII 32) and '_' (ASCII 95)
		assertTrue("Valid text should return true", CryptoManager.isStringInBounds(plainText));
		assertTrue("Valid symbols should return true", CryptoManager.isStringInBounds("!\"#$%&'()*+,-./"));
	}

	public void testIsStringInBounds_Invalid() {
		// 's' and other lowercase letters are outside the allowed range
		assertFalse("Lowercase letters should fail", CryptoManager.isStringInBounds(invalidText));
		assertFalse("Curly braces should fail", CryptoManager.isStringInBounds("{JAVA"));
		assertFalse("Tilde should fail", CryptoManager.isStringInBounds("TEST~"));
	}

	// CAESAR TESTS

	/**
	 * Test case for Caesar Encryption with a standard key (Key=7).
	 */
	public void testCaesarEncryption_Standard() {
		String encrypted = CryptoManager.caesarEncryption(plainText, Integer.parseInt(caesarKey));
		// Check against the hardcoded expected value
		assertEquals("Encryption with key " + caesarKey + " failed", expectedCaesarEncrypt, encrypted);
	}

	/**
	 * Test case for Caesar Encryption with a boundary check.
	 */
	public void testCaesarEncryption_Bounds() {
		// Test that an invalid string returns the error message
		String result = CryptoManager.caesarEncryption(invalidText, 5);
		assertEquals("The selected string is not in bounds, Try again.", result);
	}

	/**
	 * Test case for Caesar Decryption to confirm the decryption reverses the
	 * encryption.
	 */
	public void testCaesarDecryption_Standard() {
		String decrypted = CryptoManager.caesarDecryption(expectedCaesarEncrypt, Integer.parseInt(caesarKey));
		assertEquals("Decryption with key " + caesarKey + " failed", plainText, decrypted);
	}

	// VIGENERE TESTS

	public void testVigenereEncryption() {
		// 1. Check if encryption returns a valid string in bounds
		String encrypted = CryptoManager.vigenereEncryption(plainText, vigenereKey);
//		System.out.println(encrypted);
		assertTrue("Vigenere encrypted text should be in bounds", CryptoManager.isStringInBounds(encrypted));

		// 2. Check that invalid input returns the error message
		String invalidResult = CryptoManager.vigenereEncryption(invalidText, vigenereKey);
		assertEquals("The selected string is not in bounds, Try again.", invalidResult);
	}

	public void testVigenereDecryption() {
		// Round Trip Test: Encrypt -> Decrypt -> Compare to Original
		String encrypted = CryptoManager.vigenereEncryption(plainText, vigenereKey);
		String decrypted = CryptoManager.vigenereDecryption(encrypted, vigenereKey);

		assertEquals("Vigenere decryption should return the original text", plainText, decrypted);
	}

	// PLAYFAIR TESTS

	public void testPlayfairEncryption() {
		// 1. Check if encryption returns a valid string in bounds
		String encrypted = CryptoManager.playfairEncryption(plainText, playfairKey);
		assertTrue("Playfair encrypted text should be in bounds", CryptoManager.isStringInBounds(encrypted));
//		System.out.println(encrypted);

		// 2. Check that invalid input returns the error message
		String invalidResult = CryptoManager.playfairEncryption(invalidText, playfairKey);
		assertEquals("The selected string is not in bounds, Try again.", invalidResult);
	}

	public void testPlayfairDecryption() {
		// Round Trip Test: Encrypt -> Decrypt -> Compare to Original
		String encrypted = CryptoManager.playfairEncryption(plainText, playfairKey);
		String decrypted = CryptoManager.playfairDecryption(encrypted, playfairKey);

		assertEquals("Playfair decryption should return the original text", plainText, decrypted);
	}
}