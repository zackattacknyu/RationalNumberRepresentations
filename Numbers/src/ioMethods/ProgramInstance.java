package ioMethods;

import java.math.BigInteger;
import java.util.HashMap;

import core.Fraction;

public class ProgramInstance {

	private HashMap<String,Fraction> savedVariables;
	
	public ProgramInstance(){
		savedVariables = new HashMap<String,Fraction>(20);
	}
	
	public String getLogResult(String number, BigInteger inputBase, String variable, BigInteger outputBase){
		DigitNumberStringExpression expr = new DigitNumberStringExpression(number,inputBase);
		
		if(variable.equals("<NONE>")){
			return expr.OutputFraction(outputBase) + "\n" + expr.OutputDecimal(outputBase);
		}
		else{
			return "Variable used"; //**EXPAND UPON** 
		}
	}
	

}
