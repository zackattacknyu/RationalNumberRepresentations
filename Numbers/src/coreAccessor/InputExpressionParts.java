package coreAccessor;

import java.math.BigInteger;
import java.util.ArrayList;

import coreObjects.Fraction;

public class InputExpressionParts {
	
	private static final String[] ValidMessage = {"No Error, Valid String"};
	
	private static final String[] EqualsSignErrorMessage =
		{ "Invalid Syntax", "Only One Equals Sign Allowed"};
	
	private static final String[] EmptyStringError = 
		{ "Invalid Syntax, nothing was entered" , "Type exit to quit" };
	
	private static final String[] VariableNameInvalidError = 
		{ "Invalid Syntax for Variable", 
		"Variable names can only consist of letters and numbers",
		"and the first character must be a letter"
		};
	
	private static final String[] FunctionInvalidError = 
		{ "Invalid Syntax For Function" ,
		"You can only specify one open parenthesis"
		};
	
	private static final String[] FunctionNameInvalidError = 
		{ "Invalid Syntax for Variable", 
		"Variable names can only consist of letters and numbers",
		"and the first character must be a letter"
		};
	
	private static final String[] inputWithQuotesInvalidError = 
		{
		"Invalid Syntax for Inputs", "There must be between 1 and 4 parameters"
		};
	
	private static final String[] inputWithoutQuotesInvalidError = {
		"Invalid Syntax for Input", "There must be between 1 and 3 parameters"
	};
	
	private static final String[] NoClosingQuoteMessage = {
		"Invalid Syntax", "You must have a closing quote"
	};
	
	private static final String[] InvalidInputBaseErrorMessage = {"Error: Input Base invalid",
		"You can only specify an integer in base 10 format",
		"or a variable name for a fraction specified in this instance"
	};
	
	private static final String[] InvalidOutputBaseErrorMessage = {"Error: Output Base invalid",
		"You can only specify an integer in base 10 format",
		"or a variable name for a fraction specified in this instance"
	};
	
	/*
	 *DEVELOP THIS CODE MORE LATER 
	 */
	private static final String[] InvalidFormatErrorMessage = {"Invalid Format"};
	
	private String variableName;
	private String functionName;
	private String[] errorMessage;
	private String numberInputted;
	private String inputBase;
	private String outputBase;
	private String outputFormat;
	private boolean isValidExpression;
	
	public InputExpressionParts(String input){
		variableName = "PREVIOUS";
		functionName = "<NONE>";
		inputBase = "10";
		outputBase = "2";
		outputFormat = "1";
		isValidExpression = true;
		
		errorMessage = splitStringAndPreValidate(input);
		
		if(isValidExpression){
			errorMessage = validateNames();
			
			if(isValidExpression){
				errorMessage = validateBases();
				
				if(isValidExpression){

					errorMessage = validateFormat();
				}
			}
		}
		
		//TEST CODE
		DigitNumberStringExpression expr = new DigitNumberStringExpression(numberInputted, new BigInteger(inputBase));
		Fraction ourFrac = expr.getTheNumber();
		
		System.out.println();
		System.out.println("Int Part: " + ourFrac.getIntegerPart());
		System.out.println("Numerator: " + ourFrac.getNumerator());
		System.out.println("Denominator: " + ourFrac.getDenominator());
	}
	
	public String getVariableName() {
		return variableName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public String[] getErrorMessage() {
		return errorMessage;
	}
	
	private String[] validateFormat(){
		if(validFormat(outputFormat)){
			return ValidMessage;
		}
		else{
			isValidExpression = false;
			return InvalidFormatErrorMessage;
		}
	}
	
	private String[] validateNames(){
		/*
		 * This validates the variable entered. 
		 */
		if(!validVariableFunctionName(variableName)){
			isValidExpression = false;
			return VariableNameInvalidError;
		}
		
		if(!validVariableFunctionName(functionName)){
			isValidExpression = false;
			return FunctionNameInvalidError;
		}
		
		return ValidMessage;
	}
	
	private String[] validateBases(){
		if(!validBase(inputBase)){
			isValidExpression = false;
			return InvalidInputBaseErrorMessage;
		}
		if(!validBase(outputBase)){
			isValidExpression = false;
			return InvalidOutputBaseErrorMessage;
		}
		
		return ValidMessage;
	}
	
	private boolean validBase(String base){
		if(validVariableFunctionName(base)){
			return true;
		}
		else if(InputHelper.isInteger(base)){
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

	private String[] splitStringAndPreValidate(String input){
		String function = "";
		String[] expressionParts;
		String[] functionParts;
		StringBuffer numberInputtedBuffer;
		StringBuffer baseAndFormatSpecsBuffer;
		ArrayList<String> inputParts;
		char[] OpenParenthesis = {'('};
		char[] CloseParenthesis = {')'};
		char[] comma = {','};
		String functionInputs = "";
		Character quotes;
		int quoteNumber = 0;
		int currentIndex = 0;
		char currentCharacter;
		
		
		//makes sure string is non-null
		if(input.isEmpty()){
			isValidExpression = false;
			return EmptyStringError;
		}
		
		/*
		 * This function takes in the input and splits it by the equals sign
		 * 		into the variable name and the function
		 */
		expressionParts = input.split("=");
		if(expressionParts.length > 2){
			isValidExpression = false;
			return EqualsSignErrorMessage;
		}
		else if(expressionParts.length == 2){
			variableName = expressionParts[0]; function = expressionParts[1];
		}
		else if(expressionParts.length == 1){
			function = expressionParts[0];
		}
		else{
			isValidExpression = false;
			return EmptyStringError;
		}
		
		/*
		 * This splits up the function part into function name and inputs
		 * It first makes the function into a splittable form by leaving out the close parenthesis
		 * 		and converting the open parenthesis into a colon
		 */
		functionParts = InputHelper.ParseOutString(function, CloseParenthesis, OpenParenthesis);
		if(functionParts.length != 2){
			isValidExpression = false;
			return FunctionInvalidError;
		}
		functionName = functionParts[0];
		functionInputs = functionParts[1];
		
		
		/*
		 * This splits up the function Inputs into their appropriate strings
		 */
		quotes = (char)(34);
		if(functionInputs.contains(quotes.toString())){
			
			//puts everything inside the quotes into a single string
			numberInputtedBuffer = new StringBuffer();
			for(currentIndex = 0; currentIndex < functionInputs.length(); currentIndex++){
				currentCharacter = functionInputs.charAt(currentIndex);
				if(currentCharacter == quotes){
					quoteNumber++;
					if(quoteNumber > 1){
						break;
					}
				}
				else{
					numberInputtedBuffer.append(currentCharacter);
				}
			}
			if(quoteNumber < 2){
				isValidExpression = false;
				return NoClosingQuoteMessage;
			}
			numberInputted = numberInputtedBuffer.toString();
			currentIndex++;
			
			//puts the rest of the inputs into a string
			baseAndFormatSpecsBuffer = new StringBuffer();
			for(int index = currentIndex; index < functionInputs.length(); index++){
				baseAndFormatSpecsBuffer.append(functionInputs.charAt(index));
			}
			
			//assigns the input parts appropriately
			inputParts = InputHelper.MakeStringArray(baseAndFormatSpecsBuffer.toString(),comma);
			
			if(inputParts.size() < 1){ //if no more parameters were specified
				return ValidMessage;
			}
			if( inputParts.size() > 3 ){
				isValidExpression = false;
				return inputWithQuotesInvalidError;
			}
			inputBase = inputParts.get(0);
			if(inputParts.size() > 1){
				outputBase = inputParts.get(1);
			}
			if(inputParts.size() > 2){
				outputFormat = inputParts.get(2);
			}
						
		}
		else{
			
			inputParts = InputHelper.MakeStringArray(functionInputs,comma);
			
			if((inputParts.size() < 1) || (inputParts.size() > 3)){
				isValidExpression = false;
				return inputWithoutQuotesInvalidError;
			}
			
			numberInputted = inputParts.get(0);
			
			if(inputParts.size() > 1){
				outputBase = inputParts.get(1);
			}
			if(inputParts.size() > 2){
				outputFormat = inputParts.get(2);
			}
			
			
		}
		
		return ValidMessage;

	}
	
	


	/**
	 * This validates variable and function names. 
	 * They must start with a letter and only be letters and numbers
	 * This is meant to follow standard programming practice
	 * @param variable		the variable or function name
	 * @return		whether it follows standard syntax
	 */
	public static boolean validVariableFunctionName(String variable){
		
		String firstChar;
		char[] variableCharArray = variable.toCharArray();
		String currentCharacter;
		
		//if variable is null, make it valid by default
		if(variable.length() < 1){
			return true;
		}
		
		//make sure first character is a letter
		firstChar = ((Character)variable.charAt(0)).toString().toUpperCase();
		if(!firstChar.matches("[A-Z]")){
			return false;
		}
		
		//make sure whole string is letters and numbers only
		for(char currentChar : variableCharArray){
			currentCharacter = ((Character)currentChar).toString();
			if(!currentCharacter.matches("\\w")){
				return false;
			}
		}
		
		return true;
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
