package coreAccessor;

public class FractionStrings {

	private String integerRep;
	private String numeratorRep;
	private String denominatorRep;
	
	public String getIntegerRep() {
		return integerRep;
	}

	public String getNumeratorRep() {
		return numeratorRep;
	}

	public String getDenominatorRep() {
		return denominatorRep;
	}

	public FractionStrings(String integerRep, String numeratorRep,
			String denominatorRep) {
		super();
		this.integerRep = integerRep;
		this.numeratorRep = numeratorRep;
		this.denominatorRep = denominatorRep;
	}
	
}
