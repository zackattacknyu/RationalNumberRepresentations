package ioMethods;


import java.math.BigInteger;
import java.util.ArrayList;

import core.DecimalRep;
import core.Fraction;
import core.FractionRep;
import core.RationalNumberRep;

public class DigitNumberStringExpression extends NumberStringExpression {

	public DigitNumberStringExpression(String input, BigInteger inputBase) {
		super(input, inputBase);
	}

	public DigitNumberStringExpression(Fraction theFrac) {
		super(theFrac);
	}
	
	private BigInteger CharToNumber(char theDigit){
		int number;
		theDigit = Character.toUpperCase(theDigit);
		if( (theDigit >= '0') && (theDigit <= '9') ){
			number = (int)(theDigit - '0');
		}
		else if( (theDigit >= 'A') && (theDigit <= 'Z') ){
			number = (int)(theDigit - 'A') + 10;
		}
		else{
			return null;
		}
		return BigInteger.valueOf(number);
	}
	
	private char NumberToChar(BigInteger number){
		int theNumber = number.intValue();
		
		if( (theNumber >= 0) && (theNumber <= 9) ){
			return (char)('0' + theNumber);
		}
		else if( (theNumber >= 10) && (theNumber <= 35) ){
			return (char)('A' + (theNumber - 10) );
		}
		else{
			return '#';
		}
	}
	
	@Override
	protected boolean splitNumberStringAndPreValidate() {
		
		String[] numberParts;
		
		//if decimal or fraction
		if(masterInput.matches(Constants.DECIMAL_EXPRESSION_PATTERN)){
			
			WhatNumberStringRepresents = 'D';
			
			//if decimal, split by decimal point and underscore
			numberParts = StringHelper.takeOutCharsAndSplitString(
					masterInput, Constants.DECIMAL_SEPARATOR_CLASS_REGEX, 
					Constants.COMMA_IN_REGEX);
			
			//initial values for the different parts
			integerPartDigitString = "0";
			initialPartString = "";
			repeatingPartString = "";
			
			//gets the integer part
			if(numberParts.length > 0){
				integerPartDigitString = numberParts[0];
			}
			
			//gets the inital part of the decimal if it exists
			if(numberParts.length > 1){
				initialPartString = numberParts[1];
			}
			
			//gets the repeating part of the decimal if specified
			if(numberParts.length > 2){
				repeatingPartString = numberParts[2];
			}
			
			
			
		}
		else if(masterInput.matches(Constants.FRACTION_EXPRESSION_PATTERN)){
			
			numberParts = StringHelper.takeOutCharsAndSplitString(
					masterInput, Constants.FRACTION_SEPARATOR_CLASS_REGEX, 
					Constants.COMMA_IN_REGEX);
			
			//it is a fraction unless shown to be an integer below
			WhatNumberStringRepresents = 'F';
			
			//initial values for the different parts
			integerPartDigitString = "0";
			numeratorString = "0";
			denominatorString = "1";
			
			//if not in this format, return false
			if( (numberParts.length < 1)  || (numberParts.length > 3) ){
				return false;
			}
			
			//if only integer or mixed number
			if(numberParts.length == 1){
				integerPartDigitString = numberParts[0];
				WhatNumberStringRepresents = 'I';
			}
			
			//if just a fraction with no integer part
			if(numberParts.length == 2){
				numeratorString = numberParts[0];
				denominatorString = numberParts[1];
			}
			
			//if mixed number
			if(numberParts.length == 3){
				integerPartDigitString = numberParts[0];
				numeratorString = numberParts[1];
				denominatorString = numberParts[2];
			}
			
			
			
		}
		else if(masterInput.matches(Constants.INTEGER_EXPRESSION_PATTERN)){
			
			//it is a fraction unless shown to be an integer below
			WhatNumberStringRepresents = 'F';
			
			//initial values for the different parts
			integerPartDigitString = masterInput;
			numeratorString = "0";
			denominatorString = "1";
			
		}
		else{
			return false;
		}
		
		return true;
	}

	@Override
	public ArrayList<BigInteger> validateAndConstructIntegerString(
			String numberStringPart) {
		
		//reverse the string so that it is put in the right order
		StringBuffer numberStringPartBuffer = new StringBuffer(numberStringPart);
		numberStringPart = numberStringPartBuffer.reverse().toString();
		
		char[] theDigits = numberStringPart.toCharArray();
		ArrayList<BigInteger> compiledIntegerList = new ArrayList<BigInteger>(numberStringPart.length());
		BigInteger currentNumber;
		
		for(char digit: theDigits){
			
			currentNumber = CharToNumber(digit);
			if(currentNumber == null){
				return null;
			}
			else if(IntegerHelper.isIntegerBetween(currentNumber, BigInteger.ZERO, inputBase.subtract(BigInteger.ONE))){
				
				compiledIntegerList.add(currentNumber);
				
			}
			else{
				return null;
			}
			
		}
		
		
		return compiledIntegerList;
	}

	@Override
	protected boolean validateAndConstructDecimalParts() {

		//validate and construct initial Part
		if(initialPartString.isEmpty()){
			initialPartDigits = null;
		}
		else{
			initialPartDigits = validateAndConstructIntegerString(initialPartString);
			if(initialPartDigits == null){
				return false;
			}
		}
		
		//validate and construct repeating part
		if(repeatingPartString.isEmpty()){
			repeatingPartDigits = null;
		}
		else{
			repeatingPartDigits = validateAndConstructIntegerString(repeatingPartString);
			if(repeatingPartDigits == null){
				return false;
			}
		}
		
		
		
		return true;
	}

	
	@Override
	protected String IntegerRepToString(RationalNumberRep theIntegerRep) {
		return ArrayListOfDigitsToString(theIntegerRep.getIntegerPartDigits());
	}

	@Override
	protected String DecimalRepToString(DecimalRep theDecimalRep) {
		StringBuilder theDecimal = new StringBuilder();
		
		if(theNumber.getIntegerPart().compareTo(BigInteger.ZERO) > 0){
			theDecimal.append(ArrayListOfDigitsToString(theDecimalRep.getIntegerPartDigits()));
		}
		else{
			theDecimal.append(Constants.ZERO);
		}
		
		if( (theDecimalRep.getTerminatingPartNumber().compareTo(BigInteger.ZERO) > 0) ||
				(theDecimalRep.getRepeatingPartNumber().compareTo(BigInteger.ZERO) > 0) ){
			theDecimal.append(Constants.DECIMAL_POINT);
			
			if(theDecimalRep.getTerminatingPartNumber().compareTo(BigInteger.ZERO) > 0){
				theDecimal.append(ArrayListOfDigitsToString(theDecimalRep.getTerminatingPartDigits()));
			}
			if(theDecimalRep.getRepeatingPartNumber().compareTo(BigInteger.ZERO) > 0){
				theDecimal.append(Constants.UNDERSCORE);
				theDecimal.append(ArrayListOfDigitsToString(theDecimalRep.getRepeatingPartDigits()));
			}
			
		}
		
		return theDecimal.toString();
	}

	@Override
	protected String FractionRepToString(FractionRep theFractionRep) {
		StringBuilder theFraction = new StringBuilder();
		if(theNumber.getIntegerPart().compareTo(BigInteger.ZERO) > 0){
			theFraction.append(ArrayListOfDigitsToString(theFractionRep.getIntegerPartDigits()));
			theFraction.append(Constants.SPACE);
		}
		
		if(theNumber.getNumerator().compareTo(BigInteger.ZERO) > 0){
			theFraction.append(ArrayListOfDigitsToString(theFractionRep.getNumeratorDigits()));
			theFraction.append(Constants.SLASH);
			theFraction.append(ArrayListOfDigitsToString(theFractionRep.getDenominatorDigits()));
		}
		
		if(theFraction.length() == 0){
			theFraction.append(Constants.ZERO);
		}
		
		return theFraction.toString();
	}

	@Override
	protected boolean validateBase(BigInteger base) {
		return IntegerHelper.isIntegerBetween(base, new BigInteger("2"), new BigInteger("36"));
	}

	@Override
	protected String OutputBaseOutOfRangeError() {
		return "Output Base must be between 2 and 36 for Digit String Format";
	}

	@Override
	protected String ArrayListOfDigitsToString(ArrayList<BigInteger> theDigits) {
		StringBuilder integerRep = new StringBuilder(theDigits.size());
		for(BigInteger digit : theDigits){
			integerRep.append(NumberToChar(digit));
		}
		
		//you need to reverse the order 
		return integerRep.reverse().toString();
	}



}
