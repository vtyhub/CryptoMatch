package ecc;

import java.math.BigInteger;

/**
 * 位于一条椭圆曲线上的一个点，具有x，y两个坐标参数
 */
public class PointOnGFECC {

	private final BigInteger x;
	private final BigInteger y;
	private final boolean infinite;
	private final EccOnGF curve;

	public PointOnGFECC() {
		// TODO Auto-generated constructor stub
		this.infinite = true;
		this.x = null;
		this.y = null;
		this.curve = null;
	}

	/**
	 * 提供正常点构造，以及对无穷远点构造的兼容
	 * 
	 * @param subordinate
	 * @param x
	 * @param y
	 */
	public PointOnGFECC(EccOnGF subordinate, BigInteger x, BigInteger y) {
		if (subordinate == null) {
			this.infinite = true;
			this.x = null;
			this.y = null;
		} else if (!isOnTheCurve(subordinate, x, y)) {
			// 检验该点是否在该曲线上，若不是则抛出异常
			throw new IllegalArgumentException("(" + x + "," + y + ") is not on the " + subordinate);
		} else {
			this.infinite = false;
			this.x = x;
			this.y = y;
		}
		this.curve = subordinate;
	}

	public PointOnGFECC(EccOnGF subordinate, int x, int y) {
		// TODO Auto-generated constructor stub
		this(subordinate, new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)));
	}

	/**
	 * 对另一点的拷贝
	 * 
	 * @param point
	 *            若该参数为无穷远点，也可以正常调用
	 */
	public PointOnGFECC(PointOnGFECC point) {
		this(point.curve, point.x, point.y);
	}

	// ----------------------GET--------------------------------
	public BigInteger getX() {
		return x;
	}

	public BigInteger getY() {
		return y;
	}

	public EccOnGF getCurve() {
		return curve;
	}

	public boolean isInfinite() {
		return infinite;
	}

	// ----------------------------Other method---------------------------------
	public static boolean isOnTheCurve(EccOnGF sub, BigInteger x, BigInteger y) {
		BigInteger a = sub.getA();
		BigInteger b = sub.getB();
		BigInteger p = sub.getP();
		return y.pow(2).mod(p).equals(x.pow(3).add(a.multiply(x)).add(b).mod(p));
	}

	public boolean isOnTheSameCurve(PointOnGFECC Q) {
		if (isInfinite() || Q.isInfinite()) {
			return true;
		}
		return getCurve().equals(Q.getCurve());
	}

	/**
	 * @return 该点在其同一曲线下的mod p相反点
	 */
	public PointOnGFECC inverse() {
		return infinite ? this : new PointOnGFECC(curve, x, y.negate().mod(curve.getP()));
	}

	/**
	 * 
	 * @param Q
	 *            检测Q是否是本点在同一曲线下的(x,-y)点
	 * @return
	 */
	public boolean isInverse(PointOnGFECC Q) {
		return equals(Q.inverse());
	}

	// -------------------override-------------------------------
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return infinite ? "O" : "(" + x + "," + y + ") is on the curve: " + curve;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		PointOnGFECC Q = (PointOnGFECC) obj;
		if (infinite)
			return Q.infinite;
		return curve.equals(Q.curve) && x.equals(Q.x) && y.equals(Q.y);
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return new PointOnGFECC(this);
	}

	// -----------------------------------------------------------
	public static void main(String[] args) {
		PointOnGFECC p = new PointOnGFECC(new EccOnGF(1, 6, 11), 2, 7);
		System.out.println(p);
		System.out.println(p.inverse());
		System.out.println(p.isInverse(new PointOnGFECC(new EccOnGF(1, 6, 11), 5, 2)));
	}

}
