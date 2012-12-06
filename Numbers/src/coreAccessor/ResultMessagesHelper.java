package coreAccessor;

import java.util.Locale;

import coreAccessorUtils.ResourceBundleHelper;

public class ResultMessagesHelper extends ResourceBundleHelper {

	private static final String ResourceBundleLocation = "coreAccessor.ResultMessages";
	private static final String classToCheck = "coreAccessor.ResultMessagesHelper";
	
	public ResultMessagesHelper(Locale currentLocale) {
		super(currentLocale, ResourceBundleLocation,classToCheck);
	}
	
	public ResultMessagesHelper(){
		this(Locale.US);
	}

	public String getResultHeader(String numInBaseTenFraction){
		return String.format(getSingleObject("resultHeader").toString(), 
				numInBaseTenFraction);
	}
	
	public String getResultLine(String lineKey, String stringForBase, String resultString){
		return String.format(getSingleObject(lineKey).toString(), stringForBase,resultString);
	}
}
