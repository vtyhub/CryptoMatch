package caesar;

import common.Arithmetic;

public class Caesar implements Constant {

	public static void checkText(String text) {
		if (!text.matches("^[a-zA-Z]+$")) {
			throw new IllegalArgumentException("text:" + text + " is not made entirely of letters");
		}
	}

	public static String encipher(int k, String plaintext) {
		checkText(plaintext);
		k = k % LETTERCOUNTS;
		StringBuffer sb = new StringBuffer(plaintext.length());
		for (int i = 0; i < plaintext.length(); i++) {
			char c = plaintext.charAt(i);

			if (c >= MINUPPER && c <= MAXUPPER) {
				// c是大写字母
				char cc = (char) (Arithmetic.mod((c - MINUPPER + k), LETTERCOUNTS) + MINUPPER);
				sb.append(cc);
			} else if (c >= MINLOWER && c <= MAXLOWER) {
				// c是小写字母
				char cc = (char) (Arithmetic.mod((c - MINLOWER + k), LETTERCOUNTS) + MINLOWER);
				sb.append(cc);
			}
		}
		return sb.toString();
	}

	public static String decipher(int k, String ciphertext) {
		checkText(ciphertext);
		k = k % LETTERCOUNTS;
		StringBuffer sb = new StringBuffer(ciphertext.length());
		for (int i = 0; i < ciphertext.length(); i++) {
			char c = ciphertext.charAt(i);

			if (c >= MINUPPER && c <= MAXUPPER) {
				// c是大写字母
				char cc = (char) (Arithmetic.mod((c - MINUPPER - k), LETTERCOUNTS) + MINUPPER);
				sb.append(cc);
			} else if (c >= MINLOWER && c <= MAXLOWER) {
				// c是小写字母
				char cc = (char) (Arithmetic.mod((c - MINLOWER - k), LETTERCOUNTS) + MINLOWER);
				sb.append(cc);
			}
		}
		return sb.toString();
	}

	public static void main(String[] args) {

		String plain = "jiaoyukepu";
		String cipher = "mldrbxnhsx";

		System.out.println(encipher(3, plain));
		System.out.println(decipher(3, cipher));

		byte a = 65;
		System.out.println((char) a);
	}
}
