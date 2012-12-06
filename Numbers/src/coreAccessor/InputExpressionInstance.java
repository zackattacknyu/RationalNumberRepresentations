package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;

import coreAccessorUtils.Constants;
import coreAccessorUtils.StringHelper;
import coreAccessorUtils.IntegerHelper;
import coreObjects.Fraction;

public class InputExpressionInstance {
	
	/*
	 * The following are the keys behind the error messages
	 * 		that could result after invoking this class.
	 * These keys are used to reference the correct string
	 * 		in the resource bundle. 
	 */
	private static final String VALID_INPUT_MESSAGE_KEY = "ValidInputStringMessage";
	private static final String EQUALS_SIGN_ERROR_MESSAGE_KEY = "EqualsSignErrorMessage";
	private static final String VARIABLE_NAME_INVALID_ERROR_MESSAGE_KEY = "VariableNameInvalidErrorMessage";
	private static final String EMPTY_STRING_ERROR_MESSAGE_KEY = "EmptyStringErrorMessage";
	private static final String FUNCTION_INVALID_ERROR_MESSAGE_KEY = "FunctionInvalidErrorMessage";
	private static final String INPUT_WITH_QUOTES_INVALID_ERROR_MESSAGE_KEY = "InputWithQuotesInvalidErrorMessage";
	private static final String INPUT_WITHOUT_QUOTES_INVALID_ERROR_MESSAGE_KEY = "InputWithoutQuotesInvalidErrorMessage";
	private static final String NO_CLOSING_QUOTE_ERROR_MESSAGE_KEY = "NoClosingQuoteMessage";
	private static final String INVALID_INPUT_BASE_ERROR_MESSAGE_KEY = "InvalidInputBaseErrorMessage";
	private static final String INVALID_OUTPUT_BASE_ERROR_MESSAGE_KEY = "InvalidOutputBaseErrorMessage";
	private static final String INVALID_NUMBER_EXPRESSION_ERROR_MESSAGE_KEY = "InvalidNumberExpressionErrorMessage";
	private static final String INVALID_INPUT_ARGS_ERROR_MESSAGE_KEY = "InvalidInputArgumentsforExpressionFormatMessage";
	private static final String INVALID_FORMAT_ERROR_MESSAGE_KEY = "InvalidFormatErrorMessage";
	
	
	
	private String variableName;
	private String functionName;
	private String errorMessageKey;
	private String numberInputted;
	private String inputBase;
	private String outputBase;
	private String outputFormat;
	private boolean isValidExpression;
	private NumberStringExpression numberInputtedAsFraction;
	
	public InputExpressionInstance(String input){
		variableName = Constants.defaultVariableName;
		functionName = Constants.defaultFunctionName;
		inputBase = Constants.defaultInputBase;
		outputBase = Constants.defaultOutputBase;
		outputFormat = Constants.defaultOutputFormat;
		isValidExpression = true;
		
		for(int validator = 0; validator < 5; validator++){
			
			if(!isValidExpression) break;
			
			switch(validator){
			
			case 0: errorMessageKey = splitStringAndPreValidate(input); break;
			
			case 1: errorMessageKey = validateNames(); break;
			
			case 2: errorMessageKey = validateBases(); break;
			
			case 3: errorMessageKey = validateFormat(); break;
			
			case 4: errorMessageKey = validateNumberInputted(); break;
			
			}
			
		}
		
		
		//TEST CODE
		if(isValidExpression){
			Fraction ourFrac = numberInputtedAsFraction.getTheNumber();
			System.out.println();
			System.out.println("Int Part: " + ourFrac.getIntegerPart());
			System.out.println("Integer Part in Base " + outputBase + ": " + 
					numberInputtedAsFraction.OutputInteger(new BigInteger(outputBase)));
			System.out.println("Decimal Rep in Base " + outputBase + ":" +
					numberInputtedAsFraction.OutputDecimal(new BigInteger(outputBase)));
			System.out.println("Fraction Rep in Base " + outputBase + ":" +
					numberInputtedAsFraction.OutputFraction(new BigInteger(outputBase)));
			System.out.println("Numerator: " + ourFrac.getNumerator());
			System.out.println("Denominator: " + ourFrac.getDenominator());
		}
		
	}
	
	private String validateNumberInputted() {
		
		for(NumberStringExpressionFormat format : NumberStringExpressionFormat.values()){
			numberInputtedAsFraction = NumberStringExpressionFormat.getExpressionObject
					(format,numberInputted,new BigInteger(inputBase));
			
			//sees if the input matches the format
			if(numberInputtedAsFraction != null){
				if(numberInputtedAsFraction.isStringInThisFormat()){
					
					if(numberInputtedAsFraction.isValidInputArgs()){
						isValidExpression = true;
						return VALID_INPUT_MESSAGE_KEY;
					}
					else{
						isValidExpression = false;
						return INVALID_INPUT_ARGS_ERROR_MESSAGE_KEY;
					}
					
				}
			}
			
		}
		
		isValidExpression = false;
		return INVALID_NUMBER_EXPRESSION_ERROR_MESSAGE_KEY;
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
			return VALID_INPUT_MESSAGE_KEY;
		}
		else{
			isValidExpression = false;
			return INVALID_FORMAT_ERROR_MESSAGE_KEY;
		}
	}
	
	private String validateNames(){
		/*
		 * This validates the variable entered. 
		 */
		if(!validVariableFunctionName(variableName)){
			isValidExpression = false;
			return VARIABLE_NAME_INVALID_ERROR_MESSAGE_KEY;
		}
		
		if(!validVariableFunctionName(functionName)){
			isValidExpression = false;
			return FUNCTION_INVALID_ERROR_MESSAGE_KEY;
		}
		
		return VALID_INPUT_MESSAGE_KEY;
	}
	
	private String validateBases(){
		if(!validBase(inputBase)){
			isValidExpression = false;
			return INVALID_INPUT_BASE_ERROR_MESSAGE_KEY;
		}
		if(!validBase(outputBase)){
			isValidExpression = false;
			return INVALID_OUTPUT_BASE_ERROR_MESSAGE_KEY;
		}
		
		return VALID_INPUT_MESSAGE_KEY;
	}
	
	private boolean validBase(String base){
		if(validVariableFunctionName(base)){
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

	private String splitStringAndPreValidate(String input){
		String function = "";
		String[] expressionParts;
		String[] functionParts;
		ArrayList<String> inputParts;
		String functionInputs = "";
		
		//makes sure string is non-null
		if(input.isEmpty()){
			isValidExpression = false;
			return EMPTY_STRING_ERROR_MESSAGE_KEY;
		}
		
		/*
		 * This function takes in the input and splits it by the equals sign
		 * 		into the variable name and the function
		 */
		expressionParts = input.split(Constants.EQUALS_SIGN_IN_REGEX);
		if(expressionParts.length > 2){
			isValidExpression = false;
			return EQUALS_SIGN_ERROR_MESSAGE_KEY;
		}
		else if(expressionParts.length == 2){
			variableName = expressionParts[0]; function = expressionParts[1];
		}
		else if(expressionParts.length == 1){
			function = expressionParts[0];
		}
		else{
			isValidExpression = false;
			return EMPTY_STRING_ERROR_MESSAGE_KEY;
		}
		
		/*
		 * This splits up the function part into function name and inputs
		 * It first makes the function into a splittable form by leaving out the close parenthesis
		 * 		and converting the open parenthesis into a colon
		 */
		functionParts = StringHelper.takeOutCharsAndSplitString(function, 
				Constants.OPEN_PARENTHESIS_IN_REGEX,
				Constants.CLOSE_PARENTESIS_IN_REGEX);
		if(functionParts.length != 2){
			isValidExpression = false;
			return FUNCTION_INVALID_ERROR_MESSAGE_KEY;
		}
		functionName = functionParts[0];
		functionInputs = functionParts[1];
		
		
		/*
		 * This splits up the function Inputs into their appropriate strings
		 */
		if(functionInputs.matches(Constants.STRING_HAS_EXPRESSION_IN_QUOTES_PATTERN)){
			
			//splits the function parts by quotes
			String[] functionInputParts = functionInputs.split(Constants.QUOTES_IN_REGEX);
			
			//the number inputted
			numberInputted = functionInputParts[1];
			
			//if no input parameters were specified
			if(functionInputParts.length < 3){ 
				return VALID_INPUT_MESSAGE_KEY;
			}
			
			inputParts = StringHelper.splitStringIntoNonemptyParts(
					functionInputParts[2],Constants.COMMA_IN_REGEX);
			
			if( inputParts.size() > 3 ){
				isValidExpression = false;
				return INPUT_WITH_QUOTES_INVALID_ERROR_MESSAGE_KEY;
			}
			inputBase = inputParts.get(0);
			if(inputParts.size() > 1){
				outputBase = inputParts.get(1);
			}
			if(inputParts.size() > 2){
				outputFormat = inputParts.get(2);
			}
						
		}
		else if(functionInputs.matches(Constants.ONE_OR_MORE_NONQUOTES_PATTERN)){
			
			inputParts = StringHelper.splitStringIntoNonemptyParts(
					functionInputs,Constants.COMMA_IN_REGEX);
			
			if((inputParts.size() < 1) || (inputParts.size() > 3)){
				isValidExpression = false;
				return INPUT_WITHOUT_QUOTES_INVALID_ERROR_MESSAGE_KEY;
			}
			
			numberInputted = inputParts.get(0);
			
			if(inputParts.size() > 1){
				outputBase = inputParts.get(1);
			}
			if(inputParts.size() > 2){
				outputFormat = inputParts.get(2);
			}
			
			
		}
		else{
			
			isValidExpression = false;
			return NO_CLOSING_QUOTE_ERROR_MESSAGE_KEY;
			
		}
		
		return VALID_INPUT_MESSAGE_KEY;

	}
	
	


	/**
	 * This validates variable and function names. 
	 * They must start with a letter and only be letters and numbers
	 * This is meant to follow standard programming practice
	 * @param variable		the variable or function name
	 * @return		whether it follows standard syntax
	 */
	public static boolean validVariableFunctionName(String variable){
		
		//if variable is null, make it valid by default
		if(variable.isEmpty()){
			return true;
		}
		
		//does it match one letter, then letters and numbers format
		if(variable.toUpperCase().matches(Constants.VARIABLE_NAME_PATTERN)){
			return true;
		}
		
		return false;
		
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
