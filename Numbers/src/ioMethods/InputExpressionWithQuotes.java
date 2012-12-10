package ioMethods;

import ioResourceBundles.ResourceBundleConstants;
import ioUtils.Constants;
import ioUtils.StringHelper;

import java.math.BigInteger;
import java.util.ArrayList;


public class InputExpressionWithQuotes extends InputExpressionInstance{
	
	public InputExpressionWithQuotes(String input){
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
	
	public void doLaterValidation(){
		if(isValidExpression){
			validateNumberInputtedInExpressionForm();
			laterValidation();
		}
	}

	public void validateNumberInputtedInExpressionForm() {
			
		for(NumberStringExpressionFormat format : NumberStringExpressionFormat.values()){
			numberInputtedAsFraction = NumberStringExpressionFormat.getExpressionObject
					(format,numberInputted, inputBase.getBaseAsInteger());
			
			//sees if the input matches the format
			if(numberInputtedAsFraction != null){
				if(numberInputtedAsFraction.isStringInThisFormat()){
					
					if(numberInputtedAsFraction.isValidInputArgs()){
						isValidExpression = true;
						errorMessageKey =ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY; return;
					}
					else{
						isValidExpression = false;
						errorMessageKey = ResourceBundleConstants.INVALID_INPUT_ARGS_ERROR_MESSAGE_KEY; return;
					}
					
				}
			}
			
		}
		
		isValidExpression = false;
		errorMessageKey = ResourceBundleConstants.INVALID_NUMBER_EXPRESSION_ERROR_MESSAGE_KEY; return;
	}
	
	

	public String validateInputPartsString(){
		ArrayList<String> inputParts;
		
		//splits the function parts by quotes
		String[] functionInputParts = functionInputsString.split(Constants.QUOTES_IN_REGEX);
		
		//the number inputted
		numberInputted = functionInputParts[1];
		
		//if no input parameters were specified
		if(functionInputParts.length < 3){ 
			return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
		}
		
		inputParts = StringHelper.splitStringIntoNonemptyParts(
				functionInputParts[2],Constants.COMMA_IN_REGEX);
		
		if( inputParts.size() > 3 ){
			isValidExpression = false;
			return ResourceBundleConstants.INPUT_WITH_QUOTES_INVALID_ERROR_MESSAGE_KEY;
		}
		inputBaseString = inputParts.get(0);
		if(inputParts.size() > 1){
			outputBaseString = inputParts.get(1);
		}
		if(inputParts.size() > 2){
			outputFormat = inputParts.get(2);
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;

	}
	
}
