package consoleImplementation;

import java.math.BigInteger;

import coreAccessor.ConvertToRepImpl;
import coreAccessor.DecimalRepStrings;
import coreAccessor.DigitRepresentation;
import coreAccessor.FractionStrings;
import coreObjects.DecimalRep;
import coreObjects.Fraction;

public class OutputPrompts {
	
	public static void OutputFraction(Fraction theNumber, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		
		FractionStrings theStrings = getFractionStrings(theNumber,format,OutputBase,theRep);
		
		System.out.println("Here is the mixed number in base " + OutputBase + ":");
		System.out.println();
		System.out.println();
		System.out.println("Integer Part:");
		System.out.println(theStrings.getIntegerRep());
		System.out.println();
		System.out.println("Numerator:");
		System.out.println(theStrings.getNumeratorRep());
		System.out.println();
		System.out.println("Denominator:");
		System.out.println(theStrings.getDenominatorRep());
		System.out.println();
	}
	
	public static void OutputDecimalRep(DecimalRep theNumber, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		
		DecimalRepStrings theStrings = getDecimalRepStrings(theNumber,format,OutputBase,theRep);
		
		System.out.println("Here is the decimal in base " + OutputBase + ":");
		System.out.println();
		System.out.println();
		System.out.println("Integer Part:");
		System.out.println(theStrings.getIntegerPartRep());
		System.out.println();
		System.out.println("Initial Part:");
		System.out.println(theStrings.getTermPartRep());
		System.out.println();
		System.out.println("Repeating Part:");
		System.out.println(theStrings.getRepeatingPartRep());
		System.out.println();
	}
	
	public static void WriteLine(){
		System.out.println("____________________________________________");
	}
	
	public static void WriteInteger(BigInteger theNumber, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		if(format == 1){
			System.out.println(theRep.ConvertToDigitRep(theNumber, OutputBase.intValue()));
		}
		else if(format == 2){
			System.out.println(ConvertToRepImpl.ConvertToNumberRep(theNumber, OutputBase));
		}
		else{
			System.out.println(ConvertToRepImpl.ConvertToNadicRep(theNumber, OutputBase));
		}
	}
	
	public static FractionStrings getFractionStrings(Fraction theNum, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		if(format == 1){
			return theRep.ConvertFractionToDigitRep(theNum, OutputBase.intValue());
		}
		else if(format == 2){
			return ConvertToRepImpl.ConvertFractionToNumberRep(theNum, OutputBase);
		}
		else{
			return ConvertToRepImpl.ConvertFractionToNadicRep(theNum, OutputBase);
		}
	}
	
	public static DecimalRepStrings getDecimalRepStrings(DecimalRep theNum, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		if(format == 1){
			return theRep.ConvertDecimalRepToDigitRep(theNum, OutputBase.intValue());
		}
		else if(format == 2){
			return ConvertToRepImpl.ConvertDecimalToNumberRep(theNum, OutputBase);
		}
		else{
			return ConvertToRepImpl.ConvertDecimalToNadicRep(theNum, OutputBase);
		}
	}
	
	/**
	 * This outputs a bigInteger depending on the format specified
	 * @param theNumber		number to output
	 * @param format		format of result
	 * @param OutputBase	output base
	 * @param theRep		base representation
	 */
	public static void OutputInteger(BigInteger theNumber, int format, BigInteger OutputBase, 
			DigitRepresentation theRep){
		System.out.println("Number in base " + OutputBase + ":");
		WriteInteger(theNumber, format, OutputBase, theRep);
	}
	
	public static void DisplayConsecutiveWhiteSpace(int numLines){
		for(int j = 0; j < numLines; j++){
			System.out.println();
		}
	}
	
	public static void DisplayLineSet(String[] toDisplay){
		for(int j = 0; j < toDisplay.length; j++){
			System.out.println(toDisplay[j]);
		}
	}
	
}
