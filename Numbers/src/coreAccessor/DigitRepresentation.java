package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;



import coreObjects.DecimalRep;
import coreObjects.Fraction;

public class DigitRepresentation {

	private int MaxBase;
	private int MaxNumCharacters;
	
	/*
	 * This array stores the character representative for a number
	 * 		so NumberToChar[11] is the character representative for 11 if
	 * 		we are using a base higher than 11. 
	 */
	private char[] NumberToChar; 
	
	/*
	 * This is the opposite of the above with the key being the character
	 *      and the value being the integer it represents, so a key of 'A' 
	 *      corresponds to the value 10. 
	 */
	private HashMap<Character,Integer> CharToNumber;
	
	public DigitRepresentation(){
		MaxBase = 36;
		MaxNumCharacters = 256;
		
		NumberToChar = new char[MaxBase];
		CharToNumber = new HashMap<Character,Integer>(MaxNumCharacters);
		
		for(int index = 0; index < 10; index++){
			NumberToChar[index] = (char)(index + '0');
			CharToNumber.put((char)(index + '0'), index);
		}
		
		for(int index = 10; index < 36; index++){
			NumberToChar[index] = (char)(index - 10 + 'A');
			CharToNumber.put((char)(index - 10 + 'A'), index);
			CharToNumber.put((char)(index - 10 + 'a'), index);
		}
	}
	
	public String ConvertToDigitRep(BigInteger theNumber,int base){
		
		//this is a safeguard to make sure the rep can be found
		if(!canFindRep(theNumber,base)){
			return null;
		}
		
		//return 0 if the number is zero
		if(theNumber.compareTo(BigInteger.ZERO) == 0){
			return "0";
		}
		
		StringBuffer digitString = new StringBuffer();
		ArrayList<BigInteger> BaseDigits = Fraction.ConvertIntegerToIntegerRep(theNumber,BigInteger.valueOf(base));
		
		/*
		 * The digits are displayed in reverse order from how they were calculated
		 * This makes the string with the digits in reverse order
		 */
		for(int index = BaseDigits.size() - 1; index >= 0 ; index--){
			digitString.append(getChar(BaseDigits.get(index).intValue()));
		}
		
		return digitString.toString();
		
	}
	
	public FractionStrings ConvertFractionToDigitRep(Fraction theNum, int base){
		String integerPart = ConvertToDigitRep(theNum.getIntegerPart(),base);
		
		String numerator = ConvertToDigitRep(theNum.getNumerator(),base);
		String denominator = ConvertToDigitRep(theNum.getDenominator(),base);
		
		return new FractionStrings(integerPart,numerator,denominator);
	}
	
	public DecimalRepStrings ConvertDecimalRepToDigitRep(DecimalRep theNumber, int base){
		BigInteger intPart = Fraction.ConvertIntegerRepToInteger(
				theNumber.getIntegerPartDigits(),BigInteger.valueOf(base));
		String integerPart = ConvertToDigitRep(intPart,base);
		
		String termPart = writeZeros(theNumber.getTermPartNumZeros()) + 
				ConvertToDigitRep(theNumber.getTerminatingPartNumber(),base);
		int numDigitsTermPart = theNumber.getTerminatingNumDigits();
		
		String repeatPart = writeZeros(theNumber.getRepeatPartNumZeros()) +  
				ConvertToDigitRep(theNumber.getRepeatingPartNumber(),base);
		int numDigitsRepeatPart = theNumber.getRepeatingNumDigits();
		
		
		return new DecimalRepStrings(integerPart, numDigitsTermPart,termPart, numDigitsRepeatPart, repeatPart);
	}
	
	private static String writeZeros(int numZeros){
		StringBuffer zeroString = new StringBuffer();
		if(numZeros == 0){
			return "";
		}
		else{
			for(int index = 0; index < numZeros; index++){
				zeroString.append("0");
			}
			return zeroString.toString();
		}
	}
	
	/*
	 * This converts from a digit representation and converts it to a number
	 * Note that digits[i] corresponds to a[i] in the n-adic form of the integer
	 */
	public BigInteger ConvertFromDigitRep(String digits, int base){
		StringBuffer theString = new StringBuffer(digits);
		digits = theString.reverse().toString();
		return ConvertFromDigitRep(digits.toCharArray(),base);
	}
	public BigInteger ConvertFromDigitRep(char[] digits, int base){
		
		ArrayList<BigInteger> theDigits = new ArrayList<BigInteger>(digits.length);
		
		if(!isArrayValid(digits,base)){
			return null;
		}
		
		for(int index = 0; index < digits.length; index++){
			theDigits.add(BigInteger.valueOf(CharToNumber.get(digits[index])));
		}
		
		return ConvertFromRepImpl.ConvertToIntegerFromIntegerArray(theDigits, BigInteger.valueOf(base));
		
	}
	
	/*
	 * This is the validator that will be called before ConvertToDigitRep
	 */
	private boolean canFindRep(BigInteger theNumber, int base){
		if(!ConvertToRepImpl.canFindRep(theNumber,BigInteger.valueOf(base))){
			return false;
		}
		
		//this one has the added restriction that it can't be more than MaxBase
		if(base > MaxBase){
			return false;
		}
		
		return true;
	}
	
	/*
	 * This is the validator to be called before ConvertFromDigitRep
	 */
	public boolean isArrayValid(char[] digits, int base){
		
		int currentNumber;
		
		for(int index = 0; index < digits.length; index++){
			
			//makes sure it contains the number
			if(!CharToNumber.containsKey(digits[index])){
				return false;
			}
			
			currentNumber = CharToNumber.get(digits[index]);
			
			if(currentNumber < 0){
				return false;
			}
			if(currentNumber >= base){
				return false;
			}
		}
		
		return true;
	}

	public int getMaxBase() {
		return MaxBase;
	}
	
	public char getChar(int number){
		return NumberToChar[number];
	}
	
	public int getNumber(char digit){
		return CharToNumber.get(digit);
	}
}
