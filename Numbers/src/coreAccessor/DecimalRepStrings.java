package coreAccessor;

public class DecimalRepStrings {
	
	private String IntegerPartRep;
	
	private int TermNumDigits;
	private String TermPartRep;
	
	private int RepeatingNumDigits;
	private String RepeatingPartRep;
	public String getIntegerPartRep() {
		return IntegerPartRep;
	}
	public int getTermNumDigits() {
		return TermNumDigits;
	}
	public String getTermPartRep() {
		return TermPartRep;
	}
	public int getRepeatingNumDigits() {
		return RepeatingNumDigits;
	}
	public String getRepeatingPartRep() {
		return RepeatingPartRep;
	}
	public DecimalRepStrings(String integerPartRep, int termNumDigits,
			String termPartRep, int repeatingNumDigits, String repeatingPartRep) {
		super();
		IntegerPartRep = integerPartRep;
		TermNumDigits = termNumDigits;
		TermPartRep = termPartRep;
		RepeatingNumDigits = repeatingNumDigits;
		RepeatingPartRep = repeatingPartRep;
		AddStringIfNoInitialPart();
	}
	
	public void AddStringIfNoInitialPart(){
		if(TermNumDigits == 0){
			TermPartRep = "<NONE>";
		}
	}
	
	
}
