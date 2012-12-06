package coreAccessor;

import java.util.Locale;
import coreAccessorUtils.ResourceBundleHelper;

public class UserMessagesHelper extends ResourceBundleHelper{

	private static final String ResourceBundleLocation = "coreAccessor.UserMessages";
	private static final String classToCheck = "coreAccessor.UserMessagesHelper";
	
	public UserMessagesHelper(){
		this(Locale.US);
	}
	
	public UserMessagesHelper(Locale currentLocale){
		super(currentLocale, ResourceBundleLocation,classToCheck);
	}
}
