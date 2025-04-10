package ecc;

import java.math.BigInteger;
import java.util.Random;

public class MenezesVanstone extends CryptoEccOnGF {

	// ---------------------field------------------------------
	private int certainty = DEFAULT_CERTAINTY;
	// k为每次加密时不同的参数，不应在类中设置一个固定的域，应该为每次加密选取不同的k
	private PointOnGFECC e;// 公钥
	private BigInteger d;// 私钥

	// ------------------constructor------------------------
	/**
	 * 根据给定的d生成公钥e
	 * 
	 * @param a
	 * @param b
	 * @param p
	 *            p应为大于3的素数
	 * @param Gx
	 * @param Gy
	 * @param Gn
	 * @param ex
	 * @param ey
	 * @param secretKey
	 *            d与生成元做点倍生成公钥点e，只有当d为0或生成元的阶时才会生成无穷远点O
	 */
	public MenezesVanstone(BigInteger a, BigInteger b, BigInteger p, BigInteger Gx, BigInteger Gy, BigInteger order,
			BigInteger secretKey) {
		super(a, b, p, Gx, Gy, order);
		if (p.compareTo(new BigInteger("3")) <= 0 || !p.isProbablePrime(certainty)) {
			throw new IllegalArgumentException("P:" + p + " should be a prime number greater than 3");
		}
		if (secretKey.compareTo(BigInteger.ONE) <= 0 || secretKey.compareTo(order) >= 0) {
			// 若d小于0或d大于等于order
			throw new IllegalArgumentException("The private key:" + secretKey
					+ " is smaller than 2,or greater than or equal to the order:" + order);
		}
		this.d = secretKey;
		this.e = EccOnGF.pointMult(secretKey, G);
	}

	public MenezesVanstone(int a, int b, int p, int Gx, int Gy, int order, int secretKey) {
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)),
				new BigInteger(String.valueOf(Gx)), new BigInteger(String.valueOf(Gy)),
				new BigInteger(String.valueOf(order)), new BigInteger(String.valueOf(secretKey)));
	}

	/**
	 * 只能确保e在本条曲线上，但无法确保e是否是一个合法公钥
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param Gx
	 * @param Gy
	 * @param order
	 * @param ex
	 * @param ey
	 */
	public MenezesVanstone(BigInteger a, BigInteger b, BigInteger p, BigInteger Gx, BigInteger Gy, BigInteger order,
			BigInteger ex, BigInteger ey) {
		super(a, b, p, Gx, Gy, order);
		if (p.compareTo(new BigInteger("3")) <= 0 || !p.isProbablePrime(certainty)) {
			throw new IllegalArgumentException("P:" + p + " should be a prime number greater than 3");
		}
		this.e = new PointOnGFECC(this, ex, ey);
	}

	public MenezesVanstone(int a, int b, int p, int Gx, int Gy, int order, int ex, int ey) {
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)),
				new BigInteger(String.valueOf(Gx)), new BigInteger(String.valueOf(Gy)),
				new BigInteger(String.valueOf(order)), new BigInteger(String.valueOf(ex)),
				new BigInteger(String.valueOf(ey)));
	}

	/**
	 * 只能确保e在本条曲线上，但无法确保e是否是一个合法公钥
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param Gx
	 * @param Gy
	 * @param order
	 * @param publicKey
	 */
	public MenezesVanstone(BigInteger a, BigInteger b, BigInteger p, BigInteger Gx, BigInteger Gy, BigInteger order,
			PointOnGFECC publicKey) {
		super(a, b, p, Gx, Gy, order);
		if (p.compareTo(new BigInteger("3")) <= 0 || !p.isProbablePrime(certainty)) {
			throw new IllegalArgumentException("P:" + p + " should be a prime number greater than 3");
		}
		if (publicKey.isInfinite()) {
			throw new IllegalArgumentException("The public key can't be the infinite point");
		}
		if (!isOnThisCurve(publicKey)) {
			throw new IllegalArgumentException("The public key:" + publicKey + " is not on this curve:" + this);
		}
		this.e = publicKey;
	}

	public MenezesVanstone(int a, int b, int p, int Gx, int Gy, int order, PointOnGFECC publicKey) {
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)),
				new BigInteger(String.valueOf(Gx)), new BigInteger(String.valueOf(Gy)),
				new BigInteger(String.valueOf(order)), publicKey);
	}

	/**
	 * 私钥d的取值范围为0到生成元G的阶(order-1)
	 * 
	 * @param a
	 * @param b
	 * @param p
	 * @param x
	 * @param y
	 * @param n
	 */
	public MenezesVanstone(BigInteger a, BigInteger b, BigInteger p, BigInteger x, BigInteger y, BigInteger n) {
		super(a, b, p, x, y, n);
		if (p.compareTo(new BigInteger("3")) <= 0 || !p.isProbablePrime(certainty)) {
			throw new IllegalArgumentException("P:" + p + " should be a prime number greater than 3");
		}
		this.d = genRandom(n);
		this.e = EccOnGF.pointMult(d, G);
	}

	public MenezesVanstone(int a, int b, int p, int x, int y, int n) {
		this(new BigInteger(String.valueOf(a)), new BigInteger(String.valueOf(b)), new BigInteger(String.valueOf(p)),
				new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)),
				new BigInteger(String.valueOf(n)));
	}

	/**
	 * 基于给定曲线和给定私钥
	 * 
	 * @param curve
	 * @param secretKey
	 */
	public MenezesVanstone(CryptoEccOnGF curve, BigInteger secretKey) {
		// TODO Auto-generated constructor stub
		super(curve);
		if (secretKey.compareTo(BigInteger.ONE) <= 0 || secretKey.compareTo(n) >= 0) {
			// 若d小于0或d大于等于order
			throw new IllegalArgumentException(
					"The private key:" + secretKey + " is smaller than 2,or greater than or equal to the order:" + n);
		}
		this.d = secretKey;
		this.e = EccOnGF.pointMult(secretKey, G);
	}

	public MenezesVanstone(CryptoEccOnGF curve, int secretKey) {
		this(curve, new BigInteger(String.valueOf(secretKey)));
	}

	/**
	 * 基于给定曲线和公钥
	 * 
	 * @param curve
	 * @param ex
	 * @param ey
	 */
	public MenezesVanstone(CryptoEccOnGF curve, BigInteger ex, BigInteger ey) {
		super(curve);
		this.e = new PointOnGFECC(this, ex, ey);
	}

	public MenezesVanstone(CryptoEccOnGF curve, int ex, int ey) {
		this(curve, new BigInteger(String.valueOf(ex)), new BigInteger(String.valueOf(ey)));
	}

	/**
	 * 基于给定的点和曲线构造
	 * 
	 * @param curve
	 * @param publicKey
	 */
	public MenezesVanstone(CryptoEccOnGF curve, PointOnGFECC publicKey) {
		// TODO Auto-generated constructor stub
		super(curve);
		if (publicKey.isInfinite()) {
			throw new IllegalArgumentException("The public key can't be the infinite point");
		}
		if (!isOnThisCurve(publicKey)) {
			throw new IllegalArgumentException("The public key:" + publicKey + " is not on this curve:" + this);
		}
		this.e = publicKey;
	}

	// ---------------------------static nested class------------------------------
	public static class CiphertextOfMV {
		private final PointOnGFECC y0;
		private final BigInteger y1;
		private final BigInteger y2;

		public CiphertextOfMV(PointOnGFECC y0, BigInteger y1, BigInteger y2) {
			// TODO Auto-generated constructor stub
			this.y0 = y0;
			this.y1 = y1;
			this.y2 = y2;
		}

		public PointOnGFECC getY0() {
			return y0;
		}

		public BigInteger getY1() {
			return y1;
		}

		public BigInteger getY2() {
			return y2;
		}

		@Override
		public String toString() {
			// TODO Auto-generated method stub
			return y0 + " and y1=" + y1 + ",y2=" + y2;
		}

	}

	// ---------------------get set------------------------------
	public PointOnGFECC getE() {
		return e;
	}

	public void setE(PointOnGFECC e) {
		this.e = e;
	}

	public BigInteger getD() {
		return d;
	}

	public void setD(BigInteger d) {
		this.d = d;
	}

	// ---------------------------other method--------------------------------------
	/**
	 * 本义为想生成[0,n)内的任意整数，后发现《现代密码学》对此算法描述错误，d和k均不应该为0和1，将范围调整至[2,n)
	 * d为0会导致公钥为O，为1会导致公钥就是生成元，k为0会导致y0和c1c2为O，导致c1c2的坐标不再有意义，无法参与y1，y2点的生成
	 * k为1会导致y0就是生成元，跟d为1的情况一样，会让攻击者很容易反推出k从而实现攻击.......................
	 * 因为JDK没有提供随机生成均匀分布大整数的API 64位以下，处于long的范围[-2^63,2^63)之内，生成均匀分布随机数
	 * 64位以上，使用随机素数API来代替
	 * 
	 * @param n
	 *            意义为生成元的阶，或其他不可达上限
	 * @return [0,n)范围内的可能素数
	 */
	public static BigInteger genRandom(BigInteger n) {
		if (n.bitLength() <= 64) {
			while (true) {
				long lg = new Random().nextLong();
				if (lg <= 1) {
					continue;
				}
				return new BigInteger(String.valueOf(lg % n.longValue()));
			}
		}

		BigInteger d = null;
		do {
			d = BigInteger.probablePrime(n.bitLength(), new Random());
		} while (d.compareTo(BigInteger.ZERO) >= 0 && d.compareTo(n) < 0);
		return d;
	}

	// -------------------------enc--------------------------------
	/**
	 * 根据本类实例已有参数公钥点e，生成元点G，生成元阶n对 plaintext 加密
	 * 明文空间其实为Zp*,Zp中所有与p互素的点，但因为p为素数，Zp上所有元素都与p互素，所以此时Zp*就是Zp
	 * 若p不为素数，则需要仔细区分Zp*和Zp..........................
	 * 
	 * @param x
	 *            明文的x点，x取值范围为[0,p)任意数，可以不在曲线上
	 * @param y
	 *            明文的y点，y取值范围为[0,p)任意数，可以不在曲线上
	 * @param k
	 *            加密中使用的给定随机数k，取值范围[0,n)
	 * @return
	 */
	public CiphertextOfMV enc(BigInteger x, BigInteger y, BigInteger k) {
		// 检测x，y是否位于[0,p)
		if (x.compareTo(BigInteger.ZERO) < 0 || x.compareTo(p) >= 0) {
			throw new IllegalArgumentException(
					"The plaintext x:" + x + " is smaller than 0,or greater than or equal to the p:" + p);
		}
		if (y.compareTo(BigInteger.ZERO) < 0 || y.compareTo(p) >= 0) {
			throw new IllegalArgumentException(
					"The plaintext x:" + y + " is smaller than 0,or greater than or equal to the p:" + p);
		}
		if (k.compareTo(BigInteger.ONE) <= 0 || k.compareTo(n) >= 0) {
			// 若d小于等于1或d大于等于order
			throw new IllegalArgumentException(
					"K:" + k + " is smaller than 2,or greater than or equal to the order:" + n);
		}

		PointOnGFECC y0 = EccOnGF.pointMult(k, G);// k为0时会导致y0为无穷远点
		PointOnGFECC c1c2 = EccOnGF.pointMult(k, e);// k为0时也会导致c1c2为无穷远点

		BigInteger y1 = c1c2.getX().multiply(x).mod(p);// 空指针异常
		BigInteger y2 = c1c2.getY().multiply(y).mod(p);
		return new CiphertextOfMV(y0, y1, y2);
	}

	public CiphertextOfMV enc(BigInteger x, BigInteger y) {
		return enc(x, y, genRandom(n));
	}

	public CiphertextOfMV enc(int x, int y, int k) {
		return enc(new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)),
				new BigInteger(String.valueOf(k)));
	}

	public CiphertextOfMV enc(int x, int y) {
		return enc(new BigInteger(String.valueOf(x)), new BigInteger(String.valueOf(y)));
	}

	// ---------------------dec---------------------------------------
	/**
	 * 
	 * @param ciphertext
	 * @return 解密结果的一维数组，内应该有两个元素[x,y]
	 */
	public BigInteger[] dec(CiphertextOfMV ciphertext) {
		PointOnGFECC dy0 = pointMult(d, ciphertext.y0);
		return new BigInteger[] { ciphertext.y1.multiply(dy0.getX().modInverse(p)).mod(p),
				ciphertext.y2.multiply(dy0.getY().modInverse(p)).mod(p) };
	}

	// --------------------------main--------------------------
	public static void main(String[] args) {
		MenezesVanstone ecc = new MenezesVanstone(1, 6, 11, 2, 7, 13, 7);
		CiphertextOfMV enc = ecc.enc(4, 9, 3);
		System.out.println(enc);

		BigInteger[] dec = ecc.dec(enc);
		for (int i = 0; i < dec.length; i++) {
			System.out.println(dec[i]);
		}
	}

}
