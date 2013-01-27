package ioMethods;


import java.math.BigInteger;

public class IntegerHelper {

	public static boolean isInteger(String input){
		if(input.matches(Constants.ONE_OR_MORE_NUMBERS_PATTERN)){
			return true;
		}
		else{
			return false;
		}
	}


	public static boolean isIntegerGreaterThan(String input, BigInteger lowerBound){
		return IntegerHelper.isIntegerBetween(input,lowerBound,null);
	}

	public static boolean isIntegerBetween(BigInteger input, BigInteger lowerBound, BigInteger upperBound){
	
		if(input.compareTo(lowerBound) < 0){
			return false;
		}
		
		if(upperBound == null){
			return true;
		}
		
		if(input.compareTo(upperBound) > 0){
			return false;
		}
		
		return true;
	}

	public static boolean isIntegerBetween(String input, BigInteger lowerBound, BigInteger upperBound){
		if(!isInteger(input)){
			return false;
		}
				
		if(lowerBound == null){
			return true;
		}
		
		return isIntegerBetween(new BigInteger(input),lowerBound,upperBound);
		
	}

	public static boolean isIntegerGreaterThan(BigInteger input, BigInteger lowerBound){
		return isIntegerBetween(input,lowerBound,null);
	}

}
