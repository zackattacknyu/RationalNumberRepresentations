package coreAccessorResourceBundles;

import java.util.ArrayList;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import coreAccessorUtils.ArrayHelper;
import coreAccessorUtils.StringHelper;

public class ResourceBundleHelper {
	
	
	protected ResourceBundle messagesToUser;
	protected ArrayList<String> missingResourceMessage;
	protected ArrayList<Object> missingResourceMessageObject;
	
	private static final String MISSING_RESOURCE_MESSAGE_LINE_1 = 
			"Error: Program could not find resource bundle." ; 
	private static final String MISSING_RESOURCE_MESSAGE_LINE_2_PIECE_1 = 
			"Check " ;
	private static final String MISSING_RESOURCE_MESSAGE_LINE_2_PIECE_3 = 
			" to see the resource bundle defined there and then" ;
	private static final String MISSING_RESOURCE_MESSAGE_LINE_3 = 
			" check for the existence of that bundle" ;
	
	/*
	 * Multi-line messages will have the keys in the form
	 * 		<Message Key>_line<Line Number>
	 */
	private static final String keySuffix = "_line";
	
	public static final ArrayList<Object> getMissingResourceMessage(String classToCheck){
		ArrayList<Object> newMessage = new ArrayList<Object>(3);
		newMessage.add(MISSING_RESOURCE_MESSAGE_LINE_1);
		newMessage.add(StringHelper.concatThreeStrings(MISSING_RESOURCE_MESSAGE_LINE_2_PIECE_1, 
				classToCheck, MISSING_RESOURCE_MESSAGE_LINE_2_PIECE_3));
		newMessage.add(MISSING_RESOURCE_MESSAGE_LINE_3);
		return newMessage;
	}
	
	
	public ResourceBundleHelper(String ResourceBundleLocation,
			String classToCheckForResourceBundleDefinition){
		this(Locale.US,ResourceBundleLocation,classToCheckForResourceBundleDefinition);
	}
	
	public ResourceBundleHelper(Locale currentLocale, 
			String ResourceBundleLocation, String classToCheckForResourceBundleDefinition){
		
		missingResourceMessageObject = getMissingResourceMessage(classToCheckForResourceBundleDefinition);
		missingResourceMessage = ArrayHelper.toStringArrayList(missingResourceMessageObject);
		
		try{
			messagesToUser = ResourceBundle.getBundle(ResourceBundleLocation, currentLocale);
		} catch(MissingResourceException e){
			
			/*if the resource was not imported or named properly, the bundle is set to null
			 * and when the getMessage method is invoked, that method returns a generic
			 * no resource defined error. 
			 */
			messagesToUser = null;
			
		}
		
	}
	
	public static String getCurrentKey(String key, int lineNumber){
		return StringHelper.concatThreeStrings(key, keySuffix, Integer.valueOf(lineNumber).toString());
	}
	
	/*
	 * This method is for property resource bundles
	 */
	
	public ArrayList<String> getMessage(String messageKey){
		ArrayList<String> theMessage = new ArrayList<String>();
		int currentLineNumber = 1;
		String currentKey = getCurrentKey(messageKey,1);
		
		if(messagesToUser == null){
			return missingResourceMessage;
		}
		
		//if the string is one line, there is no line number in the message key
		if(messagesToUser.containsKey(messageKey)){
			theMessage.add(messagesToUser.getString(messageKey));
		}
		else if (messagesToUser.containsKey(currentKey)){ //if the string is multiple lines
			
			while(messagesToUser.containsKey(currentKey)){
				theMessage.add(messagesToUser.getString(currentKey));
				currentLineNumber++;
				currentKey = getCurrentKey(messageKey,currentLineNumber);
			}
			
		}
		else{ //if the key does not exist in the resource bundle
			return null;
		}
		
		return theMessage;
	}
	
	/*
	 * This method is for list resource bundles
	 */
	public ArrayList<Object> getObject(String messageKey){
		ArrayList<Object> theMessage = new ArrayList<Object>();
		int currentLineNumber = 1;
		String currentKey = getCurrentKey(messageKey,1);
		
		if(messagesToUser == null){
			return missingResourceMessageObject;
		}
		
		//if the string is one line, there is no line number in the message key
		if(messagesToUser.containsKey(messageKey)){
			theMessage.add(messagesToUser.getString(messageKey));
		}
		else if (messagesToUser.containsKey(currentKey)){ //if the string is multiple lines
			
			while(messagesToUser.containsKey(currentKey)){
				theMessage.add(messagesToUser.getObject(currentKey));
				currentLineNumber++;
				currentKey = getCurrentKey(messageKey,currentLineNumber);
			}
			
		}
		else{ //if the key does not exist in the resource bundle
			return null;
		}
		
		return theMessage;
	}
	
	public Object getSingleObject(String messageKey){	
		if(messagesToUser == null){
			return missingResourceMessage;
		}
		
		//if the string is one line, there is no line number in the message key
		if(messagesToUser.containsKey(messageKey)){
			return messagesToUser.getString(messageKey);
		}
		else{ //if the key does not exist in the resource bundle
			return null;
		}
	}
	
	
}
