package ioMethods;

import ioResourceBundles.ResourceBundleConstants;
import ioResourceBundles.ResultMessagesHelper;
import ioResourceBundles.UserMessagesHelper;
import ioUtils.Constants;
import ioUtils.StringHelper;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;

import core.*;
public class ProgramInstance {

	public enum CurrentFunction{
		COMPUTEWITHEXPRESSION, COMPUTEWITHVARIABLESONLY, HELPTEXT, CURRENTVARIABLE, NOTAFUNCTION
	}
	
	private HashMap<String,Fraction> variables;
	private HashMap<String,String> functions;
	private UserMessagesHelper userMessages;
	private ResultMessagesHelper resultMessages;
	private static final int DefaultHashTableSize = 10;
	
	public ProgramInstance(){
		variables = new HashMap<String,Fraction>(DefaultHashTableSize);
		functions = new HashMap<String,String>(DefaultHashTableSize);
		
		variables.put("PREVIOUS", Fraction.ZERO);
		
		functions.put("COMP", "InputExpression");
		functions.put("COMPUTE", "InputExpression");
		functions.put("CALC", "InputExpression");
		functions.put("CALCULATE", "InputExpression");
		
		userMessages = new UserMessagesHelper(Locale.US);
		resultMessages = new ResultMessagesHelper(Locale.US);
	}
	
	
	/**
	 * This is the master function that takes input and makes an array of 
	 * 		strings as the output
	 * @param input			input of the user
	 * @return				output to display to user
	 */
	public ArrayList<String> ResolveConsoleInput(String input){
		CurrentFunction objectToUse = WhichInputExpressionObject(input);
		InputExpressionInstance expr = new InputExpressionInstance(); 
		String variableNameForFraction;
		String variableNameForOutputBase;
		String variableNameForInputBase;
		
		
		
		switch(objectToUse){
		
		case HELPTEXT: return getHelpText();
		
		case CURRENTVARIABLE: return getVariableList();
		
		case NOTAFUNCTION: return userMessages.getMessage(
				ResourceBundleConstants.INVALID_INPUT_ARGS_ERROR_MESSAGE_KEY);
		
		case COMPUTEWITHEXPRESSION: expr = new InputExpressionWithQuotes(input);
				  break;
		
		case COMPUTEWITHVARIABLESONLY: expr = new InputExpressionNoQuotes(input);
				  break;
		
		}
		
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
		
		
		if(expr.getOutputBase().isBaseVariable()){
			variableNameForOutputBase = expr.getOutputBaseString();
			if(variables.containsKey(variableNameForOutputBase)){
				expr.getOutputBase().setBaseAsInteger(
						variables.get(variableNameForOutputBase).getIntegerPart());
			}
			else{
				expr.setMessageKeyToVariableInvalid();
			}
		}
		
		if(expr.getInputBase().isBaseVariable()){
			variableNameForInputBase = expr.getInputBaseString();
			if(variables.containsKey(variableNameForInputBase)){
				expr.getInputBase().setBaseAsInteger(
						variables.get(variableNameForInputBase).getIntegerPart());
			}
			else{
				expr.setMessageKeyToVariableInvalid();
			}
		}
		
		expr.doLaterValidation();
		
		String messageKey = expr.getErrorMessageKey();
		
		if(messageKey == ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY){
			variables.put(expr.getVariableName(), expr.getNumberInputtedAsFraction().getTheNumber());
			return ResultMessagesHelper.getFullResultMessage(resultMessages.getResultHeader(expr.getResultHeaderString()),
					resultMessages.getIntegerPartResult(expr.getOutputBase().getBaseAsInteger().toString(), 
							expr.getIntegerPartOutputString()), 
					resultMessages.getFractionRepResult(expr.getOutputBase().getBaseAsInteger().toString(), 
							expr.getFractionRepOutputString()), 
					resultMessages.getDecimalRepResult(expr.getOutputBase().getBaseAsInteger().toString(), 
							expr.getDecimalRepOutputString()));
		}
		else{
			return userMessages.getMessage(expr.getErrorMessageKey());
		}

	}
	
	
	public ArrayList<String> getHelpText(){
		return userMessages.getMessage(ResourceBundleConstants.helpTextKey);
	}
	
	public ArrayList<String> getVariableList(){
		ArrayList<String> variableListResult = new ArrayList<String>(variables.size() + 3);
		DigitNumberStringExpression currentFraction;
		variableListResult.add("");
		variableListResult.add(userMessages.getMessage(ResourceBundleConstants.variableListHeaderKey).get(0));
		variableListResult.add("");
		for(String key : variables.keySet()){
			currentFraction = new DigitNumberStringExpression(variables.get(key));
			variableListResult.add(String.format("%1$s = %2$s", key, 
					currentFraction.OutputFraction(new BigInteger("10"))));
		}
		
		return variableListResult;
	}
	
	private static CurrentFunction WhichInputExpressionObject(String input){
		if(input.toUpperCase().equals("HELP")){
			return CurrentFunction.HELPTEXT;
		}
		else if(input.toUpperCase().equals("VARS")){
			return CurrentFunction.CURRENTVARIABLE;
		}
		else if(input.toUpperCase().matches(Constants.STRING_HAS_EXPRESSION_IN_QUOTES_PATTERN)){
			return CurrentFunction.COMPUTEWITHEXPRESSION; //expression with quotes object	
					
		}
		else if(input.toUpperCase().matches(Constants.ONE_OR_MORE_NONQUOTES_PATTERN)){
			return CurrentFunction.COMPUTEWITHVARIABLESONLY; //expression without quotes object			
			
		}
		else{
			return CurrentFunction.NOTAFUNCTION; //invalid expression
			
		}
	}
	
	
	
	
}
