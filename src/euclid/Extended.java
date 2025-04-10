package euclid;

import java.math.BigInteger;

public class Extended {

	/**
	 * (a * x) mod m = 1 ,通过a和m求乘法逆元x
	 * 
	 * @param a
	 * @param m
	 * @return
	 */
	public static int exEuclid(int a, int m) {
		int[] mm = { 1, 0, m };
		int[] n = { 0, 1, a };
		int[] temp = new int[3];
		int q = 0; // 初始化
		boolean flag = true;
		while (flag) {
			q = mm[2] / n[2];
			for (int i = 0; i < 3; i++) {
				temp[i] = mm[i] - q * n[i];
				mm[i] = n[i];
				n[i] = temp[i];
			}
			if (n[2] == 1) {
				if (n[1] < 0) {
					n[1] = n[1] + m;
				}
				return n[1];
			}
			if (n[2] == 0) {
				flag = false;
			}
		}
		return 0;
	}

	// public static int[] exEuclid(int a, int b) {
	//
	// }

	public static void main(String[] args) {
		System.out.println(exEuclid(14, 11));
		System.out.println(new BigInteger("14").modInverse(new BigInteger("11")));
	}
}
