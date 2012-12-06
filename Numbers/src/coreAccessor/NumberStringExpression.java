package coreAccessor;

import java.math.*;
import java.util.ArrayList;

import coreObjects.*;

public abstract class NumberStringExpression{
	
	/*
	 * -----------------------------------------------------------
	 * The variables 
	 * -----------------------------------------------------------
	 */
	
	protected String masterInput;
	protected BigInteger inputBase;
	
	protected boolean StringInThisFormat;
	protected boolean validInputArgs;
	
	protected String integerPartDigitString;
	protected String numeratorString;
	protected String denominatorString;
	protected String initialPartString;
	protected String repeatingPartString;
	
	protected ArrayList<BigInteger> integerPartDigits;
	protected ArrayList<BigInteger> numeratorDigits;
	protected ArrayList<BigInteger> denominatorDigits;
	protected ArrayList<BigInteger> initialPartDigits;
	protected ArrayList<BigInteger> repeatingPartDigits;
	
	protected Fraction theNumber;	
	
	protected String OutputBaseOutOfRangeErrorMessage;
	

	/*
	 * Says what the number string represents
	 * 'I' : integer
	 * 'F' : fraction
	 * 'D' : decimal
	 */
	protected char WhatNumberStringRepresents;
	
	/*
	 * -----------------------------------------------------------
	 * The constructors
	 * -----------------------------------------------------------
	 */
	
	//constructor used for input methods
	public NumberStringExpression(String input, BigInteger inputBase){
		masterInput = input;
		StringInThisFormat = splitNumberStringAndPreValidate();
		setOutputBaseOutOfRangeErrorMessage();
		this.inputBase = inputBase;
		
		if(StringInThisFormat){
			validInputArgs = ( validateNumberStrings() && validateBase(inputBase) );
			if(validInputArgs){
				MakeTheFraction();
			}
			
		}
	}
	
	//constructor to be used for output methods
	public NumberStringExpression(Fraction theFrac){
		setOutputBaseOutOfRangeErrorMessage();
		theNumber = theFrac;
	}
	
	/*
	 * -----------------------------------------------------------
	 * Methods to validate the Base
	 * -----------------------------------------------------------
	 */
	
	protected abstract boolean validateBase(BigInteger base);
	
	private void setOutputBaseOutOfRangeErrorMessage() {
		OutputBaseOutOfRangeErrorMessage = OutputBaseOutOfRangeError();
	}
	
	protected abstract String OutputBaseOutOfRangeError();
	
	/*
	 * -----------------------------------------------------------
	 * The INPUT methods
	 * -----------------------------------------------------------
	 */
	
	public boolean isStringInThisFormat() {
		return StringInThisFormat;
	}

	public boolean isValidInputArgs() {
		return validInputArgs;
	}
	
	/**
	 * This tells whether the input is in the format. 
	 * It also parses out the input into the appropriate strings. 
	 * It also needs to say whether the input is a decimal, fraction, or integer.
	 * @param input    the string that the user inputs
	 * @return		whether or not the string is in that format
	 */
	protected abstract boolean splitNumberStringAndPreValidate();
	
	/**
	 * This validates all the strings to make sure we can proceed with the conversion
	 * @return		whether all the number strings are valid. 
	 */
	protected boolean validateNumberStrings(){
		
		/*
		 * validate the integer part
		 * it should not be empty. 
		 * the previous method should set it to zero if it does not exist
		 */
		integerPartDigits = validateAndConstructIntegerString(integerPartDigitString);
		if(integerPartDigits == null){
			return false;
		}
		
		if(WhatNumberStringRepresents == 'I'){
			return true;
		}
		else if(WhatNumberStringRepresents == 'F'){
			
			/*
			 * validates that the numerator and denominator are proper
			 * 		integers in this format. 
			 * note that they should not empty. 
			 */
			numeratorDigits = validateAndConstructIntegerString(numeratorString); 
			if(numeratorDigits == null){
				return false;
			}
			denominatorDigits = validateAndConstructIntegerString(denominatorString);
			if(denominatorDigits == null){
				return false;
			}
			return true;
			
		}
		else if(WhatNumberStringRepresents == 'D'){
			
			//validates decimal parts. they can be empty. 
			return validateAndConstructDecimalParts();
			
		}
		else{
			/*
			 * PUT POSSIBLE EXCEPTION THROWING HERE
			 */
			return false;
		}
		
	}
	
	/**
	 * This is used to validate the parts of the number string 
	 * 		where integers are specified, mainly 
	 * 		the integer part, numerator, and denominator. 
	 * It will also return the array list of integers corresponding to the
	 * 		digits in this representation
	 * If not valid, null is returned. otherwise a valid array list
	 * @param numberStringPart		the integer string to validate
	 * @return			the ArrayList of Integers representing the numbers
	 */
	public abstract ArrayList<BigInteger> validateAndConstructIntegerString(String numberStringPart);
	
	/**
	 * This is used to validate the initial Part and repeating Part of the decimal.
	 * It should only be called once they are set
	 * @return		whether the initial part and repeating part are valid for this format. 
	 */
	protected abstract boolean validateAndConstructDecimalParts();
	
	/**
	 * Makes the fraction variable
	 */
	protected void MakeTheFraction(){
		
		if(WhatNumberStringRepresents == 'I'){
			theNumber = new Fraction( 
					new RationalNumberRep(integerPartDigits,inputBase) );
		}
		else if(WhatNumberStringRepresents == 'F'){
			theNumber = new Fraction( 
					new FractionRep(inputBase, integerPartDigits, numeratorDigits, denominatorDigits) );
		}
		else if(WhatNumberStringRepresents == 'D'){
			theNumber = new Fraction(
					new DecimalRep(inputBase, integerPartDigits, initialPartDigits, repeatingPartDigits) );
		}
		else{
			/*
			 * PUT POSSIBLE EXCEPTION THROWING HERE
			 */
			theNumber = null;
		}
	}
	
	/*
	 * -----------------------------------------------------------
	 * The OUTPUT methods
	 * -----------------------------------------------------------
	 */
		
	public Fraction getTheNumber() {
		return theNumber;
	}
	
	/**
	 * Takes the fraction and returns a string which is the integer rep for the format.
	 * 		Gets the fraction from the object
	 * @param OutputBase		the output base
	 * @return		the appropriate string given the format
	 */
	public String OutputInteger(BigInteger OutputBase){
		
		//returns error if output base is out of range
		if(!validateBase(OutputBase)){
			return OutputBaseOutOfRangeErrorMessage;
		}
		
		/*
		 * gets the integer rep from the fraction and then 
		 *    calls the method which converts an integer rep
		 *    to a string in the currently implemented format
		 */
		return IntegerRepToString(theNumber.GetIntegerRep(OutputBase));
	}
	
	/**
	 * Takes the fraction and returns a string which is the fraction rep for the format
	 * 		Gets the fraction from the object
	 * @param OutputBase		the output base
	 * @return		the appropriate string given the format
	 */
	public String OutputFraction(BigInteger OutputBase){
		
		//returns error if output base is out of range
		if(!validateBase(OutputBase)){
			return OutputBaseOutOfRangeErrorMessage;
		}
		
		/*
		 * gets the fraction rep from the fraction and then 
		 *    calls the method which converts a fraction rep
		 *    to a string in the currently implemented format
		 */
		return FractionRepToString(theNumber.GetFractionRep(OutputBase));
	}
	
	/**
	 * Takes the fraction and returns a string which is the decimal rep for the format
	 * 		Gets the fraction from the object
	 * @param OutputBase		the output base
	 * @return		the appropriate string given the format
	 */
	public String OutputDecimal(BigInteger OutputBase){
		
		//returns error if output base is out of range for the format
		if(!validateBase(OutputBase)){
			return OutputBaseOutOfRangeErrorMessage;
		}
		
		/*
		 * gets the decimal rep from the decimal and then 
		 *    calls the method which converts a decimal rep
		 *    to a string in the currently implemented format
		 */
		return DecimalRepToString(theNumber.GetDecimalRep(OutputBase));
		
	}
	
	protected abstract String ArrayListOfDigitsToString(ArrayList<BigInteger> theDigits);
	
	/**
	 * Takes an integer rep object and makes the appropriate string
	 * @param theIntegerRep			the integer rep object
	 * @return		the appropriate string for the format
	 */
	protected abstract String IntegerRepToString(RationalNumberRep theIntegerRep);
	
	/**
	 * Takes a decimal rep object and makes the appropriate string
	 * @param theDecimalRep			the decimal rep object
	 * @return		the appropriate string for the format
	 */
	protected abstract String DecimalRepToString(DecimalRep theDecimalRep);
	
	/**
	 * Takes a fraction rep object and makes the appropriate string
	 * @param theFractionRep			the fraction rep object
	 * @return		the appropriate string for the format
	 */
	protected abstract String FractionRepToString(FractionRep theFractionRep);
	
}
