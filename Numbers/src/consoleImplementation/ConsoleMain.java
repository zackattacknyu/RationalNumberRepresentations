package consoleImplementation;

import java.util.Scanner;

import coreAccessor.ConsoleInstance;

public class ConsoleMain {

	private static final String[] Greetings = {
		"Welcome to the Number Convertor", "", 
		"This application converts rational numbers between fraction/decimal representation" ,
		"and between different base representations",
		"",
		"",
		"The application starts by converting from base 10 (decimal) to base 2 (binary)",
		"The starting input format is normal digit representation and",
		"the starting output format is normal digit representation"};
	
	/**
	 * main method for console execution
	 * @param args
	 */
	public static void main(String[] args){
		
		ConsoleInstance thisInstance = new ConsoleInstance();
		String input = "Start";
		Scanner sc = new Scanner(System.in);
		
		while(!isQuit(input)){
			System.out.print("> ");
			input = sc.nextLine();
			DisplayLineSet(thisInstance.ResolveConsoleInput(input));
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
	 * Displays blank lines
	 * @param numLines		number of lines that are blank to display
	 */
	public static void DisplayConsecutiveWhiteSpace(int numLines){
		for(int j = 0; j < numLines; j++){
			System.out.println();
		}
	}
	
	/**
	 * Displays an array of strings with each string being on a new line
	 * @param toDisplay		the array of strings to display
	 */
	public static void DisplayLineSet(String[] toDisplay){
		for(int j = 0; j < toDisplay.length; j++){
			System.out.println(toDisplay[j]);
		}
	}
}
