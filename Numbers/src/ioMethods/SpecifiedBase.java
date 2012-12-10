package ioMethods;

import ioUtils.IntegerHelper;
import ioUtils.StringHelper;

import java.math.BigInteger;


public class SpecifiedBase {

	private String baseAsString;
	private BigInteger baseAsInteger;
	private boolean baseValid;
	private boolean baseVariable;
	
	public boolean isBaseValid() {
		return baseValid;
	}
	public BigInteger getBaseAsInteger() {
		return baseAsInteger;
	}
	public void setBaseAsInteger(BigInteger baseAsInteger) {
		this.baseAsInteger = baseAsInteger;
	}
	public boolean isBaseVariable() {
		return baseVariable;
	}
	
	
	public SpecifiedBase(String baseInput){
		baseAsString = baseInput;
		baseValid = validateBase();
	}
		
	private boolean validateBase(){
		if(StringHelper.validVariableFunctionName(baseAsString)){
			baseVariable = true;
			return true;
		}
		else if(IntegerHelper.isInteger(baseAsString)){
			baseVariable = false;
			baseAsInteger = new BigInteger(baseAsString);
			return true;
		}
		else{
			return false;
		}
	}
	
}
