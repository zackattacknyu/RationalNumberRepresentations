package coreAccessor;


import java.math.BigInteger;
import java.util.ArrayList;




import coreObjects.DecimalRep;
import coreObjects.Fraction;

public class ConvertToRepImpl {
	
	public static DecimalRepStrings ConvertDecimalToNadicRep(DecimalRep theDecimal, BigInteger base, String baseRep){
		
		/* PUT CODE HERE SOON */
		
		return null;
	}
	public static DecimalRepStrings ConvertDecimalToNadicRep(DecimalRep theDecimal, BigInteger base){
		return ConvertDecimalToNadicRep(theDecimal,base,base.toString());
	}
	
	public static DecimalRepStrings ConvertDecimalToNumberRep(DecimalRep theDecimal, BigInteger base){
		BigInteger intPart = Fraction.ConvertIntegerRepToInteger(theDecimal.getIntegerPartDigits(), base);
		
		String integerPart = ConvertToNumberRep(intPart,base);
		
		String termPart = MakeSetStringWithZeros(theDecimal.getTerminatingPartDigits(),
				theDecimal.getTermPartNumZeros());
		int numDigitsTermPart = theDecimal.getTerminatingNumDigits();
		
		String repeatPart = MakeSetStringWithZeros(theDecimal.getRepeatingPartDigits(),
				theDecimal.getRepeatPartNumZeros());
		int numDigitsRepeatPart = theDecimal.getRepeatingNumDigits();
		
		return new DecimalRepStrings(integerPart,numDigitsTermPart,termPart,numDigitsRepeatPart,repeatPart);
	}
	
	public static FractionStrings ConvertFractionToNadicRep(Fraction theFraction, BigInteger base, String baseRep){
		String integerPart = ConvertToNadicRep(theFraction.getIntegerPart(),base,baseRep);
		
		String numerator = ConvertToNadicRep(theFraction.getNumerator(),base,baseRep);
		String denominator = ConvertToNadicRep(theFraction.getDenominator(),base,baseRep);
		
		return new FractionStrings(integerPart,numerator,denominator);
	}
	public static FractionStrings ConvertFractionToNadicRep(Fraction theFraction, BigInteger base){
		return ConvertFractionToNadicRep(theFraction,base,base.toString());
	}
	public static FractionStrings ConvertFractionToNumberRep(Fraction theFraction, BigInteger base){
		String integerPart = ConvertToNumberRep(theFraction.getIntegerPart(),base);
		
		String numerator = ConvertToNumberRep(theFraction.getNumerator(),base);
		String denominator = ConvertToNumberRep(theFraction.getDenominator(),base);
		
		return new FractionStrings(integerPart,numerator,denominator);
	}
	
	/*
	 * The following few methods take an integer and convert to its base representation in various forms
	 * For the explanation of the methods, the Integer is X, the base is N, the number of digits is D, and
	 * 		the digits are a[d],...,a[0] where a[i] is the factor with N^i
	 * 
	 *  1. ConvertToNadicRep: X = a[d]*n^d + a[d-1]*n^(d-1) + ... + a[1]*n + a[0]
	 *  2. ConvertToNumberRep: X = {a[d]}{a[d-1]}...{a[1]}{a[0]}
	 *  3. ConvertToDigitRep: X = a[d]...a[1]a[0] where a[i] are digits 0-1, or letters or symbols
	 *  
	 *  Methods 1 and 2 can be used for any integer base
	 *  where Method 3 can only be used for integer bases between 2 and 36
	 *  
	 *  The validators to make sure the methods can be used are below the methods
	 */
	public static String ConvertToNadicRep(BigInteger theNumber, BigInteger base, String baseRep){
		
		//this is a safeguard to make sure the rep can be found
		if(!canFindRep(theNumber,base)){
			return null;
		}
		
		//returns 0 if the number is zero
		if(theNumber.compareTo(BigInteger.ZERO) == 0){
			return "0";
		}
		
		StringBuffer digitString = new StringBuffer();
		ArrayList<BigInteger> BaseDigits = Fraction.ConvertIntegerToIntegerRep(theNumber,base);
		
		/*
		 * The digits are displayed in reverse order from how they were calculated
		 * This makes the string with the digits in reverse order
		 */
		for(int index = BaseDigits.size() - 1; index >= 0 ; index--){
			digitString.append(BaseDigits.get(index));
			if(index > 1){
				digitString.append("*"+ baseRep + "^" + index + " + ");
			}
			else if(index == 1){
				digitString.append("*"+ baseRep +  " + ");
			}
		}
		
		return digitString.toString();
	}
	public static String ConvertToNadicRep(BigInteger theNumber, BigInteger base){
		
		return ConvertToNadicRep(theNumber,base,base.toString());
	}
	public static String ConvertToNumberRep(BigInteger theNumber,BigInteger base){
		
		//this is a safeguard to make sure the rep can be found
		if(!canFindRep(theNumber,base)){
			return null;
		}
		
		//returns 0 if the number is zero
		if(theNumber.compareTo(BigInteger.ZERO) == 0){
			return "0";
		}
		
		ArrayList<BigInteger> BaseDigits = Fraction.ConvertIntegerToIntegerRep(theNumber,base);
		
		return MakeSetString(BaseDigits);

		
	}
	public static String MakeSetStringWithZeros(ArrayList<BigInteger> BaseDigits, int numZeros){
		
		ArrayList<BigInteger> DigitArray = new ArrayList<BigInteger>(numZeros + BaseDigits.size());
						
		//adds in the base digits
		for(int index = 0; index < BaseDigits.size(); index++){
			DigitArray.add(BaseDigits.get(index));
		}
		
		/*
		 * This puts the leading zeros into the array
		 * Since the following method will reverse the order, this is called second
		 */
		for(int index = 0; index < numZeros; index++){
			DigitArray.add(BigInteger.ZERO);
		}
		
		if(DigitArray.size() > 0){
			return MakeSetString(DigitArray);
		}
		else{
			return "";
		}
		
		
	}
	
	public static String MakeSetString(ArrayList<BigInteger> BaseDigits){
		StringBuffer digitString = new StringBuffer();
		
		/*
		 * The digits are displayed in reverse order from how they were calculated
		 * This makes the string with the digits in reverse order
		 */
		digitString.append("{");
		for(int index = BaseDigits.size() - 1; index > 0 ; index--){
			digitString.append(BaseDigits.get(index));
			digitString.append(",");
		}
		digitString.append(BaseDigits.get(0));
		digitString.append("}");
		
		return digitString.toString();
	}
	
	
	/*
	 * This is the validator that will be called before ConvertToNadicRep and ConvertToNumberRep are called
	 */
	public static boolean canFindRep(BigInteger theNumber, BigInteger theBase){
		
		/*
		 * theNumber must be greater than or equal to zero. negative numbers should be handled in the user interface
		 */
		if(theNumber.compareTo(BigInteger.ZERO) < 0){
			return false;
		}
		
		/*
		 * theBase needs to be greater than 1
		 */
		if(theBase.compareTo(BigInteger.valueOf(1)) <= 0){
			return false;
		}
		
		return true;
	}
	
	
	
}
