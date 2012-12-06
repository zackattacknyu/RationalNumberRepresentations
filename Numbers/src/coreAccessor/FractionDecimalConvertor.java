package coreAccessor;

import java.math.BigInteger;


import coreObjects.DecimalRep;
import coreObjects.Fraction;

public class FractionDecimalConvertor {

	
	public static DecimalRep FracToDecimal(Fraction input, BigInteger theBase){
		return Fraction.ConvertFractionToDecimalRep(input, theBase);
	}
	
	public static Fraction DecimalToFrac(DecimalRep theRep){
		return Fraction.ConvertDecimalRepToFraction(theRep);
	}
	
	public static DecimalRep DecimalToDecimal(DecimalRep input, BigInteger outputBase){
		
		Fraction theInput = Fraction.ConvertDecimalRepToFraction(input);
		return Fraction.ConvertFractionToDecimalRep(theInput, outputBase);
	}
}
