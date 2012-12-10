package core;

import java.math.BigInteger;
import java.util.ArrayList;



public class DecimalRep extends RationalNumberRep{
	
	private int TerminatingNumDigits;
	private int RepeatingNumDigits;
	
	private int TermPartNumZeros;
	private int RepeatPartNumZeros;
	
	private BigInteger TerminatingPartNumber;
	private BigInteger RepeatingPartNumber;
	
	private ArrayList<BigInteger> terminatingPartDigits;
	private ArrayList<BigInteger> repeatingPartDigits;
	
	
	public int getTermPartNumZeros() {
		return TermPartNumZeros;
	}
	public int getRepeatPartNumZeros() {
		return RepeatPartNumZeros;
	}
	public ArrayList<BigInteger> getTerminatingPartDigits() {
		return terminatingPartDigits;
	}
	public ArrayList<BigInteger> getRepeatingPartDigits() {
		return repeatingPartDigits;
	}	

	public int getTerminatingNumDigits() {
		return TerminatingNumDigits;
	}
	public int getRepeatingNumDigits() {
		return RepeatingNumDigits;
	}
	public BigInteger getTerminatingPartNumber() {
		return TerminatingPartNumber;
	}
	public BigInteger getRepeatingPartNumber() {
		return RepeatingPartNumber;
	}
	
	public DecimalRep(BigInteger base, int terminatingNumDigits,
			int repeatingNumDigits, BigInteger terminatingPartNumber,
			BigInteger repeatingPartNumber) {
		this(base,BigInteger.ZERO,terminatingNumDigits,repeatingNumDigits,terminatingPartNumber,repeatingPartNumber);
	}
	
	public DecimalRep(BigInteger base, BigInteger intPart, int terminatingNumDigits,
			int repeatingNumDigits, BigInteger terminatingPartNumber,
			BigInteger repeatingPartNumber) {
		
		super(base);
		
		this.theBase = base;
		TerminatingNumDigits = terminatingNumDigits;
		RepeatingNumDigits = repeatingNumDigits;
		TerminatingPartNumber = terminatingPartNumber;
		RepeatingPartNumber = repeatingPartNumber;
		
		//get the digit arrays
		IntegerPartDigits = Fraction.ConvertIntegerToIntegerRep(intPart, base);
		terminatingPartDigits = Fraction.ConvertIntegerToIntegerRep(terminatingPartNumber, base);
		repeatingPartDigits = Fraction.ConvertIntegerToIntegerRep(repeatingPartNumber, base);
		
		setNumZerosFields();
	}
	
	public DecimalRep(BigInteger base, ArrayList<BigInteger> intPartDigits,
			ArrayList<BigInteger> termPartDigits, ArrayList<BigInteger> repeatPartDigits){
		
		super(intPartDigits,base);
		
		terminatingPartDigits = termPartDigits;
		repeatingPartDigits = repeatPartDigits;
		
		if(terminatingPartDigits == null){
			TerminatingNumDigits = 0;
			TerminatingPartNumber = BigInteger.ZERO;
		}
		else{
			TerminatingNumDigits = termPartDigits.size();
			TerminatingPartNumber = Fraction.ConvertIntegerRepToInteger(termPartDigits, base);
		}
		
		if(repeatingPartDigits == null){
			RepeatingNumDigits = 0;
			RepeatingPartNumber = BigInteger.ZERO;
		}
		else{
			RepeatingNumDigits = repeatPartDigits.size();
			RepeatingPartNumber = Fraction.ConvertIntegerRepToInteger(repeatPartDigits, base);
		}
		
		
	}
	
	private void setNumZerosFields(){
		//get the number of leading zeros necessary
		TermPartNumZeros = getNumZeros(terminatingPartDigits,TerminatingNumDigits);
		RepeatPartNumZeros = getNumZeros(repeatingPartDigits,RepeatingNumDigits);
	}
	
	private int getNumZeros(ArrayList<BigInteger> digitRep, int numDigitsRequired){
		int numDigitsInRep = digitRep.size();
		
		return (numDigitsRequired-numDigitsInRep);
	}
	
	
}
