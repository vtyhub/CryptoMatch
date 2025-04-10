package rsa;

import java.util.ArrayList;

import euclid.Extended;

public class RSA {

	/**
	 * 分解n的质因数，返回[p，q]
	 * 
	 * @param n
	 * @return
	 */
	public static ArrayList<Integer> getPQ(int n) {
		return getPQ(n, new ArrayList<Integer>());
	}

	public static ArrayList<Integer> getPQ(int n, ArrayList<Integer> list) {
		for (int i = 2; i <= n; i++) {
			if (i == n) {
				// System.out.print(i);
				list.add(i);
				return list;
			}
			if (n > i && (n % i == 0)) {
				// System.out.print(i + "*");
				list.add(i);
				getPQ(n / i, list);
				break;
			}
		}
		return list;
	}

	/**
	 * d * e ≡ 1 (mod (p-1)(q-1)) d e互为乘法逆元，根据公式算出
	 * 
	 * @param p
	 * @param q
	 * @param dOrE
	 * @return
	 */
	public static int computeDE(int p, int q, int dOrE) {
		return Extended.exEuclid(dOrE, (p - 1) * (q - 1));
	}

	public static int computeDE(int n, int dOrE) {
		ArrayList<Integer> pq = getPQ(n);
		if (pq.size() != 2) {
			throw new IllegalArgumentException("n:" + n + " is not a semiprime");
		}
		return Extended.exEuclid(dOrE, (pq.get(0) - 1) * (pq.get(1) - 1));
	}

	public static void main(String[] args) {
		System.out.println(computeDE(3, 7, 5));
	}
}
