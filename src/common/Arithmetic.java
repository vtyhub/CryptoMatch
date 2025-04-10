package common;

public class Arithmetic {

	/**
	 * a mod abs(m) 效率太低，无法适用于特别小的long整数
	 * 
	 * @param a
	 * @param m
	 * @return
	 */
	public static long mod(long a, long m) {
		while (a < 0)
			a += m;
		return a % Math.abs(m);
	}

	public static void main(String[] args) {
		System.out.println(mod(-5, 26));
	}

}
