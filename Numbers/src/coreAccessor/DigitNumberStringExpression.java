package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;

import coreObjects.DecimalRep;
import coreObjects.Fraction;
import coreObjects.FractionRep;
import coreObjects.RationalNumberRep;

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
				
		Character decimal = '.';
		char[] comma = {','};
		char[] DecimalSeparators = {'.','_'};
		char[] FractionSeparators = {'/',' '};
		
		String[] numberParts;
		
		//if decimal or fraction
		if(masterInput.contains(decimal.toString())){
			
			WhatNumberStringRepresents = 'D';
			
			//if decimal, split by decimal point and underscore
			numberParts = InputHelper.ParseOutString(masterInput, comma, DecimalSeparators);
			
			//initial values for the different parts
			integerPartDigitString = "0";
			initialPartString = "";
			repeatingPartString = "";
			
			//if not in this format, return false
			if( (numberParts.length < 2)  || (numberParts.length > 3) ){
				return false;
			}
			
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
		else{
			
			numberParts = InputHelper.ParseOutString(masterInput, comma, FractionSeparators);
			
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
			else if(InputHelper.isIntegerBetween(currentNumber, BigInteger.ZERO, inputBase.subtract(BigInteger.ONE))){
				
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String DecimalRepToString(DecimalRep theDecimalRep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected String FractionRepToString(FractionRep theFractionRep) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected boolean validateBase(BigInteger base) {
		return InputHelper.isIntegerBetween(base, new BigInteger("2"), new BigInteger("36"));
	}

	@Override
	protected String OutputBaseOutOfRangeError() {
		return "Output Base must be between 2 and 36 for Digit String Format";
	}



}
