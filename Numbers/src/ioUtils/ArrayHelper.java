package ioUtils;

import java.util.ArrayList;

public class ArrayHelper {

	public static ArrayList<String> toStringArrayList(ArrayList<Object> objectArray){
		ArrayList<String> newArrayList = new ArrayList<String>(objectArray.size());
		for(Object objectEntry: objectArray){
			newArrayList.add(objectEntry.toString());
		}
		return newArrayList;
	}
}
