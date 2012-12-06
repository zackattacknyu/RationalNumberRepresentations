package coreAccessorResourceBundles;

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
}