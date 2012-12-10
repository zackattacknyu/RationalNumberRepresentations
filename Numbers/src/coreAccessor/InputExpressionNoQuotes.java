package coreAccessor;

import java.util.ArrayList;

import coreAccessorResourceBundles.ResourceBundleConstants;
import coreAccessorUtils.Constants;
import coreAccessorUtils.StringHelper;

public class InputExpressionNoQuotes extends InputExpressionInstance{

	public InputExpressionNoQuotes(String input){
		super();
		initialValidation(input);
		validateInputPartsString();
		laterValidation();
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
			outputBase = inputParts.get(1);
		}
		if(inputParts.size() > 2){
			outputFormat = inputParts.get(2);
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;

	}
	


	
}
