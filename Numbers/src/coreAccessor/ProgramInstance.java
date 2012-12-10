package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import coreAccessorResourceBundles.ResourceBundleConstants;
import coreAccessorResourceBundles.ResultMessagesHelper;
import coreAccessorResourceBundles.UserMessagesHelper;
import coreAccessorUtils.Constants;
import coreAccessorUtils.StringHelper;
import coreObjects.*;
public class ProgramInstance {

	
	private Hashtable<String,Fraction> variables;
	private Hashtable<String,String> functions;
	private UserMessagesHelper userMessages;
	private ResultMessagesHelper resultMessages;
	private static final int DefaultHashTableSize = 10;
	
	public ProgramInstance(){
		variables = new Hashtable<String,Fraction>(DefaultHashTableSize);
		functions = new Hashtable<String,String>(DefaultHashTableSize);
		
		variables.put("PREVIOUS", Fraction.ZERO);
		
		functions.put("COMP", "InputExpression");
		functions.put("COMPUTE", "InputExpression");
		functions.put("CALC", "InputExpression");
		functions.put("CALCULATE", "InputExpression");
	}
	
	
	/**
	 * This is the master function that takes input and makes an array of 
	 * 		strings as the output
	 * @param input			input of the user
	 * @return				output to display to user
	 */
	public ArrayList<String> ResolveConsoleInput(String input){
		userMessages = new UserMessagesHelper(Locale.US);
		resultMessages = new ResultMessagesHelper(Locale.US);
		char objectToUse = WhichInputExpressionObject(input);
		InputExpressionInstance expr = new InputExpressionInstance(); 
		
		switch(objectToUse){
		
		case 'X': return userMessages.getMessage(
				ResourceBundleConstants.INVALID_INPUT_ARGS_ERROR_MESSAGE_KEY);
		
		case 'W': expr = new InputExpressionWithQuotes(input);
				  break;
		
		case 'N': expr = new InputExpressionNoQuotes(input);
				  break;
		
		}
		String variableNameForFraction;
		
		String functionNameUsed = expr.getFunctionName().toUpperCase();
		if(!functions.containsKey(functionNameUsed)){
			return resultMessages.getFunctionNameError(functionNameUsed);
		}
		
		if(expr instanceof InputExpressionNoQuotes){
			variableNameForFraction = expr.getNumberInputted();
			if(variables.containsKey(variableNameForFraction)){
				expr.setNumberInputtedFraction(variables.get(variableNameForFraction));
			}
			else{
				expr.setMessageKeyToVariableInvalid();
			}
		}
		
		String messageKey = expr.getErrorMessageKey();
		ArrayList<String> info = new ArrayList<String>(10);
		
		if(messageKey == ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY){
			variables.put(expr.getVariableName(), expr.getNumberInputtedAsFraction().getTheNumber());
			info.add("");
			info.add(resultMessages.getResultHeader(expr.getResultHeaderString()));
			info.add("");
			info.add(resultMessages.getIntegerPartResult(expr.getOutputBase(), 
					expr.getIntegerPartOutputString()));
			info.add(resultMessages.getFractionRepResult(expr.getOutputBase(), 
					expr.getFractionRepOutputString()));
			info.add(resultMessages.getDecimalRepResult(expr.getOutputBase(), 
					expr.getDecimalRepOutputString()));
			info.add("");
			info.add("");
			return info;
		}
		else{
			return userMessages.getMessage(expr.getErrorMessageKey());
		}

	}
	
	
	private static char WhichInputExpressionObject(String input){
		if(input.toUpperCase().matches(Constants.STRING_HAS_EXPRESSION_IN_QUOTES_PATTERN)){
			return 'W'; //expression with quotes object	
					
		}
		else if(input.toUpperCase().matches(Constants.ONE_OR_MORE_NONQUOTES_PATTERN)){
			return 'N'; //expression without quotes object			
			
		}
		else{
			return 'X'; //invalid expression
			
		}
	}
	
	
	
	
}
