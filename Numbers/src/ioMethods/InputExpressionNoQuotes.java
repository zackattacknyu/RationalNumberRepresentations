package ioMethods;

import ioResourceBundles.ResourceBundleConstants;
import ioUtils.Constants;
import ioUtils.StringHelper;

import java.util.ArrayList;


public class InputExpressionNoQuotes extends InputExpressionInstance{

	public InputExpressionNoQuotes(String input){
		super();
		initialValidation(input);
		
		for(int validator = 0; validator < 2; validator++){
			
			if(!isValidExpression) break;
			
			switch(validator){
			
			case 0: errorMessageKey = validateInputPartsString(); break;
			
			case 1: errorMessageKey = validateBases(); break;
			
			}
			
		}
		
	}
	

	public String validateInputPartsString(){

		ArrayList<String> inputParts = StringHelper.splitStringIntoNonemptyParts(
				functionInputsString,Constants.COMMA_IN_REGEX);
		
		if((inputParts.size() < 1) || (inputParts.size() > 3)){
			isValidExpression = false;
			return ResourceBundleConstants.INPUT_WITHOUT_QUOTES_INVALID_ERROR_MESSAGE_KEY;
		}

		
		numberInputted = inputParts.get(0);
		
		if(inputParts.size() > 1){
			outputBaseString = inputParts.get(1);
		}
		if(inputParts.size() > 2){
			outputFormat = inputParts.get(2);
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;

	}
	


	
}
