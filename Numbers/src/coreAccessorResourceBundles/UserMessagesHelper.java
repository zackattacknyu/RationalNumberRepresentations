package coreAccessorResourceBundles;

import java.util.Locale;

public class UserMessagesHelper extends ResourceBundleHelper{

	
	
	public UserMessagesHelper(){
		this(Locale.US);
	}
	
	public UserMessagesHelper(Locale currentLocale){
		super(currentLocale, ResourceBundleConstants.UserMessages_ResourceBundleLocation,
				ResourceBundleConstants.UserMessages_classToCheck);
	}
}
