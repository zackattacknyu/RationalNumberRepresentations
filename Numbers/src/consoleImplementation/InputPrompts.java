package consoleImplementation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;

import coreAccessor.ConvertFromRepImpl;
import coreAccessor.DigitRepresentation;
import coreAccessor.InputHelper;
import coreObjects.Fraction;


public class InputPrompts {
	
	private static final String[] FormatInformation = {
		"",
		"There are three possible formats that you can choose from.",
		"1: Normal Digit Representation (i.e. 3 in base 2 is 11) ",
		"2: Set Representation (i.e. 5 in base 3 is {1,2})",
		"3: N-adic representation (i.e. 10 in base 2 is 1*2^3 + 0*2^2+ 1*2 + 0)",
		"",
		"Which one would you like? "
	};
	private static final String FormatConsoleHint = "Choose your number: ";
	
	
	private static final String InputBasePrompt = "Please enter the Input Base";
	private static final String OutputBasePrompt = "Please enter the Output Base";
	private static final String BaseConsoleHint = "Enter a base: ";
	
	public static BigInteger getFormat(){
		System.out.println();
		OutputPrompts.DisplayLineSet(FormatInformation);
		return getIntegerChoice(FormatConsoleHint,BigInteger.valueOf(1),BigInteger.valueOf(3));
	}
	
	private static BigInteger getBase(boolean isInputBase,int format, int upperBound){
		
		System.out.println();
		
		if(isInputBase){
			System.out.println(InputBasePrompt);
		}
		else{
			System.out.println(OutputBasePrompt);
		}
		
		if(format == 1){ //restrict the base if the format is digit representation
			return getIntegerChoice(BaseConsoleHint, new BigInteger("2"), BigInteger.valueOf(upperBound));
		}
		else{
			return getIntegerChoice(BaseConsoleHint,new BigInteger("2"));
		}
	}
	
	
	public static BigInteger getInputBase(int format, int upperBound){
		return getBase(true,format,upperBound);
	}
	public static BigInteger getOutputBase(int format, int upperBound){
		return getBase(false,format,upperBound);
	}
	
	public static BigInteger getOnlyInteger(int format, DigitRepresentation theRep, 
			BigInteger theBase, String setDelimiter){
		OutputPrompts.DisplayConsecutiveWhiteSpace(2);
		System.out.println("Enter the integer you want converted");
		OutputPrompts.DisplayConsecutiveWhiteSpace(1);
		return getInputInteger(format, theRep, theBase, setDelimiter);
	}
	
	public static BigInteger getInputInteger(int format, DigitRepresentation theRep, 
			BigInteger theBase, String setDelimiter){
		if(format == 1){
			return getInputIntegerInDigitRepFormat(theRep,theBase);
		}
		else if(format == 2){
			return getInputIntegerInSetFormat(theBase, setDelimiter);
		}
		else {
			return getInputIntegerInNadicFormat(theBase);
		}
	}
	
	/**
	 * This will prompt the user and return a BigInteger with what they input.
	 * It is meant to be used if the format is Digit Representation
	 * @return
	 */
	public static BigInteger getInputIntegerInDigitRepFormat(DigitRepresentation theRep, BigInteger theBase){
		return getIntegerFromDigitRep(theRep, theBase);
	}
	
	/**
	 * This will prompt the user and return a BigInteger with what they input. 
	 * It is meant to be used if the format is n-adic format. 
	 * @param theBase
	 * @return	the big integer of what they put in
	 */
	public static BigInteger getInputIntegerInNadicFormat(BigInteger theBase){
		BigInteger numSubs = getIntegerChoice("Enter the highest subscript number:",
				BigInteger.ZERO,new BigInteger("2000000000"));
		int numSubscripts;
		
		if(numSubs != null){
			numSubscripts = numSubs.intValue();
		}
		else{
			return null;
		}
		
		
		System.out.println("Enter the subscripts of the n-adic representation:");
		return getDigitArrayFromMultipleInputs(numSubscripts, 0, theBase);
	}
	
	/**
	 * This will prompt the user and return a BigInteger with what they input. 
	 * It is meant to be used if the format is set format. 
	 * @param theBase
	 * @return	the big integer of what they put in
	 */
	public static BigInteger getInputIntegerInSetFormat(BigInteger theBase, String delimiter){
		BigInteger numSubs = getIntegerChoice("Enter the number of subscripts:",
				BigInteger.ZERO,new BigInteger("2000000000"));
		if(numSubs == null){
			return null;
		}
		int numSubscripts = numSubs.intValue();
				
		return getIntegerFromSetRep(0, numSubscripts, theBase, delimiter);
	}
	
	private static final String integerHelper = "Please enter an integer. Hit enter to go back.";
	private static final String getHelper(BigInteger lowerBound, BigInteger upperBound){
		if(lowerBound == null){
			return integerHelper;
		}
		else if(upperBound == null){
			return "Please enter an integer greater than " + lowerBound + ". Hit enter to go back.";
		}
		else{
			return "Please enter an integer between " + lowerBound + " and " + upperBound + ". Hit enter to go back.";
		}
	}
	
	/**
	 * This allows you to prompt the user and get an integer
	 * @param consoleHint	the prompt before the user input
	 * @return		the BigInteger that the user input, or null if nothing was sucessfully entered
	 */
	public static BigInteger getIntegerChoice(String consoleHint){
		return getIntegerChoice(consoleHint,null,null);
	}
	
	/**
	 * This allows you to prompt the user and get an integer while ensuring the integer will be greater
	 * 		than the lowerBound below
	 * @param consoleHint	the prompt before the user input
	 * @param lowerBound	the lower bound to ensure that the resultant integer will be greater than
	 * @return		the BigInteger that the user input, or null if unsuccessful
	 */
	public static BigInteger getIntegerChoice(String consoleHint, BigInteger lowerBound){
		return getIntegerChoice(consoleHint,lowerBound,null);
	}
	public static BigInteger getIntegerChoice(String consoleHint, BigInteger lowerBound, BigInteger upperBound){
		Scanner result;
		BigInteger theResult = null;
		String entry = "1";
		
		while(theResult == null){
			System.out.print(consoleHint);
			result = new Scanner(System.in);
			entry = result.nextLine();
			if(entry.isEmpty()){
				break;
			}
			if(InputHelper.isIntegerBetween(entry,lowerBound,upperBound)){
				theResult = new BigInteger(entry);
			}
			else{
				System.out.println(getHelper(lowerBound,upperBound));
			}
		}
		
		return theResult;
	}
	
	/**
	 * This will prompt the user for the set representation and return the resulting big integer
	 * @param startingValue		the initial subscript
	 * @param endingValue		the ending subscript
	 * @param theBase			the base being used
	 * @return		the resultant big integer
	 */
	public static BigInteger getIntegerFromSetRep(int startingValue, int endingValue, BigInteger theBase, String delimiter){
		Scanner result;
		BigInteger theResult = null;
		String entry = "1";
		
		while(theResult == null){
			System.out.print("Enter the comma-delimited list of subscripts:");
			result = new Scanner(System.in);
			entry = result.nextLine();
			if(entry.isEmpty()){
				break;
			}
			if(InputHelper.isIntegerSetString(entry, delimiter, theBase)){
				theResult = ConvertFromRepImpl.ConvertToIntegerFromNumberString(entry, delimiter, theBase);
			}
			else{
				System.out.println("String of subscripts invalid. Please re-enter or press enter to exit.");
			}
		}
		
		return theResult;
	}
	
	/**
	 * This will prompt the user for a digit representation of a number and validate that the representation
	 * 		is valid given the base
	 * @param theRep	DigitRepresentation instance used to tell what each number's symbol is
	 * @param theBase	The base of the representation
	 * @return		the integer the user inputted or null if none was entered
	 */
	public static BigInteger getIntegerFromDigitRep(DigitRepresentation theRep, BigInteger theBase){
		Scanner result;
		BigInteger theResult = null;
		String entry = "1";
		
		while( theResult == null ){
			System.out.print("Enter the number in base " + theBase + ": ");
			result = new Scanner(System.in);
			entry = result.nextLine();
			if(entry.isEmpty()){
				break;
			}
			if( theRep.isArrayValid(entry.toCharArray(), theBase.intValue()) ){
				theResult = theRep.ConvertFromDigitRep(entry, theBase.intValue());
			}
			else{
				System.out.println("The number must be in base " + theBase);
			}
		}
		
		return theResult;
	}
	
	/**
	 * This will get the user input by having the user input each a_i value
	 * 		needed for the set/sequence input
	 * @param startingValue		the highest subscript i for a_i
	 * @param endingValue		the lowest subscript i for a_i
	 * @return		the Digit Array
	 */
	public static BigInteger getDigitArrayFromMultipleInputs(int startingValue, int endingValue, BigInteger theBase){
		BigInteger theResult = null;
		ArrayList<BigInteger> results = new ArrayList<BigInteger>(startingValue - endingValue + 1);
		String consoleHint;
		
		for(int i = startingValue; i >= endingValue; i--){
			consoleHint = "a_" + i + " = ";
			theResult = getIntegerChoice(consoleHint,BigInteger.ZERO,theBase.subtract(BigInteger.ONE));
			if(theResult == null){
				results = null;
				break;
			}
			else{
				results.add(theResult);
			}
		}
		
		
		if(theResult != null){
			return ConvertFromRepImpl.ConvertToIntegerFromIntegerArray(results, theBase);
		}
		else{
			return null;
		}
	}
	
	public static Fraction getFractionInput(int format, DigitRepresentation theRep, 
			BigInteger theBase, String setDelimiter){
		BigInteger integerPart;
		BigInteger numerator;
		BigInteger denominator = BigInteger.ZERO;
		
		System.out.println("Enter Integer Part");
		integerPart = getInputInteger(format, theRep, theBase, setDelimiter);
		
		if(integerPart == null) return null;
		
		System.out.println();
		System.out.println("Enter Numerator");
		numerator = getInputInteger(format, theRep, theBase, setDelimiter);
		
		if(numerator == null) return null;
		
		while(denominator.compareTo(BigInteger.ZERO) == 0){
			System.out.println();
			System.out.println("Enter Denominator");
			denominator = getInputInteger(format, theRep, theBase, setDelimiter);
			
			if(denominator == null) return null;
			
			if(denominator.compareTo(BigInteger.ZERO) == 0){
				System.out.println("You cannot have zero in the denominator.");
			}
						
		}
				
		return ConvertFromRepImpl.ConvertToFractionFromIntegers(integerPart, numerator, denominator);
	}
}
