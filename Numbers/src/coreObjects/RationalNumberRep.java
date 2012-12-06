package coreObjects;

import java.math.BigInteger;
import java.util.ArrayList;

public class RationalNumberRep {

	protected ArrayList<BigInteger> IntegerPartDigits;
	protected BigInteger theBase;
	public RationalNumberRep(ArrayList<BigInteger> integerDigits, BigInteger theBase) {
		super();
		IntegerPartDigits = integerDigits;
		this.theBase = theBase;
	}
	public RationalNumberRep(BigInteger theBase) {
		super();
		this.theBase = theBase;
		IntegerPartDigits.add(BigInteger.ZERO);
	}
	public void setIntegerPartDigits(ArrayList<BigInteger> integerPartDigits) {
		IntegerPartDigits = integerPartDigits;
	}
	public ArrayList<BigInteger> getIntegerPartDigits() {
		return IntegerPartDigits;
	}
	public BigInteger getTheBase() {
		return theBase;
	}
	
	
	
}
