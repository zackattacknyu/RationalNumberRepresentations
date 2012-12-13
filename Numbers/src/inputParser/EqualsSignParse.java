package inputParser;

import ioUtils.Constants;

public class EqualsSignParse extends BaseParse{

	private String variableName;
	private String functionInputted;
	
	public String getVariableName() {
		return variableName;
	}

	public String getFunctionInputted() {
		return functionInputted;
	}


	public boolean isValid() {
		return valid;
	}

	public EqualsSignParse(String input){
		
		super(input);
		
	}

	@Override
	protected void AssignTokensToVariables() {
		
		if(Tokens.length == 2){
			variableName = Tokens[0]; 
			functionInputted = Tokens[1];
		}
		else if(Tokens.length == 1){
			functionInputted = Tokens[0];
			variableName = Constants.defaultVariableName;
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
		return "";
	}

	@Override
	public String getRegexToSplitBy() {
		return Constants.EQUALS_SIGN_IN_REGEX;
	}

}
