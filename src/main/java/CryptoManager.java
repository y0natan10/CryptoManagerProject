/**
 * This is a utility class that encrypts and decrypts a phrase using three
 * different approaches.
 * 
 * The first approach is called the Vigenere Cipher.Vigenere encryption is a
 * method of encrypting alphabetic text based on the letters of a keyword.
 * 
 * The second approach is Playfair Cipher. It encrypts two letters (a digraph)
 * at a time instead of just one.
 * 
 * The third approach is Caesar Cipher. It is a simple replacement cypher.
 * 
 * @author Huseyin Aygun
 * @version 8/3/2025
 */

public class CryptoManager {

	private static final char LOWER_RANGE = ' ';
	private static final char UPPER_RANGE = '_';
	private static final int RANGE = UPPER_RANGE - LOWER_RANGE + 1;
	// Use 64-character matrix (8X8) for Playfair cipher
	private static final String ALPHABET64 = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789 !\"#$%&'()*+,-./:;<=>?@[\\]^_\n";

	/**
	 * Checks if the string is within the allowable bounds of the cipher system.
	 * 
	 * @param plainText The string to check.
	 * 
	 * @return true if all characters are within the bounds, false otherwise.
	 */
	public static boolean isStringInBounds(String plainText) {
		for (int i = 0; i < plainText.length(); i++) {
			if (!(plainText.charAt(i) >= LOWER_RANGE && plainText.charAt(i) <= UPPER_RANGE)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Encrypts a string using the Caesar cipher.
	 * 
	 * @param plainText The string to be encrypted.
	 * @param key       The integer shift value.
	 * 
	 * @return The encrypted string, or an error message if the string is out of
	 *         bounds.
	 */
	public static String caesarEncryption(String plainText, int key) {
		if (!isStringInBounds(plainText)) {
			return "The selected string is not in bounds, Try again.";
		}

		StringBuilder encryptedText = new StringBuilder();

		// Ensure the key is positive and within RANGE
		int normalizedKey = key % RANGE;

		for (int i = 0; i < plainText.length(); i++) {
			char plainChar = plainText.charAt(i);

			// Use the helper method for encryption (positive shift)
			char encryptedChar = shiftCharacter(plainChar, normalizedKey);

			encryptedText.append(encryptedChar);
		}

		return encryptedText.toString();
	}

	/**
	 * Decrypts a string using the Caesar cipher.
	 * 
	 * @param encryptedText The string to be decrypted.
	 * @param key           The integer shift value.
	 * 
	 * @return The decrypted string, or an error message if the string is out of
	 *         bounds.
	 */
	public static String caesarDecryption(String encryptedText, int key) {
		if (!isStringInBounds(encryptedText)) {
			return "The selected string is not in bounds, Try again.";
		}

		StringBuilder decryptedText = new StringBuilder();

		// Ensure the key is positive and within RANGE
		int normalizedKey = key % RANGE;

		for (int i = 0; i < encryptedText.length(); i++) {
			char encryptedChar = encryptedText.charAt(i);

			// Use the helper method for decryption (negative shift)
			char decryptedChar = shiftCharacter(encryptedChar, -normalizedKey);

			decryptedText.append(decryptedChar);
		}

		return decryptedText.toString();
	}

	/**
	 * Vigenere Encryption
	 * 
	 * @param plainText The string to be encrypted.
	 * @param key       The keyword used for the cipher.
	 * 
	 * @return The encrypted string, or an error message if the string is out of
	 *         bounds.
	 */
	public static String vigenereEncryption(String plainText, String key) {
		if (!isStringInBounds(plainText) || !isStringInBounds(key) || key.length() == 0) {
			return "The selected string is not in bounds, Try again.";
		}

		StringBuilder encryptedText = new StringBuilder();
		int keyLength = key.length();

		for (int i = 0; i < plainText.length(); i++) {
			char plainChar = plainText.charAt(i);
			char keyChar = key.charAt(i % keyLength);

			// Calculate the shift value from the key character (0-based)
			int keyShift = keyChar - LOWER_RANGE;

			// Encrypt using the shift character helper
			char encryptedChar = shiftCharacter(plainChar, keyShift);

			encryptedText.append(encryptedChar);
		}

		return encryptedText.toString();
	}

	/**
	 * Vigenere Decryption
	 * 
	 * @param encryptedText The string to be decrypted.
	 * @param key           The keyword used for the cipher.
	 * 
	 * @return The decrypted string, or an error message if the string is out of
	 *         bounds.
	 */
	public static String vigenereDecryption(String encryptedText, String key) {
		if (!isStringInBounds(encryptedText) || !isStringInBounds(key) || key.length() == 0) {
			return "The selected string is not in bounds, Try again.";
		}

		StringBuilder decryptedText = new StringBuilder();
		int keyLength = key.length();

		for (int i = 0; i < encryptedText.length(); i++) {
			char encryptedChar = encryptedText.charAt(i);
			char keyChar = key.charAt(i % keyLength);

			// Calculate the shift value from the key character (0-based)
			int keyShift = keyChar - LOWER_RANGE;

			// Decrypt using the shift character helper (negative shift)
			char decryptedChar = shiftCharacter(encryptedChar, -keyShift);

			decryptedText.append(decryptedChar);
		}

		return decryptedText.toString();
	}

	/**
	 * Private helper method to handle the modular arithmetic for character shifting
	 * for both encryption (positive shift) and decryption (negative shift).
	 * 
	 * @param charToShift The character to be shifted.
	 * @param shiftAmount The amount to shift (positive for encrypt, negative for
	 *                    decrypt).
	 * @return The shifted character.
	 */
	private static char shiftCharacter(char charToShift, int shiftAmount) {
		// 1. Convert char to its 0-based index (0 to RANGE - 1)
		int charIndex = charToShift - LOWER_RANGE;

		// 2. Apply the shift. We add RANGE to ensure the result is positive before the
		// final modulo.
		// Example: (10 - 7 + 95) % 95 == 3
		int shiftedIndex = (charIndex + shiftAmount + RANGE) % RANGE;

		// 3. Convert the 0-based index back to a character
		return (char) (shiftedIndex + LOWER_RANGE);
	}

	/**
	 * Playfair Encryption
	 *
	 * Encrypts the text using an 8x8 Playfair cipher matrix.
	 * 
	 * @param plainText The string to be encrypted.
	 * @param key       The keyword used to construct the Playfair matrix.
	 * 
	 * @return The encrypted string, or an error message if the plainText contains
	 *         out-of-bounds characters.
	 */
	public static String playfairEncryption(String plainText, String key) {
		if (!isStringInBounds(plainText)) {
			return "The selected string is not in bounds, Try again.";
		}

		// Build the 8x8 Playfair matrix using the key + ALPHABET64
		char[][] matrix = buildPlayfairMatrix(key);

		// Ensure text length is even (pad with a space if needed)
		String text = preparePlayfairText(plainText);

		StringBuilder result = new StringBuilder();

		// Process the text two characters at a time
		for (int i = 0; i < text.length(); i += 2) {
			char a = text.charAt(i);
			char b = text.charAt(i + 1);
			result.append(encryptPair(a, b, matrix));
		}

		return result.toString();
	}

	/**
	 * Playfair Decryption
	 *
	 * Reverses the Playfair encryption rules using the same 8x8 matrix.
	 *
	 * @param encryptedText The encrypted string.
	 * @param key           The keyword used to construct the Playfair matrix.
	 *
	 * @return The decrypted (original) plaintext string.
	 */
	public static String playfairDecryption(String encryptedText, String key) {
		if (!isStringInBounds(encryptedText)) {
			return "The selected string is not in bounds, Try again.";
		}

		// Build matrix again using the same key
		char[][] matrix = buildPlayfairMatrix(key);

		StringBuilder result = new StringBuilder();

		// Process encrypted text in pairs
		for (int i = 0; i < encryptedText.length(); i += 2) {
			char a = encryptedText.charAt(i);
			char b = encryptedText.charAt(i + 1);
			result.append(decryptPair(a, b, matrix));
		}

		return result.toString();
	}

	// PLAYFAIR HELPERS

	/**
	 * Builds an 8x8 Playfair matrix based on the given key.
	 *
	 * 1. Inserts unique characters of the key in order
	 * 
	 * 2. Fills remaining spaces with characters from ALPHABET64.
	 * 
	 * @param key The key used to populate the beginning of the matrix.
	 * 
	 * @return An 8x8 Playfair cipher matrix.
	 */
	private static char[][] buildPlayfairMatrix(String key) {
		boolean[] used = new boolean[128]; // Tracks if a character has been added
		char[][] matrix = new char[8][8];
		int r = 0, c = 0;

		// Step 1: Add unique characters from the key
		for (int i = 0; i < key.length(); i++) {
			char ch = key.charAt(i);
			if (!used[ch]) {
				used[ch] = true;
				matrix[r][c] = ch;
				c++;
				if (c == 8) {
					c = 0;
					r++;
				}
			}
		}

		// Step 2: Fill remaining matrix using ALPHABET64
		for (int i = 0; i < ALPHABET64.length(); i++) {
			char ch = ALPHABET64.charAt(i);
			if (!used[ch]) {
				used[ch] = true;
				matrix[r][c] = ch;
				c++;
				if (c == 8) {
					c = 0;
					r++;
				}
			}
			if (r == 8)
				break; // Matrix filled
		}

		return matrix;
	}

	/**
	 * Finds the row and column of a character inside the 8x8 Playfair matrix.
	 *
	 * @param matrix The 8x8 Playfair matrix.
	 * @param ch     The character to locate.
	 * 
	 * @return A 2-element array: [row, column].
	 */
	private static int[] findPosition(char[][] matrix, char ch) {
		int[] pos = new int[2];
		for (int r = 0; r < 8; r++) {
			for (int c = 0; c < 8; c++) {
				if (matrix[r][c] == ch) {
					pos[0] = r;
					pos[1] = c;
					return pos;
				}
			}
		}
		return null; // Should never happen because ALPHABET64 contains all allowed characters
	}

	/**
	 * Prepares text for Playfair by ensuring it has an even number of characters.
	 * 
	 * We do NOT change the contents — we only pad with a space if needed.
	 *
	 * @param text The original plaintext.
	 * 
	 * @return The text padded to even length.
	 */
	private static String preparePlayfairText(String text) {
		if (text.length() % 2 != 0) {
			text = text + " "; // Padding with a space (safe, reversible)
		}
		return text;
	}

	/**
	 * Encrypts a pair of characters using Playfair rules: - Same row: shift right -
	 * Same column: shift down - Rectangle: swap columns
	 *
	 * @param a      First character of the pair.
	 * @param b      Second character of the pair.
	 * @param matrix The Playfair matrix.
	 * 
	 * @return The encrypted 2-character string.
	 */
	private static String encryptPair(char a, char b, char[][] matrix) {
		int[] p1 = findPosition(matrix, a);
		int[] p2 = findPosition(matrix, b);

		int r1 = p1[0], c1 = p1[1];
		int r2 = p2[0], c2 = p2[1];

		// Same row --> shift right
		if (r1 == r2) {
			c1 = (c1 + 1) % 8;
			c2 = (c2 + 1) % 8;
		}
		// Same column --> shift down
		else if (c1 == c2) {
			r1 = (r1 + 1) % 8;
			r2 = (r2 + 1) % 8;
		}
		// Rectangle rule --> swap columns
		else {
			int temp = c1;
			c1 = c2;
			c2 = temp;
		}

		return "" + matrix[r1][c1] + matrix[r2][c2];
	}

	/**
	 * Decrypts a pair of characters using Playfair rules (reverse of encryption): -
	 * Same row: shift left - Same column: shift up - Rectangle: swap columns
	 *
	 * @param a      First encrypted character.
	 * @param b      Second encrypted character.
	 * @param matrix The Playfair matrix.
	 * 
	 * @return The decrypted 2-character string.
	 */
	private static String decryptPair(char a, char b, char[][] matrix) {
		int[] p1 = findPosition(matrix, a);
		int[] p2 = findPosition(matrix, b);

		int r1 = p1[0], c1 = p1[1];
		int r2 = p2[0], c2 = p2[1];

		// Same row --> shift left
		if (r1 == r2) {
			c1 = (c1 + 7) % 8;
			c2 = (c2 + 7) % 8;
		}
		// Same column --> shift up
		else if (c1 == c2) {
			r1 = (r1 + 7) % 8;
			r2 = (r2 + 7) % 8;
		}
		// Rectangle --> swap columns
		else {
			int temp = c1;
			c1 = c2;
			c2 = temp;
		}

		return "" + matrix[r1][c1] + matrix[r2][c2];
	}
}