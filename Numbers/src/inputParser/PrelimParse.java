package inputParser;

public class PrelimParse {

	private String variableName;
	private String functionName;
	
	private FractionSpecification fractionInputted;
	private FractionSpecification outputBase;
	
	private String outputFormat;
	
	private boolean valid;
	
	public PrelimParse(String input){
		ParseOutInput(input);
	}
	
	public void ParseOutInput(String input){
		EqualsSignParse varAndFunction;
		FunctionParse functionAndInputs;
		FunctionInputParse functionInputs;
		
		//does the initial equals sign parse
		varAndFunction = new EqualsSignParse(input);
		if(!varAndFunction.isValid()){
			valid = false; return;
		}
		variableName = varAndFunction.getVariableName();
		
		//does the function parse;
		functionAndInputs = new FunctionParse(varAndFunction.getFunctionInputted());
		if(!functionAndInputs.isValid()){
			valid = false; return;
		}
		functionName = functionAndInputs.getFunctionName();
		
		//does the function input parse
		functionInputs = new FunctionInputParse(functionAndInputs.getFunctionInputsString());
		if(!functionInputs.isValid()){
			valid = false; return;
		}
		outputFormat = functionInputs.getOutputFormat();
		
		//does the fraction specification parses
		fractionInputted = new FractionSpecification(functionInputs.getFractionInputted());
		outputBase = new FractionSpecification(functionInputs.getOutputBase());
		if( !(fractionInputted.isValid() && outputBase.isValid()) ){
			//if either fraction spec is not valid
			valid = false; return;
		}
		
	}

	public String getVariableName() {
		return variableName;
	}

	public String getFunctionName() {
		return functionName;
	}

	public FractionSpecification getFractionInputted() {
		return fractionInputted;
	}

	public FractionSpecification getOutputBase() {
		return outputBase;
	}

	public String getOutputFormat() {
		return outputFormat;
	}

	public boolean isValid() {
		return valid;
	}
	
	
}
