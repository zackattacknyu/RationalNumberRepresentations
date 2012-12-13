package consoleImplementation;

import inputParser.PrelimParse;
import ioMethods.ProgramInstance;

import java.util.ArrayList;
import java.util.Scanner;


/**
 * This class is for the Console User Interface. It calls into the core accessor which returns
 * 		an array list of strings as the result. The program then prints those
 * 		strings to the console. 
 * 
 * @author Zach DeStefano
 *
 */
public class ConsoleMain {
	
	/**
	 * main method for console execution
	 * @param args
	 */
	public static void main(String[] args){
		
		ProgramInstance thisInstance = new ProgramInstance();
		String input = "Start";
		Scanner sc = new Scanner(System.in);
		
		while(!isQuit(input)){
			System.out.print("> ");
			input = sc.nextLine();
			
			//TEST CODE
			PrelimParse theParse = new PrelimParse(input);
			System.out.println("varName = " + theParse.getVariableName());
			System.out.println("funcName = " + theParse.getFunctionName());
			System.out.println("fractionInputted = " + theParse.getFractionInputted().getNumberInputted());
			System.out.println("fractionInputtedInBase = " + theParse.getFractionInputted().getInputBase());
			System.out.println("outputBaseSpecified = " + theParse.getOutputBase().getNumberInputted());
			System.out.println("ouptutBaseSpecifiedInBase = " + theParse.getOutputBase().getInputBase());
			System.out.println("outputFormat = " + theParse.getOutputFormat());
		}
	}
	
	/**
	 * says if the string is "quit" or "exit". case-insensitive
	 * @param input		the string the user input
	 * @return		true if it is "quit" or "exit". false if not
	 */
	public static boolean isQuit(String input){
		if(input.toUpperCase().equals("EXIT")){
			return true;
		}
		else if(input.toUpperCase().equals("QUIT")){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Displays an array of strings with each string being on a new line
	 * @param toDisplay		the array of strings to display
	 */
	public static void DisplayLineSet(ArrayList<String> toDisplay){
		for(String line:toDisplay){
			System.out.println(line);
		}
	}
}
