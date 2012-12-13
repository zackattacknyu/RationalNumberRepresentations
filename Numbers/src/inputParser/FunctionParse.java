package inputParser;

import ioUtils.Constants;

public class FunctionParse extends BaseParse{
	
	private String functionName;
	private String functionInputsString;
	
	public FunctionParse(String functionInputted){
		
		super(functionInputted);
		
	}

	public String getFunctionName() {
		return functionName;
	}

	public String getFunctionInputsString() {
		return functionInputsString;
	}

	@Override
	protected void AssignTokensToVariables() {
		if(Tokens.length == 2){
			functionName = Tokens[0];
			functionInputsString = Tokens[1];
		}
		else if(Tokens.length == 1){
			functionName = Constants.defaultFunctionName;
			functionInputsString = Tokens[0];
		}
		
	}


	@Override
	public int getMinNumTokens() {
		return 1;
	}

	@Override
	public int getMaxNumTokens() {
		return 2;
	}

	@Override
	public String getRegexToIgnore() {
		return Constants.CLOSE_PARENTESIS_IN_REGEX;
	}

	@Override
	public String getRegexToSplitBy() {
		return Constants.OPEN_PARENTHESIS_IN_REGEX;
	}
}
