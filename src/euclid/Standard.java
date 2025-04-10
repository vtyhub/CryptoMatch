package euclid;

public class Standard {

	/**
	 * a应该比b大，减少一次递归
	 * 
	 * @param a
	 * @param b
	 * @return gcd(a,b)
	 */
	public static int gcd(int a, int b) {
		return b == 0 ? a : gcd(b, a % b);
	}
	
	public static void main(String[] args) {
//		System.out.println(gcd(33,));
	}
}
