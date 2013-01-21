package ioMethods;

/**
 * This class is meant to hold regular expressions for use 
 * 		with splitting strings and deciding if strings 
 * 		match certain patterns
 * @author Zachary DeStefano
 *
 */
public class Constants {

	/*
	 * These represent the representations of various
	 * 		characters in regular expressions. They are most
	 * 		often used as the regex in the String.split
	 * 		method
	 */
	public static final String OPEN_PARENTHESIS_IN_REGEX = "\\x28";
	public static final String CLOSE_PARENTESIS_IN_REGEX = "\\x29";
	public static final String QUOTES_IN_REGEX = "\\x22";
	public static final String COMMA_IN_REGEX = ",";
	public static final String EQUALS_SIGN_IN_REGEX = "=";
	public static final String SEMICOLON_IN_REGEX = ";";
	public static final String COLON_IN_REGEX = ":";
	public static final String DECIMAL_POINT_IN_REGEX = ".";
	public static final String SPACE_IN_REGEX = " ";
	public static final String DECIMAL_SEPARATOR_CLASS_REGEX = "[._]";
	public static final String FRACTION_SEPARATOR_CLASS_REGEX = "[/ ]";
	
	/*
	 * These are the regular expressions used for pattern matching.They are meant to be used
	 * 		inside a string.matches call. 
	 */
	public static final String VARIABLE_NAME_PATTERN = "[A-Z]{1}[\\w]*";
	public static final String ONE_OR_MORE_NUMBERS_PATTERN = "[0-9]+";
	public static final String ONE_OR_MORE_NONQUOTES_PATTERN = "[^\\x22]+";
	public static final String INTEGER_EXPRESSION_PATTERN = "[0-9a-zA-Z]+";
	public static final String DECIMAL_EXPRESSION_PATTERN = "[0-9a-zA-Z]*[.]{1}[0-9a-zA-Z]*[_]{0,1}[0-9a-zA-Z]*";
	public static final String FRACTION_EXPRESSION_PATTERN = "[0-9a-zA-Z]*[ ]*[0-9a-zA-Z]+[/]{1}[0-9a-zA-Z]+";
	public static final String STRING_HAS_EXPRESSION_IN_QUOTES_PATTERN = 
			"[^\\x22]*[\\x22]{1}[^\\x22]+[\\x22]{1}[^\\x22]*";
	
	/*
	 * These are the formmated strings to use when String.format is called in this project
	 */
	public static final String CONCAT_TWO_STRINGS_FORMATTED_STRING = "%1$s%2$s";
	public static final String CONCAT_THREE_STRINGS_FORMATTED_STRING = "%1$s%2$s%3$s";
	public static final String CONCAT_FIVE_STRINGS_FORMATTED_STRING = "%1$s%2$s%3$s%4$s%5$s";
	
	/*
	 * Default values
	 */
	public static final String defaultInputBase = "10";
	public static final String defaultOutputBase = "16";
	public static final String defaultOutputFormat = "1";
	public static final String defaultVariableName = "ANS";
	public static final String defaultFunctionName = "CALC";
	
	/*
	 * Characters used in Strings
	 */
	public static final char DECIMAL_POINT = '.';
	public static final char UNDERSCORE = '_';
	public static final String ZERO_THEN_DECIMAL_POINT = "0.";
	public static final char ZERO = '0';
	public static final char SLASH = '/';
	public static final char SPACE = ' ';
	
	
}
