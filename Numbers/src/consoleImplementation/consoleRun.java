package consoleImplementation;

import java.math.*;

import coreAccessor.DigitRepresentation;
import coreAccessor.InputHelper;

public class consoleRun {
	
	/*
	 * InputFormat and OutputFormat can only be set to 1, 2, or 3
	 * 
	 * 1: DigitRepresentation, so 9 in base 2 is 101
	 * 2: SetRepresentation, so 32 in base 16 will be {2,0}
	 * 3: NadicRepresentation, so 32 in base 5 would be 1*5^2 + 1*5 + 2
	 * 
	 * For the n-adic representation, the equation with only numbers will be output
	 * 		and then the equation with symbols and then numbers will be displayed, so in 
	 * 		the above example, 32 would be outputted as 1*5^2 + 1*5 + 2, 
	 * 		and then the output would be 
	 * 		a_2*5^2 + a_1*5 + a_0 
	 * 		a_2=1
	 * 		a_1=1
	 * 		a_0=2
	 * The input for that format will be taken by going in forward or reverse order of subscripts
	 * 		depending on the user preference and taking the integers. 
	 */
	private int InputFormat;
	private int OutputFormat;
	
	
	/*
	 * InputBase and OutputBase must be greater than or equal to 2
	 * Additionally, if the current Input/Output Format is 1, then it is limited by the current DigitRepresentation
	 */
	private BigInteger InputBase;
	private BigInteger OutputBase;
	
	private DigitRepresentation RepImpl;

	public consoleRun() {
		super();
		InputFormat = 1;
		OutputFormat = 1;
		InputBase = new BigInteger("10");
		OutputBase = new BigInteger("2");
		RepImpl = new DigitRepresentation();
	}
	
	public boolean isFormatValid(int input){
		if( InputHelper.isIntegerBetween(BigInteger.valueOf(input), 
				BigInteger.valueOf(1), BigInteger.valueOf(3)  )  
				) 
			return true;
		else return false;
	}
	public boolean isBaseValid(BigInteger input, int format){
		//restricted format, so must be less than what the digit representation can do
		if(format == 1){
			if(InputHelper.isIntegerBetween(input, BigInteger.valueOf(2), 
					BigInteger.valueOf(RepImpl.getMaxBase()))){
				return true;
			}
			else{
				return false;
			}
		}
		else{ //unrestricted format
			if(InputHelper.isIntegerGreaterThan(input, BigInteger.valueOf(2))){
				return true;
			}
			else{
				return false;
			}
		}
	}

	public int getInputFormat() {
		return InputFormat;
	}

	public void setInputFormat(int inputFormat) {
		if(isFormatValid(inputFormat)){
			InputFormat = inputFormat;
		}
	}

	public int getOutputFormat() {
		return OutputFormat;
	}

	public void setOutputFormat(int outputFormat) {
		if(isFormatValid(outputFormat)){
			OutputFormat = outputFormat;
		}
	}

	public BigInteger getInputBase() {
		return InputBase;
	}

	public void setInputBase(BigInteger inputBase) {
		if(isBaseValid(inputBase,InputFormat)){
			InputBase = inputBase;
		}
		
	}

	public BigInteger getOutputBase() {
		return OutputBase;
	}

	public void setOutputBase(BigInteger outputBase) {
		if(isBaseValid(outputBase,OutputFormat)){
			OutputBase = outputBase;
		}
	}

	public DigitRepresentation getRepImpl() {
		return RepImpl;
	}
	
	
}
