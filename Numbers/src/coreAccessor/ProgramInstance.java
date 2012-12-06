package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
	public String[] ResolveConsoleInput(String input){
		userMessages = new UserMessagesHelper(Locale.US);
		resultMessages = new ResultMessagesHelper(Locale.US);
		InputExpressionInstance expr = new InputExpressionInstance(input);
		
		String[] returnMessage;
		String messageKey = expr.getErrorMessageKey();
		ArrayList<String> info = new ArrayList<String>(10);
		
		if(messageKey == InputExpressionInstance.VALID_INPUT_MESSAGE_KEY){
			info.add("");
			info.add(resultMessages.getResultHeader(
					expr.getNumberInputtedAsFraction().OutputFraction(new BigInteger("10"))));
			info.add("");
			info.add(resultMessages.getResultLine("integerPartLabel", expr.getOutputBase(), 
					expr.getIntegerPartOutputString()));
			info.add(resultMessages.getResultLine("fractionRepLabel", expr.getOutputBase(), 
					expr.getFractionRepOutputString()));
			info.add(resultMessages.getResultLine("decimalRepLabel", expr.getOutputBase(), 
					expr.getDecimalRepOutputString()));
			info.add("");
			info.add("");
		}
		else{
			String[] errorMessage = userMessages.getMessage(expr.getErrorMessageKey());
			for(String msgLine: errorMessage){
				info.add(msgLine);
			}
		}
				
		
		//ResourceBundle resultsBundle = ResourceBundle.getBundle("coreAccessor.ComputedResults");
		//info.add(resultsBundle.getObject("userInputPart").toString());
		
		
		returnMessage = new String[info.size()];
		for(int index = 0; index < info.size(); index++){
			returnMessage[index] = info.get(index);
		}
		return returnMessage;
	}
	
	
	
}
