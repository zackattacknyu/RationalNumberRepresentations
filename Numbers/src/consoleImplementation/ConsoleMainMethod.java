package consoleImplementation;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Scanner;


import coreAccessor.FractionDecimalConvertor;
import coreObjects.DecimalRep;
import coreObjects.Fraction;

public class ConsoleMainMethod {

	private static final String[] Greetings = {
		"Welcome to the Number Convertor", "", 
		"This application converts rational numbers between fraction/decimal representation" ,
		"and between different base representations",
		"",
		"",
		"The application starts by converting from base 10 (decimal) to base 2 (binary)",
		"The starting input format is normal digit representation and",
		"the starting output format is normal digit representation"};
	
	private static final String[] MainMenuOptions = { 
		"",
		"1: Convert from Fraction to Decimal Form",
		"2: Convert from Decimal to Fraction Form",
		"3: Convert Fraction between Base Representations",
		"4: Convert Decimal between Base Representations",
		"5: Convert Integer between Base Representations",
		"6: Change Input Base",
		"7: Change Output Base",
		"8: Change Input Format",
		"9: Change Output Format",
		"10: Display Current Settings",
		"11: Exit",
	};
	
	private static final String[] ExitMessage = {
		"Thank you for using the Number Convertor",
		"Have a nice day"
	};
	private static final int NumberOfMainOptions = 11;
	
	private static final int NumLeadingSpacesIntro = 2;
	private static final int NumTrailingSpacesIntro = 4;
	
	private static consoleRun theRun = new consoleRun();
	/**
	 * @param args
	 */
	/*
	public static void main(String[] args) {
		Test();
		
		//Intro();
		//MainMenu();
			

	}
	*/
	public static void Test(){
		
		ArrayList<BigInteger> digits = Fraction.ConvertIntegerToIntegerRep(new BigInteger("203"), new BigInteger("10"));
		
		System.out.println(Fraction.ConvertIntegerRepToInteger(digits, new BigInteger("10")));
	} 
	
	private static void MainMenu(){
		BigInteger InputResult = new BigInteger("0");
		int result;
		BigInteger theBase;
		BigInteger currentFormat;
		
		
		while(InputResult != null){
			OutputPrompts.DisplayLineSet(MainMenuOptions);
			InputResult = InputPrompts.getIntegerChoice("Enter a choice:", new BigInteger("1"), BigInteger.valueOf(NumberOfMainOptions));
			
			if(InputResult == null){
				break;
			}
			result = InputResult.intValue();
			System.out.println();
			switch(result){
			
			case 1: FractionToDecimalChangeMenuOption(); break;
			
			case 3: FractionChangeMenuOption(); break;
			
			case 5: IntegerChangeMenuOption(); break;
			case 6: InputBaseChangeMenuOption();  break;
			case 7: OutputBaseChangeMenuOption(); break;
			case 8: InputFormatMenuOption(); break;
			case 9: OutputFormatMenuOption(); break;
			case 10: DisplayCurrentSettings(); break;
					
			}
		}
		
		ExitMessage();
	}
	
	public static void ExitMessage(){
		OutputPrompts.DisplayConsecutiveWhiteSpace(2);
		OutputPrompts.DisplayLineSet(ExitMessage);
	}
	
	public static void FractionToDecimalChangeMenuOption(){
		OutputPrompts.WriteLine();
		Fraction theNum = InputPrompts.getFractionInput(theRun.getInputFormat(), 
				theRun.getRepImpl(), theRun.getInputBase(), ",");
		DecimalRep outputRep = FractionDecimalConvertor.FracToDecimal(theNum, theRun.getOutputBase());
		if(theNum != null){
			OutputPrompts.WriteLine();
			OutputPrompts.DisplayConsecutiveWhiteSpace(2);
			OutputPrompts.OutputDecimalRep(outputRep,theRun.getOutputFormat(),theRun.getOutputBase(),theRun.getRepImpl());
			OutputPrompts.WriteLine();
		}
	}
	
	public static void FractionChangeMenuOption(){
		OutputPrompts.WriteLine();
		Fraction theNum = InputPrompts.getFractionInput(theRun.getInputFormat(), 
				theRun.getRepImpl(), theRun.getInputBase(), ",");
		if(theNum != null){
			OutputPrompts.WriteLine();
			OutputPrompts.DisplayConsecutiveWhiteSpace(2);
			OutputPrompts.OutputFraction(theNum, theRun.getOutputFormat(), 
					theRun.getOutputBase(), theRun.getRepImpl());
			OutputPrompts.WriteLine();
		}
		
	}
	
	public static void IntegerChangeMenuOption(){
		OutputPrompts.WriteLine();
		BigInteger theInt = InputPrompts.getOnlyInteger(theRun.getInputFormat(), 
				theRun.getRepImpl(), theRun.getInputBase(), ",");
		if(theInt != null){
			OutputPrompts.WriteLine();
			System.out.println();
			OutputPrompts.OutputInteger(theInt, theRun.getOutputFormat(), 
					theRun.getOutputBase(), theRun.getRepImpl());
			OutputPrompts.WriteLine();
		}
	}
	
	public static void InputBaseChangeMenuOption(){
		OutputPrompts.WriteLine();
		BigInteger theBase = InputPrompts.getInputBase(theRun.getInputFormat(),theRun.getRepImpl().getMaxBase());
		if(theBase != null) {
			theRun.setInputBase(theBase);
			OutputPrompts.WriteLine();
		}
	}
	
	public static void OutputBaseChangeMenuOption(){
		OutputPrompts.WriteLine();
		BigInteger theBase = InputPrompts.getOutputBase(theRun.getOutputFormat(),theRun.getRepImpl().getMaxBase());
		if(theBase != null) {
			theRun.setOutputBase(theBase);
			OutputPrompts.WriteLine();
		}
	}
	
	public static void InputFormatMenuOption(){
		OutputPrompts.WriteLine();
		BigInteger currentFormat = InputPrompts.getFormat();
		if(currentFormat != null) {
			OutputPrompts.WriteLine();
			theRun.setInputFormat(currentFormat.intValue());
		}
	}
	
	public static void OutputFormatMenuOption(){
		OutputPrompts.WriteLine();
		BigInteger currentFormat = InputPrompts.getFormat();
		if(currentFormat != null) {
			OutputPrompts.WriteLine();
			theRun.setOutputFormat(currentFormat.intValue());
		}
	}
	
	private static void DisplayCurrentSettings(){
		OutputPrompts.WriteLine();
		System.out.println();
		System.out.println("Input Format: " + theRun.getInputFormat());
		System.out.println("Input Base: " + theRun.getInputBase());
		System.out.println();
		System.out.println("Output Format: " + theRun.getOutputFormat());
		System.out.println("Output Base: " + theRun.getOutputBase());
		OutputPrompts.WriteLine();
	}
	
	private static void Intro(){
		OutputPrompts.WriteLine();
		OutputPrompts.DisplayConsecutiveWhiteSpace(NumLeadingSpacesIntro);
		OutputPrompts.DisplayLineSet(Greetings);
		OutputPrompts.DisplayConsecutiveWhiteSpace(NumTrailingSpacesIntro);
		OutputPrompts.WriteLine();
	}

}
