package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;

import coreAccessorResourceBundles.ResourceBundleConstants;
import coreAccessorUtils.Constants;
import coreAccessorUtils.StringHelper;
import coreAccessorUtils.IntegerHelper;
import coreObjects.Fraction;

public class InputExpressionInstance {
	
	protected String wholeInputString;
	protected String variableName;
	protected String functionName;
	protected String errorMessageKey;
	protected String numberInputted;
	protected String inputBase;
	protected String outputBase;
	protected String outputFormat;
	protected String wholeFunctionString;
	protected String functionInputsString;
	protected boolean isValidExpression;
	protected NumberStringExpression numberInputtedAsFraction;
	
	public InputExpressionInstance(){
		
		
	}
	
	protected void initialValidation(String input){
		variableName = Constants.defaultVariableName;
		functionName = Constants.defaultFunctionName;
		inputBase = Constants.defaultInputBase;
		outputBase = Constants.defaultOutputBase;
		outputFormat = Constants.defaultOutputFormat;
		wholeInputString = input;
		isValidExpression = true;
		
		for(int validator = 0; validator < 2; validator++){
			
			if(!isValidExpression) break;
			
			switch(validator){
			
			case 0: errorMessageKey = splitIntoVariableAndFunction(); break;
			
			case 1: errorMessageKey = splitIntoFunctionNameAndFunctionInputs(); break;
			
			}
			
		}
	}
	
	protected void laterValidation(){
		for(int validator = 0; validator < 3; validator++){
			
			if(!isValidExpression) break;
			
			switch(validator){
			
			case 0: errorMessageKey = validateNames(); break;
			
			case 1: errorMessageKey = validateBases(); break;
			
			case 2: errorMessageKey = validateFormat(); break;
			
			}
			
		}
	}
	
	public String getIntegerPartOutputString(){
		return numberInputtedAsFraction.OutputInteger(new BigInteger(outputBase));
	}
	
	public String getFractionRepOutputString(){
		return numberInputtedAsFraction.OutputFraction(new BigInteger(outputBase));
	}
	
	public String getDecimalRepOutputString(){
		return numberInputtedAsFraction.OutputDecimal(new BigInteger(outputBase));
	}
	
	public String getResultHeaderString(){
		return numberInputtedAsFraction.OutputFraction(new BigInteger("10"));
	}
	
	public NumberStringExpression getNumberInputtedAsFraction() {
		return numberInputtedAsFraction;
	}

	public void validateNumberInputtedInExpressionForm() {
			
		for(NumberStringExpressionFormat format : NumberStringExpressionFormat.values()){
			numberInputtedAsFraction = NumberStringExpressionFormat.getExpressionObject
					(format,numberInputted,new BigInteger(inputBase));
			
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
	
	public void setMessageKeyToVariableInvalid(){
		errorMessageKey = ResourceBundleConstants.VARIABLE_NAME_INVALID_ERROR_MESSAGE_KEY;
	}
	
	public void setNumberInputtedFraction(Fraction newFraction){
		numberInputtedAsFraction = new DigitNumberStringExpression(newFraction);
	}

	public String getVariableName() {
		return variableName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public String getErrorMessageKey() {
		return errorMessageKey;
	}
	
	private String validateFormat(){
		if(validFormat(outputFormat)){
			return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
		}
		else{
			isValidExpression = false;
			return ResourceBundleConstants.INVALID_FORMAT_ERROR_MESSAGE_KEY;
		}
	}
	
	private String validateNames(){
		/*
		 * This validates the variable entered. 
		 */
		if(!StringHelper.validVariableFunctionName(variableName)){
			isValidExpression = false;
			return ResourceBundleConstants.VARIABLE_NAME_INVALID_ERROR_MESSAGE_KEY;
		}
		
		if(!StringHelper.validVariableFunctionName(functionName)){
			isValidExpression = false;
			return ResourceBundleConstants.FUNCTION_INVALID_ERROR_MESSAGE_KEY;
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
	}
	
	private String validateBases(){
		if(!validBase(inputBase)){
			isValidExpression = false;
			return ResourceBundleConstants.INVALID_INPUT_BASE_ERROR_MESSAGE_KEY;
		}
		if(!validBase(outputBase)){
			isValidExpression = false;
			return ResourceBundleConstants.INVALID_OUTPUT_BASE_ERROR_MESSAGE_KEY;
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
	}
	
	private boolean validBase(String base){
		if(StringHelper.validVariableFunctionName(base)){
			return true;
		}
		else if(IntegerHelper.isInteger(base)){
			return true;
		}
		else{
			return false;
		}
	}
	
	private boolean validFormat(String format){
		
		/*
		 * **PUT CODE HERE LATER**
		 */
		return true;
	}
	
	private String splitIntoVariableAndFunction(){
		String[] expressionParts;
		
		/*
		 * This function takes in the input and splits it by the equals sign
		 * 		into the variable name and the function
		 */
		expressionParts = wholeInputString.split(Constants.EQUALS_SIGN_IN_REGEX);
		if(expressionParts.length > 2){
			isValidExpression = false;
			return ResourceBundleConstants.EQUALS_SIGN_ERROR_MESSAGE_KEY;
		}
		else if(expressionParts.length == 2){
			variableName = expressionParts[0]; wholeFunctionString = expressionParts[1];
		}
		else if(expressionParts.length == 1){
			wholeFunctionString = expressionParts[0];
		}
		else{
			isValidExpression = false;
			return ResourceBundleConstants.EMPTY_STRING_ERROR_MESSAGE_KEY;
		}
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
	}
	
	private String splitIntoFunctionNameAndFunctionInputs(){
		String[] functionParts;
		
		/*
		 * This splits up the function part into function name and inputs
		 * It first makes the function into a splittable form by leaving out the close parenthesis
		 * 		and converting the open parenthesis into a colon
		 */
		functionParts = StringHelper.takeOutCharsAndSplitString(wholeFunctionString, 
				Constants.OPEN_PARENTHESIS_IN_REGEX,
				Constants.CLOSE_PARENTESIS_IN_REGEX);
		if(functionParts.length != 2){
			isValidExpression = false;
			return ResourceBundleConstants.FUNCTION_INVALID_ERROR_MESSAGE_KEY;
		}
		functionName = functionParts[0];
		functionInputsString = functionParts[1];
		
		return ResourceBundleConstants.VALID_INPUT_MESSAGE_KEY;
	}

	
	public boolean isValidExpression() {
		return isValidExpression;
	}

	public String getNumberInputted() {
		return numberInputted;
	}

	public String getInputBase() {
		return inputBase;
	}

	public String getOutputBase() {
		return outputBase;
	}

	public String getOutputFormat() {
		return outputFormat;
	}
}
