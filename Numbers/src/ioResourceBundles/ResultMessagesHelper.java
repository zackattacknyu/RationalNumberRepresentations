package ioResourceBundles;

import java.util.ArrayList;
import java.util.Locale;


public class ResultMessagesHelper extends ResourceBundleHelper {

	
	public ResultMessagesHelper(Locale currentLocale) {
		super(currentLocale, ResourceBundleConstants.ResultMessages_ResourceBundleLocation,
				ResourceBundleConstants.ResultMessages_classToCheck);
	}
	
	public ResultMessagesHelper(){
		this(Locale.US);
	}

	public String getResultHeader(String numInBaseTenFraction){
		return String.format(getSingleObject(ResourceBundleConstants.resultHeaderKey).toString(), 
				numInBaseTenFraction);
	}
	
	public ArrayList<String> getFunctionNameError(String functionName){
		ArrayList<String> toReturn = new ArrayList<String>(1);
		toReturn.add(String.format(getSingleObject(ResourceBundleConstants.functionNameErrorKey).toString()
				, functionName));
		return toReturn;
	}
	
	private String getResultLine(String lineKey, String stringForBase, String resultString){
		return String.format(getSingleObject(lineKey).toString(), stringForBase,resultString);
	}
	
	public String getIntegerPartResult(String stringForBase, String resultString){
		return getResultLine(ResourceBundleConstants.integerPartLabelKey,
				stringForBase,resultString);
	}
	
	public String getFractionRepResult(String stringForBase, String resultString){
		return getResultLine(ResourceBundleConstants.fractionRepLabelKey,
				stringForBase,resultString);
	}
	
	public String getDecimalRepResult(String stringForBase, String resultString){
		return getResultLine(ResourceBundleConstants.decimalRepLabelKey,
				stringForBase,resultString);
	}
	
	public static ArrayList<String> getFullResultMessage(String resultHeader, 
			String integerResult, String fractionResult, String decimalResult){
		ArrayList<String> mesage = new ArrayList<String>(10);
		mesage.add("");
		mesage.add(resultHeader);
		mesage.add("");
		mesage.add(integerResult);
		mesage.add(fractionResult);
		mesage.add(decimalResult);
		mesage.add("");
		mesage.add("");
		return mesage;
	}
}
