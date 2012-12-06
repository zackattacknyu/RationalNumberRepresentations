package coreObjects;

import java.math.BigInteger;
import java.util.ArrayList;

public class FractionRep extends RationalNumberRep{
	
	public FractionRep(BigInteger theBase, ArrayList<BigInteger> integerDigits,
			ArrayList<BigInteger> NumeratorDigits,
			ArrayList<BigInteger> DenominatorDigits) {
		super(integerDigits, theBase);
		this.NumeratorDigits = NumeratorDigits;
		this.DenominatorDigits = DenominatorDigits;
	}

	public FractionRep(BigInteger theBase,
			ArrayList<BigInteger> numeratorDigits,
			ArrayList<BigInteger> denominatorDigits) {
		super(theBase);
		NumeratorDigits = numeratorDigits;
		DenominatorDigits = denominatorDigits;
	}

	private ArrayList<BigInteger> NumeratorDigits;
	private ArrayList<BigInteger> DenominatorDigits;
	

	public ArrayList<BigInteger> getNumeratorDigits() {
		return NumeratorDigits;
	}

	public ArrayList<BigInteger> getDenominatorDigits() {
		return DenominatorDigits;
	}

	
}
