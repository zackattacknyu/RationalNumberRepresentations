package inputParser;

import ioUtils.Constants;

public class FractionSpecification extends BaseParse{

	public enum TypeOfSpecification{
		VARIABLE, EXPRESSION
	}
	
	private String inputBase;
	private String numberInputted;
	private TypeOfSpecification specType;
	
	public String getInputBase() {
		return inputBase;
	}

	public String getNumberInputted() {
		return numberInputted;
	}

	public boolean isValid() {
		return valid;
	}

	
	
	public FractionSpecification(String fractionSpecification){
		
		super(fractionSpecification);
	}

	public TypeOfSpecification getSpecType() {
		return specType;
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
		return Constants.COLON_IN_REGEX;
	}

	@Override
	protected void AssignTokensToVariables() {
		//if <Variable Name> was specified
		if(Tokens.length == 1){
			numberInputted = Tokens[0];
			inputBase = null;
			specType = TypeOfSpecification.VARIABLE;
			valid = true;
		}
		
		//if <Expression>:<Input Base> is syntax
		if(Tokens.length == 2){
			numberInputted = Tokens[0];
			specType = TypeOfSpecification.EXPRESSION;
			inputBase = Tokens[1];
			valid=true;
		}
		
	}
}
