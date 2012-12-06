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
	private UserMessagesHelper userMessages;
	private ResultMessagesHelper resultMessages;
	
	public ProgramInstance(){
		
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
		
		String messageKey = expr.getErrorMessageKey();
		ArrayList<String> info = new ArrayList<String>(10);
		
		if(messageKey == InputExpressionInstance.VALID_INPUT_MESSAGE_KEY){
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
