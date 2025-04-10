package ecc;

import java.math.BigInteger;

import common.IPrimeConstant;

/**
 * Ep(a,b)为有限域上的椭圆曲线,形如 y^2 ≡ x^3 + ax + b (mod p)且满足 (4a^3 + 27b^2) mod p ≠ 0
 */
public class EccOnGF implements IConstantECC, IPrimeConstant {

	// ----------------field--------------------------
	public static final PointOnGFECC infinitePoint = new PointOnGFECC();// 无限点

	protected final BigInteger p;// 参数p
	protected final BigInteger a;// 参数p
	protected final BigInteger b;// 参数p

	// -------------------constructor--------------------------
	public EccOnGF(BigInteger a, BigInteger b, BigInteger p) {
		// 首先计算a b是否满足方程的唯一因子要求
		if (isIllegal(a, b, p)) {
			throw new IllegalArgumentException("(4a^3 + 27b^2) mod p = 0");
		}
		this.a = a;
		this.b = b;
		this.p = p;
	}

	public EccOnGF(int a, int b, int p) {
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)));
	}

	public EccOnGF(EccOnGF curve) {
		this(curve.a, curve.b, curve.p);
	}

	public EccOnGF() {
		// TODO Auto-generated constructor stub
		this(SPEC256K1_a, SPEC256K1_b, SPEC256K1_p);
	}

	// --------------------------override------------------------------------------

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new EccOnGF(this);
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		EccOnGF ecc2 = (EccOnGF) obj;
		return p == ecc2.p && a == ecc2.a && b == ecc2.b;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "E" + p + "(" + a + "," + b + ")";
	}

	// --------------------------get set -----------------------------------------
	public BigInteger getP() {
		return p;
	}

	public BigInteger getA() {
		return a;
	}

	public BigInteger getB() {
		return b;
	}

	// -----------------------------other method---------------------------------

	public boolean isOnThisCurve(PointOnGFECC point) {
		return point.isInfinite() ? true : equals(point.getCurve());
	}

	public static boolean isIllegal(BigInteger a, BigInteger b, BigInteger p) {
		return a.pow(3).multiply(new BigInteger("4")).add(b.pow(2).multiply(new BigInteger("27"))).mod(p)
				.equals(BigInteger.ZERO);
	}

	// ------------------------------add mult-------------------------------------

	public static PointOnGFECC pointAdd(PointOnGFECC P, PointOnGFECC Q) {
		if (!P.isOnTheSameCurve(Q)) {
			throw new IllegalArgumentException();
		}

		if (P.isInfinite())
			return Q;
		if (Q.isInfinite())
			return P;
		if (P.isInverse(Q))
			return infinitePoint;

		BigInteger Xp = P.getX();
		BigInteger Xq = Q.getX();

		BigInteger Yp = P.getY();
		BigInteger Yq = Q.getY();

		EccOnGF curve = P.getCurve();
		BigInteger p = curve.getP();

		BigInteger m = P.equals(Q)
				? Xp.pow(2).multiply(new BigInteger("3")).add(curve.a)
						.multiply(Yp.multiply(new BigInteger("2")).modInverse(p)).mod(p)
				: Yp.subtract(Yq).multiply(Xp.subtract(Xq).modInverse(p)).mod(p);
		BigInteger Xr = m.pow(2).subtract(Xp).subtract(Xq).mod(p);
		// BigInteger Yr = Yp.add(m.multiply(Xr.subtract(Xp))).mod(p);//
		// 简书文章的错误实现，算出来的是-Yr mod p,也就是-R点
		BigInteger Yr = m.multiply(Xp.subtract(Xr)).subtract(Yp).mod(p);
		return new PointOnGFECC(curve, Xr, Yr);
	}

	public static PointOnGFECC pointMult(BigInteger n, PointOnGFECC P) {
		PointOnGFECC result = infinitePoint, append = P;

		String sequence = n.toString(2);
		// 因为右端最小，从右向左遍历
		for (int i = sequence.length() - 1; i >= 0; i--) {
			if (sequence.charAt(i) == '1') {
				result = pointAdd(result, append);
			}
			append = pointAdd(append, append);
		}
		return result;
	}

	public static PointOnGFECC pointMult(int n, PointOnGFECC P) {
		return pointMult(new BigInteger(String.valueOf(n)), P);
	}

	// --------------------------------------------------------------------------

	public static void main(String[] args) {
		EccOnGF ecc = new EccOnGF(1, 6, 11);
		PointOnGFECC p1 = new PointOnGFECC(ecc, 2, 7);
		PointOnGFECC q = new PointOnGFECC(ecc, 8, 8);
		
		PointOnGFECC mult1 = EccOnGF.pointMult(4, p1);
		PointOnGFECC mult2 = EccOnGF.pointMult(3, mult1);
		
		System.out.println(mult2);
		
		PointOnGFECC mult = EccOnGF.pointMult(12, p1);
		System.out.println(mult);
		
		
	}

}
