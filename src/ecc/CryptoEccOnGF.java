package ecc;

import java.math.BigInteger;

public class CryptoEccOnGF extends EccOnGF {

	// ---------------------field-----------------------------
	/**
	 * 生成元的阶order应该与循环群的阶相同，且nG=0,2G到(n-1)G分别是群中其他元素
	 */
	protected final PointOnGFECC G;

	/**
	 * 生成元G的阶
	 */
	protected final BigInteger n;

	// ----------------constructor-----------------------------
	/**
	 * 原始型
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param Gx
	 *            生成元G的x坐标
	 * @param Gy
	 *            生成元G的y坐标
	 * @param order
	 *            生成元G的阶
	 */
	public CryptoEccOnGF(BigInteger a, BigInteger b, BigInteger p, BigInteger Gx, BigInteger Gy, BigInteger order) {
		// TODO Auto-generated constructor stub
		super(a, b, p);
		this.G = new PointOnGFECC(this, Gx, Gy);
		this.n = order;
	}

	public CryptoEccOnGF(int a, int b, int p, int x, int y, int n) {
		// TODO Auto-generated constructor stub
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)),
				new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)),
				new BigInteger(String.valueOf(n)));
	}

	/**
	 * 基于已有生成元点的构造方法
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param G
	 * @param n
	 */
	public CryptoEccOnGF(BigInteger a, BigInteger b, BigInteger p, PointOnGFECC G, BigInteger n) {
		// TODO Auto-generated constructor stub
		super(a, b, p);
		if (G.isInfinite()) {
			throw new IllegalArgumentException("Generator can't be the infinite point");
		}
		if (!isOnThisCurve(G)) {
			throw new IllegalArgumentException("Generator:" + G + "is not on the " + this);
		}
		this.G = G;
		this.n = n;
	}

	public CryptoEccOnGF(int a, int b, int p, PointOnGFECC G, int n) {
		// TODO Auto-generated constructor stub
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)), G,
				new BigInteger(String.valueOf(n)));
	}

	/**
	 * 基于已有曲线和给定坐标的构造器
	 * 
	 * @param curve
	 * @param a
	 * @param b
	 * @param p
	 * @param n
	 */
	public CryptoEccOnGF(EccOnGF curve, BigInteger x, BigInteger y, BigInteger n) {
		// TODO Auto-generated constructor stub
		super(curve);
		this.G = new PointOnGFECC(this, x, y);
		this.n = n;
	}

	public CryptoEccOnGF(EccOnGF curve, int x, int y, int n) {
		// TODO Auto-generated constructor stub
		this(curve, new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)),
				new BigInteger(String.valueOf(n)));
	}

	/**
	 * 基于已有曲线和已有生成元的构造器
	 * 
	 * @param curve
	 * @param G
	 * @param n
	 */
	public CryptoEccOnGF(EccOnGF curve, PointOnGFECC G, BigInteger n) {
		// TODO Auto-generated constructor stub
		super(curve);
		if (G.isInfinite()) {
			throw new IllegalArgumentException("Generator can not be the infinite point");
		}
		if (!isOnThisCurve(G)) {
			throw new IllegalArgumentException("Generator:" + G + "is not on the " + this);
		}
		this.G = G;
		this.n = n;
	}

	public CryptoEccOnGF(EccOnGF curve, PointOnGFECC G, int n) {
		this(curve, G, new BigInteger(String.valueOf(n)));
	}

	/**
	 * 本体构造方法
	 * 
	 * @param cryptoCurve
	 */
	public CryptoEccOnGF(CryptoEccOnGF cryptoCurve) {
		// TODO Auto-generated constructor stub
		this(cryptoCurve.a, cryptoCurve.b, cryptoCurve.p, cryptoCurve.G, cryptoCurve.n);
	}

	// ------------------------get--------------------------------------
	public PointOnGFECC getG() {
		return G;
	}

	// ------------------------------override-----------------------------------
	@Override
	public String toString() {
		// 因为G中曲线变量也是该变量，调用G.toStr会导致再次调用本曲线toStr，防止无限递归在这里不调用G.toStr
		return super.toString() + " which generator is (" + G.getX() + "," + G.getY() + "),order=" + n;
	}

	// --------------------------------------------------------------------
	public static void main(String[] args) {
		CryptoEccOnGF ecc = new CryptoEccOnGF(1, 6, 11, 2, 7, 13);
		System.out.println(ecc);
	}

}
