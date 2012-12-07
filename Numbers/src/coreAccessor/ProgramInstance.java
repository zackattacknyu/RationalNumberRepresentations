package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import coreAccessorResourceBundles.ResultMessagesHelper;
import coreAccessorResourceBundles.UserMessagesHelper;
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
		InputExpressionInstance expr = new InputExpressionInstance(input);
		String variableNameForFraction;
		
		String functionNameUsed = expr.getFunctionName().toUpperCase();
		if(!functions.containsKey(functionNameUsed)){
			return resultMessages.getFunctionNameError(functionNameUsed);
		}
		
		if(expr.isNumberInputtedIsVariable()){
			variableNameForFraction = expr.getNumberInputted();
			if(variables.containsKey(variableNameForFraction)){
				expr.setNumberInputtedFraction(variables.get(variableNameForFraction));
			}
			else{
				expr.setMessageKeyToVariableInvalid();
			}
		}
		else{
			expr.validateNumberInputtedInExpressionForm();
		}
		
		String messageKey = expr.getErrorMessageKey();
		ArrayList<String> info = new ArrayList<String>(10);
		
		if(messageKey == InputExpressionInstance.VALID_INPUT_MESSAGE_KEY){
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
	
	
	
	
}
