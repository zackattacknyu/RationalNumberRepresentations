package inputParser;

import ioUtils.Constants;

public class FunctionInputParse extends BaseParse{

	
	private String fractionInputted;
	private String outputBase;
	private String outputFormat;
	
	public FunctionInputParse(String functionInputs){

		super(functionInputs);
		
	}

	public String getFractionInputted() {
		return fractionInputted;
	}

	public String getOutputBase() {
		return outputBase;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public boolean isValid() {
		return valid;
	}

	@Override
	public int getMinNumTokens() {
		return 1;
	}

	@Override
	public int getMaxNumTokens() {
		return 3;
	}

	@Override
	public String getRegexToIgnore() {
		return "";
	}

	@Override
	public String getRegexToSplitBy() {
		return Constants.SEMICOLON_IN_REGEX;
	}

	@Override
	protected void AssignTokensToVariables() {
		//only <Fraction To Translate> specified. Default Output Base and Output Format used. 
		if(Tokens.length == 1){
			fractionInputted = Tokens[0];
			outputBase = Constants.defaultOutputBase;
			outputFormat = Constants.defaultOutputFormat;
		}
		
		//<Fraction To Translate>;<Output Base> specified. Default format used. 
		if(Tokens.length == 2){
			fractionInputted = Tokens[0];
			outputBase = Tokens[1];
			outputFormat = Constants.defaultOutputFormat;
		}
		
		//all parts specified
		if(Tokens.length == 3){
			fractionInputted = Tokens[0];
			outputBase = Tokens[1];
			outputFormat = Tokens[2];
		}
		
	}
}
