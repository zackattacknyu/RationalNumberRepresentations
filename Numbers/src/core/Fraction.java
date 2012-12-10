package core;



import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import coreUtils.DataStructures;
import coreUtils.NumberTheory;

public class Fraction{
	
	/*
	 * -----------------------------------------------------------
	 * VARIABLE DECLARATIONS
	 * -----------------------------------------------------------
	 */
	private BigInteger numerator;
	private BigInteger denominator;
	private BigInteger IntegerPart;
	private BigInteger masterNumerator;
	
	private HashMap<BigInteger,DecimalRep> DecimalReps;
	private HashMap<BigInteger,FractionRep> FractionReps;
	private HashMap<BigInteger,RationalNumberRep> IntegerPartReps;
	
	public static Fraction ZERO = new Fraction(BigInteger.ZERO, BigInteger.ONE);
	public static Fraction ONE = new Fraction(BigInteger.ONE, BigInteger.ONE);
	
	/*
	 * -----------------------------------------------------------
	 * GETTER AND SETTER METHODS
	 * -----------------------------------------------------------
	 */
	/**
	 * This gets the numerator of the fraction.
	 * @return	fraction's numerator
	 */
	public BigInteger getNumerator() {
		return numerator;
	}

	/**
	 * This gets the fraction's denominator
	 * @return	fractino's denominator
	 */
	public BigInteger getDenominator() {
		return denominator;
	}
	
	public BigInteger getIntegerPart() {
		return IntegerPart;
	}
	
	public BigInteger getMasterNumerator() {
		return masterNumerator;
	}
	
	/*
	 * -----------------------------------------------------------
	 * CONSTRUCTORS USING DIFFERENT REPRESENTATIONS
	 * -----------------------------------------------------------
	 */
	
	/**
	 * This constructor takes in a decimal rep, converts it to a fraction, 
	 * 		and makes that fraction the object
	 * @param theRep	the DecimalRep object being passed in
	 */
	public Fraction(DecimalRep theRep){
		this(ConvertDecimalRepToFraction(theRep));
		
		DecimalReps.put(theRep.getTheBase(), theRep);
		
	}
	
	/**
	 * This constructor takes in a fraction rep, converts it to a fraction,
	 * 		and makes that fraction the object
	 * @param theRep	the FractionRep object being passed in
	 */
	public Fraction(FractionRep theRep){
		this(ConvertFractionRepToFraction(theRep));
		FractionReps.put(theRep.getTheBase(), theRep);
	}
	
	
	/**
	 * This takes in an integer representation and makes the fraction. 
	 * 		The starting numerator is zero and the
	 * 		starting denominator is one. 
	 * @param IntegerRep
	 */
	public Fraction(RationalNumberRep IntegerRep){
		IntegerPart = ConvertIntegerRepToInteger(
				IntegerRep.getIntegerPartDigits(),
				IntegerRep.getTheBase());
		numerator = BigInteger.ZERO;
		denominator = BigInteger.ONE;
		masterNumerator = BigInteger.ZERO;
		
		InitializeRepTables();
		
		IntegerPartReps.put(IntegerRep.getTheBase(), IntegerRep);
	}
	
	private void InitializeRepTables(){
		DecimalReps = new HashMap<BigInteger,DecimalRep>(5);
		FractionReps = new HashMap<BigInteger,FractionRep>(5);
		IntegerPartReps = new HashMap<BigInteger,RationalNumberRep>(5);
	}
	
	/*
	 * -----------------------------------------------------------
	 * GETTERS AND SETTERS FOR THE REP HASHMAPS
	 * -----------------------------------------------------------
	 */
	
	public RationalNumberRep GetIntegerRep(BigInteger theBase){
		PutIntegerRep(theBase);
		return IntegerPartReps.get(theBase);
	}
	public void PutIntegerRep(BigInteger theBase){
		if(IntegerPartReps.containsKey(theBase)){
			return;
		}
		else{
			RationalNumberRep IntegerPartRep = 
					new RationalNumberRep(ConvertIntegerToIntegerRep(IntegerPart,theBase),theBase);
			IntegerPartReps.put(theBase, IntegerPartRep);
		}
	}
	
	public FractionRep GetFractionRep(BigInteger theBase){
		PutFractionRep(theBase);
		return FractionReps.get(theBase);
	}
	public void PutFractionRep(BigInteger theBase){
		if(FractionReps.containsKey(theBase)){
			return;
		}
		else{
			FractionRep theRep = ConvertFractionToFractionRep(this,theBase); 
			FractionReps.put(theBase, theRep);
		}
	}
	
	public DecimalRep GetDecimalRep(BigInteger theBase){
		PutDecimalRep(theBase);
		return DecimalReps.get(theBase);
	}
	public void PutDecimalRep(BigInteger theBase){
		if(DecimalReps.containsKey(theBase)){
			return;
		}
		else{
			DecimalRep theRep = ConvertFractionToDecimalRep(this,theBase); 
			DecimalReps.put(theBase, theRep);
		}
	}

	/*
	 * -----------------------------------------------------------
	 * CONSTRUCTORS USING PURE BIG INTEGERS
	 * -----------------------------------------------------------
	 */
	
	/**
	 * This is the general constructor for the fraction. 
	 * It makes sure that numerator/denominator is less than 1
	 * 		with any extra going into the integer part. 
	 * @param numerator
	 * @param denominator
	 * @param intPart
	 */
	public Fraction(BigInteger numerator, BigInteger denominator, BigInteger intPart){
		InitializeRepTables();
		
		this.denominator = denominator;
		this.IntegerPart = intPart;
		this.numerator = numerator;
		
		AdjustIntegerPart();
		SimplifyFraction();
		SetMasterNumerator();
	}
	
	/**
	 * This is the constructor for a fraction consisting of BigIntegers for the numerator and denominator.
	 * After initializing the variables, it makes sure the numerator and denominator are in lowest terms. 
	 * 
	 * @param numerator		the numerator of the fraction
	 * @param denominator	the denominator of the fraction
	 */
	public Fraction(BigInteger numerator, BigInteger denominator){
		this(numerator,denominator,BigInteger.ZERO);
	}
	
	/**
	 * This is used to instantiate the fraction using a generated fraction from a status method
	 * @param theFraction
	 */
	private Fraction(Fraction fracResult){
		this.numerator = fracResult.getNumerator();
		this.denominator = fracResult.getDenominator();
		this.IntegerPart = fracResult.getIntegerPart();
		this.masterNumerator = fracResult.getMasterNumerator();
		InitializeRepTables();
	}
	
	
	/*
	 *  -----------------------------------------------------------
	 *  FRACTION ADJUSTMENT METHODS
	 *   -----------------------------------------------------------
	 */
	private void AdjustIntegerPart(){
		
		//adjust the integer part
		BigInteger[] result = numerator.divideAndRemainder(denominator);
		IntegerPart = IntegerPart.add(result[0]);
		numerator = result[1];
	}
	
	private void SetMasterNumerator(){
		
		masterNumerator = IntegerPart.multiply(denominator);
		masterNumerator = masterNumerator.add(numerator);
	}

	/**
	 * This puts the fraction in lowest terms. 
	 * It is called when the fraction is made so there is no need to call it manually. 
	 */
	private void SimplifyFraction(){
		BigInteger gcd = numerator.abs().gcd(denominator.abs());
		
		numerator = numerator.divide(gcd);
		denominator = denominator.divide(gcd);
	}
	
	
	/*
	 *  -----------------------------------------------------------
	 *  FRACTION ARITHMETIC METHODS
	 *   -----------------------------------------------------------
	 *   Note: the results of the operations are new fraction objects. 
	 *   		they do not modify the existing fraction object. 
	 */
	
	/**
	 * This multiplies the currently instantiated fraction by another fraction
	 * @param otherFraction		the fraction to multiply the current one by
	 * @return					resultant fraction after multiplication
	 */
	public Fraction Multiply(Fraction otherFraction){
		return Multiply(this,otherFraction);
	}
	
	/**
	 * This is a static method multiplying two fractions together
	 * @param Frac1		first fraction in multiplication
	 * @param Frac2		second fraction in multiplication 
	 * @return			resultant fraction after multiplication 
	 */
	public static Fraction Multiply(Fraction Frac1, Fraction Frac2){
		return new Fraction(Frac1.getMasterNumerator().multiply(Frac2.getMasterNumerator()),
				Frac1.getDenominator().multiply(Frac2.getDenominator()));
	}
	
	

	/**
	 * This adds the currently instantiated fraction by another fraction
	 * @param otherFraction		the fraction to add the current one by
	 * @return					resultant fraction after addition
	 */
	public Fraction Add(Fraction otherFraction){
		return Add(this,otherFraction);
	}
	
	/**
	 * This is a static method adding two fractions together
	 * @param Frac1		first fraction in addition
	 * @param Frac2		second fraction in addition 
	 * @return			resultant fraction after addition 
	 */
	public static Fraction Add(Fraction Frac1, Fraction Frac2){
		BigInteger GCD = Frac1.getDenominator().gcd(Frac2.getDenominator());
		BigInteger PrelimLCM = Frac1.getDenominator().multiply(Frac2.getDenominator());
		BigInteger LCM = PrelimLCM.divide(GCD);
		
		BigInteger Frac1Multiplier = LCM.divide(Frac1.getDenominator());
		BigInteger Frac2Multiplier = LCM.divide(Frac2.getDenominator());
		
		BigInteger newFrac1Numerator = Frac1Multiplier.multiply(Frac1.getMasterNumerator());
		BigInteger newFrac2Numerator = Frac2Multiplier.multiply(Frac2.getMasterNumerator());
		
		return new Fraction(newFrac1Numerator.add(newFrac2Numerator),LCM);
	}
	
	
	/*
	 * -----------------------------------------------------------
	 * METHODS THAT CONVERT DIFFERENT REPS TO FRACTION OBJECTS
	 * -----------------------------------------------------------
	 */
	
	public static BigInteger ConvertIntegerRepToInteger(ArrayList<BigInteger> theDigits, BigInteger theBase){
		
		BigInteger theNumber = BigInteger.ZERO;
		
		for(int index = 0; index < theDigits.size(); index++){
			theNumber = theNumber.add( theDigits.get(index).multiply(theBase.pow(index)));		
		}
		
		return theNumber;
	}

	public static Fraction ConvertFractionRepToFraction(FractionRep FracRep){
		BigInteger IntegerPart = ConvertIntegerRepToInteger(FracRep.getIntegerPartDigits(),FracRep.getTheBase());
		BigInteger Numerator = ConvertIntegerRepToInteger(FracRep.getNumeratorDigits(),FracRep.getTheBase());
		BigInteger Denominator = ConvertIntegerRepToInteger(FracRep.getDenominatorDigits(),FracRep.getTheBase());
		
		return new Fraction(Numerator,Denominator,IntegerPart);
	}
	
	/*
	 * This takes in a digit rep in the form [int].[int][int] where the second int is the 
	 *      repeating part and returns the corresponding fraction. 
	 * It takes in the integers as BigIntegers and leave the conversion to them to the
	 *      other classes
	 */
	public static Fraction ConvertDecimalRepToFraction(DecimalRep theDecimal){
		
		BigInteger initialPart = theDecimal.getTerminatingPartNumber();
		int initialNumDigits = theDecimal.getTerminatingNumDigits();
		
		BigInteger repeatingPart = theDecimal.getRepeatingPartNumber();
		int repeatingNumDigits = theDecimal.getRepeatingNumDigits();
		
		BigInteger base = theDecimal.getTheBase();
		BigInteger integerPart = ConvertIntegerRepToInteger(theDecimal.getIntegerPartDigits(),base);
		
		Fraction initialPartFrac;
		
		if(initialNumDigits > 0){
			initialPartFrac = new Fraction(initialPart,base.pow(initialNumDigits), integerPart);
		}
		else{
			initialPartFrac = new Fraction(BigInteger.ZERO,BigInteger.ONE, integerPart);
		}
		
		BigInteger repeatingPartDenom = base.pow(repeatingNumDigits).subtract(BigInteger.ONE);
		
		
		Fraction repeatingPartFrac;
		Fraction repeatingPartValue;
		
		if(repeatingNumDigits > 0){
			repeatingPartFrac = new Fraction(repeatingPart,repeatingPartDenom);
			
			repeatingPartValue = repeatingPartFrac.Multiply(new Fraction(BigInteger.ONE,base.pow(initialNumDigits)));
		}
		else{
			repeatingPartFrac = Fraction.ZERO;
			repeatingPartValue = Fraction.ZERO;
		}
		
		return initialPartFrac.Add(repeatingPartValue);
	}
	
	/*
	 * -----------------------------------------------------------
	 * METHODS THAT CONVERT FRACTION TO DIFFERENT REPS
	 * -----------------------------------------------------------
	 */
	
	public static ArrayList<BigInteger> ConvertIntegerToIntegerRep(BigInteger theNumber, BigInteger base){
		
		BigInteger currentDivisor = theNumber;
		BigInteger[] QuotRemainder;
		ArrayList<BigInteger> BaseDigits = new ArrayList<BigInteger>();
		
		/*
		 * This uses the algorithm of repeated integer division to get the digits
		 * 
		 */
		currentDivisor = theNumber;
		while(currentDivisor.compareTo(BigInteger.ZERO) != 0){
			QuotRemainder = currentDivisor.divideAndRemainder(base);
			currentDivisor=QuotRemainder[0];
			BaseDigits.add(QuotRemainder[1]);
		}
		
		return BaseDigits;
	}
	
	public static FractionRep ConvertFractionToFractionRep(Fraction theFrac, BigInteger theBase){
		ArrayList<BigInteger> integerPartDigits = ConvertIntegerToIntegerRep(theFrac.getIntegerPart(),theBase);
		ArrayList<BigInteger> numeratorDigits = ConvertIntegerToIntegerRep(theFrac.getNumerator(),theBase);
		ArrayList<BigInteger> denominatorDigits = ConvertIntegerToIntegerRep(theFrac.getDenominator(),theBase);
		
		return new FractionRep(theBase,integerPartDigits,numeratorDigits,denominatorDigits);
		
	}
	
	public static DecimalRep ConvertFractionToDecimalRep(Fraction theFraction, BigInteger theBase){
		
		DecimalRep theRep; 
		
		Set<BigInteger> DenomPrimeFactors = 
				NumberTheory.GetPrimeFactorization(theFraction.getDenominator()).keySet();
		Set<BigInteger> BasePrimeFactors = 
				NumberTheory.GetPrimeFactorization(theBase).keySet();
		
		/*
		 * The base prime factor set contains all the denominator's prime factors, so 
		 *     the fraction can be expressed as a terminating decimal 
		 */
		if(BasePrimeFactors.containsAll(DenomPrimeFactors)){
			theRep = Terminating(theFraction,theBase);
		}
		else if(DataStructures.isIntersectionEmpty(DenomPrimeFactors, BasePrimeFactors)){
			theRep = OnlyRepeating(theFraction,theBase);
		}
		else{
			theRep = MixedDecimal(theFraction,theBase);
		}
		
		//set the integer part digits
		theRep.setIntegerPartDigits(ConvertIntegerToIntegerRep(theFraction.getIntegerPart(),theBase));
		
		return theRep;
	}
	
	/*
	 * -----------------------------------------------------------
	 * THE PRIVATE METHODS THAT ASSIST IN THE FRACTION TO DECIMAL CONVERSION
	 * -----------------------------------------------------------
	 */
	
	private static DecimalRep OnlyRepeating(Fraction theFraction, BigInteger theBase){
		BigInteger numerator = theFraction.getNumerator();
		BigInteger denominator = theFraction.getDenominator();
		int numDigitsRepeating = RepeatingNumDigitsRequired(denominator, theBase);
		
		/*
		 * Since we want to find a s.t. numerator/denominator = a/(base^power - 1)
		 *       this is the factor needed to multiply the numerator and denominator
		 */
		BigInteger multiplyingFactor = theBase.pow(numDigitsRepeating).subtract(BigInteger.ONE).divide(denominator);
				
		BigInteger repeatingPartNumber = numerator.multiply(multiplyingFactor);
		
		return new DecimalRep(theBase,0,numDigitsRepeating,BigInteger.ZERO,repeatingPartNumber);
		
	}
	
	private static DecimalRep Terminating(Fraction theFraction, BigInteger theBase){
		BigInteger numerator = theFraction.getNumerator();
		BigInteger denominator = theFraction.getDenominator();
		int numDigitsTerminating = TerminatingNumDigitsRequired(denominator, theBase);
		
		/*
		 * Since we want to find a s.t. numerator/denominator = a/(base^power)
		 *       this is the factor needed to multiply the numerator and denominator
		 */
		BigInteger multiplyingFactor = theBase.pow(numDigitsTerminating).divide(denominator);
		
		BigInteger terminatingPartNumber = numerator.multiply(multiplyingFactor);
		
		return new DecimalRep(theBase,numDigitsTerminating,0,terminatingPartNumber,BigInteger.ZERO);
	}
	
	private static DecimalRep MixedDecimal(Fraction theFraction, BigInteger theBase){
		BigInteger numerator = theFraction.getNumerator();
		BigInteger denominator = theFraction.getDenominator();
		
		BigInteger[] denoms = GetDenominators(denominator, theBase);
		int numDigitsTerminating = TerminatingNumDigitsRequired(denoms[0], theBase);
		int numDigitsRepeating = RepeatingNumDigitsRequired(denoms[1], theBase);
		
		/*
		 * Since we want to find a s.t. numerator/denominator = a/(base^power)
		 *       this is the factor needed to multiply the numerator and denominator
		 */
		BigInteger repeatingDecDenom = theBase.pow(numDigitsRepeating).subtract(BigInteger.ONE);
		BigInteger terminatingDecDenom = theBase.pow(numDigitsTerminating);
		BigInteger multiplyingFactor = repeatingDecDenom.multiply(terminatingDecDenom).divide(denominator);
		
		BigInteger numeratorToMatch = multiplyingFactor.multiply(numerator);
		
		BigInteger[] DecimalRepParts = numeratorToMatch.divideAndRemainder(repeatingDecDenom);
		
		BigInteger terminatingPartNumber = DecimalRepParts[0];
		BigInteger repeatingPartNumber = DecimalRepParts[1];
		
		//quotient of the above is the terminating part. remainder is the repeating part
		return new DecimalRep(theBase,numDigitsTerminating,numDigitsRepeating,terminatingPartNumber,repeatingPartNumber);
	}
	
	/**
	 * For a mixed decimal, this tells the operative denominator for the terminating 
	 *     and repeating parts
	 * @param denominator
	 * @param base
	 * @return Array of BigIntegers which are the denominators associated with the terminating and repeating part
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private static BigInteger[] GetDenominators(BigInteger denominator, BigInteger base){
		
		BigInteger TermPartDenom = BigInteger.ONE;
		BigInteger RepeatPartDenom = BigInteger.ONE;
		
		HashMap<BigInteger,BigInteger> DenomPrimeFactorization = NumberTheory.GetPrimeFactorization(denominator);
		HashMap<BigInteger,BigInteger> BasePrimeFactorization = NumberTheory.GetPrimeFactorization(base);
		
		Set[] FactorSets = DataStructures.SplitSetbySet(DenomPrimeFactorization.keySet(),BasePrimeFactorization.keySet());
		
		Iterator<BigInteger> TermPartFactors = FactorSets[0].iterator();
		Iterator<BigInteger> RepeatPartFactors = FactorSets[1].iterator();
		BigInteger currentTermFactor;
		BigInteger currentTermFactorExponent;
		BigInteger currentRepeatFactor;
		BigInteger currentRepeatFactorExponent;
		
		BigInteger[] Denominators = new BigInteger[2];
		
		while(TermPartFactors.hasNext()){
			currentTermFactor = TermPartFactors.next();
			currentTermFactorExponent = DenomPrimeFactorization.get(currentTermFactor);
			TermPartDenom = TermPartDenom.multiply(currentTermFactor.pow(currentTermFactorExponent.intValue()));
		}
		
		while(RepeatPartFactors.hasNext()){
			currentRepeatFactor = RepeatPartFactors.next();
			currentRepeatFactorExponent = DenomPrimeFactorization.get(currentRepeatFactor);
			RepeatPartDenom = RepeatPartDenom.multiply(currentRepeatFactor.pow(currentRepeatFactorExponent.intValue()));
		}
		
		Denominators[0] = TermPartDenom;
		Denominators[1] = RepeatPartDenom;
		return Denominators;
		
	}
	
	/**
	 * For a repeating decimal, this tells the number of digits required for it.
	 * The denominator must be coprime to the base to call this function. 
	 * The code works by calculating the order of the base modulo the denominator. 
	 * The number of digits required for 1/r where gcd(r,base)=1 is equal to 
	 *      the order of the element base in the group Z_r star. 
	 * @param denominator	the denominator
	 * @param base			the base number
	 * @return				the number of digits required for the repeating decimal
	 */
	private static int RepeatingNumDigitsRequired(BigInteger denominator, BigInteger base){
		
		return NumberTheory.OrderModN(base, denominator).intValue();
	}
	
	/**
	 * This tells the number of digits required for a terminating decimal. 
	 * The denominator must divide a power of the base in order to use this function. 
	 * @param denominator	the denominator
	 * @param base			the base for our representation
	 * @return				the number of digits required for the terminating decimal
	 */
	private static int TerminatingNumDigitsRequired(BigInteger denominator, BigInteger base){
		int MaxRatio = 0;
		BigInteger currentPrime;
		BigInteger BasePrimePower;
		BigInteger DenomPrimePower;
		BigInteger[] theRatioInfo;
		BigInteger theRatio;
		int currentRatio;
		HashMap<BigInteger,BigInteger> DenomPrimeFactorization = NumberTheory.GetPrimeFactorization(denominator);
		HashMap<BigInteger,BigInteger> BasePrimeFactorization = NumberTheory.GetPrimeFactorization(base);
		Iterator<BigInteger> thePrimeFactors = DenomPrimeFactorization.keySet().iterator();
		
		while(thePrimeFactors.hasNext()){
			currentPrime = thePrimeFactors.next();
			
			if(DenomPrimeFactorization.containsKey(currentPrime)){
				BasePrimePower = BasePrimeFactorization.get(currentPrime);
				DenomPrimePower = DenomPrimeFactorization.get(currentPrime);
				
				theRatioInfo = DenomPrimePower.divideAndRemainder(BasePrimePower);
				theRatio = theRatioInfo[0];
				if(theRatioInfo[1].compareTo(BigInteger.ZERO) > 0){
					theRatio.add(BigInteger.ONE);
				}
				currentRatio = theRatio.intValue();
				
				if(currentRatio > MaxRatio){
					MaxRatio = currentRatio;
				}
				
			}
		}
		
		return MaxRatio;
	}
}
