package coreAccessor;


import java.math.BigInteger;
import java.util.ArrayList;



import coreObjects.Fraction;

public class ConvertFromRepImpl {
	
	public static Fraction ConvertToFractionFromIntegers(BigInteger intPart, 
			BigInteger numer, BigInteger denom){
		return new Fraction(numer, denom, intPart);
	}
	
	public static BigInteger ConvertToIntegerFromNumberString(String numbers, String delimiter, BigInteger theBase){
		String[] theDigits = numbers.split(delimiter);
		ArrayList<BigInteger> Digits = new ArrayList<BigInteger>(theDigits.length);
		
		if(!isStringArrayAllInts(theDigits)){
			return null;
		}
		
		for(int index = theDigits.length - 1; index >= 0; index--){
			Digits.add(new BigInteger(theDigits[index]));
		}
		
		return ConvertToIntegerFromIntegerArray(Digits,theBase);
	}
	
	public static BigInteger ConvertToIntegerFromIntegerArray(ArrayList<BigInteger> theDigits, BigInteger theBase){
		if(!isDigitArrayValid(theDigits,theBase)){
			return null;
		}
		
		return Fraction.ConvertIntegerRepToInteger(theDigits, theBase);
	}
	
	public static boolean isStringArrayAllInts(String[] theArray){
		for(int index = 0; index < theArray.length; index++){
			if(!theArray[index].matches("[0-9]+")){
				return false;
			}
		}
		return true;
	}
	
	public static boolean isDigitArrayValid(ArrayList<BigInteger> theDigits, BigInteger theBase){
		for(int index = 0; index < theDigits.size(); index++){
			
			//if any of the digits are not between 0 and theBase-1
			if(theDigits.get(index).compareTo(BigInteger.ZERO) < 0){
				return false;
			}
			if(theDigits.get(index).compareTo(theBase) >= 0){
				return false;
			}
			
		}
		
		return true;
	}
	
}
