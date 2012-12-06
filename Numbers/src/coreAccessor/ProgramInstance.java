package coreAccessor;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import coreObjects.*;
public class ProgramInstance {

	
	private Hashtable<String,Fraction> variables;
	private UserMessagesHelper userMessages;
	
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
		InputExpressionInstance expr = new InputExpressionInstance(input);
		String[] errorMessage = userMessages.getMessage(expr.getErrorMessageKey());
		String[] returnMessage;
		
		ArrayList<String> info = new ArrayList<String>(10);
		
		ResourceBundle resultsBundle = ResourceBundle.getBundle("coreAccessor.ComputedResults");
		info.add(resultsBundle.getObject("userInputPart").toString());
		info.add("varName = " + expr.getVariableName());
		info.add("FuncName = " + expr.getFunctionName());
		info.add("number = " + expr.getNumberInputted());
		info.add("inputBase = " + expr.getInputBase());
		info.add("outputBase = " + expr.getOutputBase());
		info.add("outputFormat = " + expr.getOutputFormat());
		for(String msgLine: errorMessage){
			info.add(msgLine);
		}
		
		returnMessage = new String[info.size()];
		for(int index = 0; index < info.size(); index++){
			returnMessage[index] = info.get(index);
		}
		return returnMessage;
	}
	
	
	
}
