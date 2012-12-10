package ioMethods;

import java.math.BigInteger;

public enum NumberStringExpressionFormat {
	DIGIT(1), VECTOR(2), NADIC(3);
	
	private final int MenuOptionNumber;

	NumberStringExpressionFormat(int MenuOptionForFormat){
		this.MenuOptionNumber = MenuOptionForFormat;
	}
	
	public static NumberStringExpression getExpressionObject(
			NumberStringExpressionFormat theFormat, String theInput, BigInteger inputBase){
		
		NumberStringExpression ToReturn = null;
		
		switch(theFormat){
		
		case DIGIT:
			ToReturn = new DigitNumberStringExpression(theInput,inputBase);
			break;
		
		case VECTOR:
			//**UNCOMMENT WHEN CODE IS WRITTEN**
			//ToReturn = new VectorNumberStringExpression(theInput,inputBase);
			break;
		
		case NADIC:
			//**UNCOMMENT WHEN CODE IS WRITTEN**
			//ToReturn = new NadicNumberStringExpression(theInput,inputBase);
			break;
		}
		
		return ToReturn;
	}
}
