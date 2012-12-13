package inputParser;

import ioUtils.StringHelper;

public abstract class BaseParse {

	protected int minNumTokens;
	protected int maxNumTokens;
	
	protected String regexToIgnore;
	protected String regexToSplitBy;
	
	protected String[] Tokens;
	
	protected boolean valid = false;

	public boolean isValid() {
		return valid;
	}
	
	protected BaseParse(String input){
		int numTokens;
		
		minNumTokens = getMinNumTokens();
		maxNumTokens = getMaxNumTokens();
		
		regexToIgnore = getRegexToIgnore();
		regexToSplitBy = getRegexToSplitBy();

		SplitInputString(input);
		
		numTokens = Tokens.length;
		
		if( (numTokens >= minNumTokens) && (numTokens <= maxNumTokens) ){
			valid = true;
			AssignTokensToVariables();
		}
		else{
			valid = false;
		}
		
	}
	
	public abstract int getMinNumTokens();

	public abstract int getMaxNumTokens();

	public abstract String getRegexToIgnore();

	public abstract String getRegexToSplitBy();
	
	protected abstract void AssignTokensToVariables();
	
	protected void SplitInputString(String input){
		Tokens = StringHelper.takeOutCharsAndSplitString(input, regexToSplitBy, regexToIgnore);
		for(int index = 0; index < Tokens.length; index++){
			Tokens[index] = Tokens[index].trim();
		}
	}
}
