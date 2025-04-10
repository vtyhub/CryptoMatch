package ecc;

import java.math.BigInteger;

public interface IConstantECC {

	BigInteger SPEC256K1_a = BigInteger.ZERO;
	BigInteger SPEC256K1_b = new BigInteger("7");
	BigInteger SPEC256K1_p = new BigInteger(
			"115792089237316195423570985008687907853269984665640564039457584007908834671663");
}
