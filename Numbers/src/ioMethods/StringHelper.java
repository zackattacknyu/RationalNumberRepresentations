package ioMethods;


import java.util.ArrayList;

public class StringHelper {
	
	
	/**
	 * takes a delimited list and outputs the parts with the
	 * 		empty ones ignored
	 * @param input		the delimited string
	 * @param regex 	the regular expression to use in splitting
	 * @return			an array with the empty parts left out when splitting
	 */
	public static ArrayList<String> splitStringIntoNonemptyParts(String input, String regex){
		String[] theParts = input.split(regex);
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
	public static String[] takeOutCharsAndSplitString(String input, String regexToSplitBy, String regexToIgnore){
		
		return takeOutChars(input, regexToIgnore).split(regexToSplitBy);
	}
	
	public static String takeOutChars(String input, String regexToIgnore){
		return input.replaceAll(regexToIgnore, "");
	}
	
	/**
	 * This validates variable and function names. 
	 * They must start with a letter and only be letters and numbers
	 * This is meant to follow standard programming practice
	 * @param variable		the variable or function name
	 * @return		whether it follows standard syntax
	 */
	public static boolean validVariableName(String variable){
		
		//if variable is null, make it valid by default
		if(variable.isEmpty()){
			return true;
		}
		
		//does it match one letter, then letters and numbers format
		if(variable.toUpperCase().matches(Constants.VARIABLE_NAME_PATTERN)){
			return true;
		}
		
		return false;
		
	}

}
