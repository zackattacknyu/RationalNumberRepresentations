package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;

public class InputHelper {
	
	
	//TEST CODE
	public static String DisplayArrayList(ArrayList<BigInteger> ToDisplay){
		StringBuffer list = new StringBuffer();
		for(int index = 0; index < ToDisplay.size(); index++){
			list.append(ToDisplay.get(index));
			list.append(" ");
		}
		return list.toString();
	}
	
	/**
	 * takes a delimited list and outputs the parts with the
	 * 		empty ones ignored
	 * @param input		the delimited string
	 * @return			an array with the empty parts left out when splitting
	 */
	public static ArrayList<String> MakeStringArray(String input, char[] ToSplitBy){
		String[] theParts = ParseOutString(input,null,ToSplitBy);
		ArrayList<String> parts = new ArrayList<String>(theParts.length);
		for(String part: theParts){
			if(!part.isEmpty()){
				parts.add(part);
			}
		}
		return parts;
	}
	
	/*
	 * This parses out a string by doing two things: 
	 * 		it ignores the characters it should ignore
	 * 		it splits by the characters it is told to
	 */
	public static String[] ParseOutString(String input, char[] ToIgnore, char[] ToSplitBy){
		char[] inputCharArray = input.toCharArray();
		StringBuffer assembledString = new StringBuffer();
				
		for(char currentChar: inputCharArray){
			if(inInCharArray(ToSplitBy,currentChar)){
				assembledString.append(':');
			}
			else if(!inInCharArray(ToIgnore,currentChar)){
				assembledString.append(currentChar);
			}
		}
		return assembledString.toString().split(":");
	}
	
	private static boolean inInCharArray(char[] arrayToCheck,char characterToCheckFor){
		if(arrayToCheck == null){
			return false;
		}
		for(char currentChar : arrayToCheck){
			if(currentChar == characterToCheckFor){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isInteger(String input){
		if(input.matches("[0-9]+")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static boolean isIntegerSetString(String input, String delimiter, BigInteger theBase){
		String[] theDigits = input.split(delimiter);
		
		for(String digit : theDigits){
			if(  !isIntegerBetween( digit, BigInteger.ZERO, theBase.subtract(BigInteger.ONE) )  ){
				return false;
			}
		}
		
		return true;
	}
	
	public static boolean isIntegerGreaterThan(String input, BigInteger lowerBound){
		return isIntegerBetween(input,lowerBound,null);
	}
	public static boolean isIntegerGreaterThan(BigInteger input, BigInteger lowerBound){
		return isIntegerBetween(input,lowerBound,null);
	}
	
	public static boolean isIntegerBetween(BigInteger input, BigInteger lowerBound, BigInteger upperBound){

		if(input.compareTo(lowerBound) < 0){
			return false;
		}
		
		if(upperBound == null){
			return true;
		}
		
		if(input.compareTo(upperBound) > 0){
			return false;
		}
		
		return true;
	}
	public static boolean isIntegerBetween(String input, BigInteger lowerBound, BigInteger upperBound){
		if(!isInteger(input)){
			return false;
		}
				
		if(lowerBound == null){
			return true;
		}
		
		return isIntegerBetween(new BigInteger(input),lowerBound,upperBound);
		
	}

}
