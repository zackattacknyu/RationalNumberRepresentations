package generalMethods;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.TreeSet;

public class NumberTheory {
	
	public static BigInteger OrderModN(BigInteger Element, BigInteger ModNum){
		
		BigInteger GroupSize = ModNum.subtract(BigInteger.ONE);
		TreeSet<BigInteger> PossibleOrders = GetAllFactors(GroupSize);
		Iterator<BigInteger> theFactors = PossibleOrders.iterator();
		BigInteger currentPossOrder;
		BigInteger currentResult;
		
		while(theFactors.hasNext()){
			currentPossOrder = theFactors.next();
			currentResult = Element.modPow(currentPossOrder, ModNum);
			if(currentResult.compareTo(BigInteger.ONE) == 0){
				return currentPossOrder;
			}
		}

		
		return GroupSize;
		
	}
	
	public static BigInteger OrderModN2(BigInteger Element, BigInteger ModNum){
		
		BigInteger theOrder = BigInteger.ONE;
		BigInteger currentFactor = Element;
		
		while(currentFactor.compareTo(BigInteger.ONE) != 0){
			currentFactor = currentFactor.multiply(Element);
			currentFactor = currentFactor.divideAndRemainder(ModNum)[1];
			theOrder = theOrder.add(BigInteger.ONE);
		}
		
		return theOrder;
		
	}
	
	/*
	 * This gets all the exponent vectors in order to compute all the factors of a given number
	 */
	public static ArrayList<BigInteger[]> GetAllExpVectors(BigInteger[] ExpVector){
		
		//compute the number of exp vectors
		BigInteger NumFactors = BigInteger.ONE;
		for(int index = 0; index < ExpVector.length; index++){
			NumFactors = NumFactors.multiply(ExpVector[index]);
		}
		
		//initialize the array list
		ArrayList<BigInteger[]> ExpVectors = new ArrayList<BigInteger[]>(NumFactors.intValue());
		
		//initialize the other variables
		BigInteger[] currentVector = new BigInteger[ExpVector.length];
		BigInteger[] startingVector = new BigInteger[ExpVector.length];
		boolean moreFactorsExist = true;
		BigInteger proposedExponent;
		
		//initialize the first vector
		for(int index = 0; index < ExpVector.length; index++){
			currentVector[index] = BigInteger.ZERO;
			startingVector[index] = BigInteger.ZERO;
		}
		ExpVectors.add(startingVector);
		
		while(moreFactorsExist){
			
			//the for loop has to catch something or else the while loop will quite
			moreFactorsExist = false;
			
			for(int masterIndex = ExpVector.length - 1; masterIndex >= 0; masterIndex--){
				
				proposedExponent = currentVector[masterIndex].add(BigInteger.ONE); 
				if(proposedExponent.compareTo(ExpVector[masterIndex]) <= 0){
					currentVector[masterIndex] = proposedExponent;
					
					for(int innerIndex = masterIndex+1; innerIndex < ExpVector.length; innerIndex++){
						currentVector[innerIndex] = BigInteger.ZERO;
					}
					
					BigInteger[] VectorToAdd = new BigInteger[currentVector.length];
					for(int Index2 = 0; Index2 < currentVector.length; Index2++){
						VectorToAdd[Index2] = currentVector[Index2];
					}
					ExpVectors.add(VectorToAdd);
					
					moreFactorsExist = true;
					break;
				}
				
			}
			
		}
		
		return ExpVectors;
	}
	
	public static TreeSet<BigInteger> GetAllFactors(BigInteger theNumber){
		
		HashMap<BigInteger,BigInteger> PrimeFactorization = GetPrimeFactorization(theNumber);
		
		BigInteger[] PrimeFactors = new BigInteger[PrimeFactorization.size()];
		BigInteger[] ExponentVector = new BigInteger[PrimeFactorization.size()];
		BigInteger currentPrime;
		int index = 0;
		Iterator<BigInteger> theFactors = PrimeFactorization.keySet().iterator();
		while(theFactors.hasNext()){
			currentPrime = theFactors.next();
			PrimeFactors[index] = currentPrime;
			ExponentVector[index] = PrimeFactorization.get(currentPrime);
			index++;
		}
		
		
		ArrayList<BigInteger[]> AllExpVectors = GetAllExpVectors(ExponentVector);
		TreeSet<BigInteger> Factors = new TreeSet<BigInteger>();
		BigInteger currentFactor;
		BigInteger currentPrimePower;
		BigInteger[] currentVector;
		int innerIndex = 0;
		for(int currentIndex = 0; currentIndex < AllExpVectors.size(); currentIndex++){
			currentVector = AllExpVectors.get(currentIndex);
			currentFactor = BigInteger.ONE;
			for(innerIndex = 0; innerIndex < PrimeFactors.length; innerIndex++){
				currentPrimePower = PrimeFactors[innerIndex].pow(currentVector[innerIndex].intValue());
				currentFactor = currentFactor.multiply(currentPrimePower);
			}
			Factors.add(currentFactor);
		}
		
		return Factors;
	}
	
	public static HashMap<BigInteger,BigInteger> GetPrimeFactorization(BigInteger n){
		
		HashMap<BigInteger,BigInteger> PrimeFactorization = new HashMap<BigInteger,BigInteger>(20);
		BigInteger currentDividend = n;
		BigInteger[] result = new BigInteger[2];
		BigInteger currentDivisor = new BigInteger("2");
		BigInteger currentExponent = BigInteger.ZERO;
		
		while(currentDividend.compareTo(BigInteger.ONE) > 0){
			
			result = currentDividend.divideAndRemainder(currentDivisor);
			
			//it divides evenly
			if(result[1].compareTo(BigInteger.ZERO) == 0){
				
				currentExponent = currentExponent.add(BigInteger.ONE);
				currentDividend = result[0];
				
			}
			else{//it does not divide evenly
				
				if(currentExponent.compareTo(BigInteger.ZERO) > 0){
					PrimeFactorization.put(currentDivisor, currentExponent);
					currentExponent = BigInteger.ZERO;
				}
				
				currentDivisor = currentDivisor.add(BigInteger.ONE);
			}
			
			//catches the last factor
			if(currentDividend.compareTo(BigInteger.ONE) == 0){
				PrimeFactorization.put(currentDivisor, currentExponent);
			}
			
		}
		
		return PrimeFactorization;
		
	}
}
